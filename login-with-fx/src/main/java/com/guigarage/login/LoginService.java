package com.guigarage.login;

import io.datafx.core.concurrent.ConcurrentUtils;
import io.datafx.core.concurrent.ProcessChain;
import javafx.concurrent.Task;
import javafx.scene.web.WebView;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class LoginService<T extends Api> {

    private OAuthApi<T> api;

    private Supplier<String> key;

    private Supplier<String> secret;

    private Supplier<String> callbackUrl;


    public LoginService(OAuthApi<T> api, String key, String secret, String callbackUrl) {
        this.api = api;
        this.key = () -> key;
        this.secret = () -> secret;
        this.callbackUrl = () -> callbackUrl;
    }

    public LoginService(OAuthApi<T> api, Supplier<String> key, Supplier<String> secret, Supplier<String> callbackUrl) {
        this.api = api;
        this.key = key;
        this.secret = secret;
        this.callbackUrl = callbackUrl;
    }

    public Task<User> login(WebView webView) {

        Task<OAuthService> oAuthServiceProvider = ProcessChain.create().addSupplierInExecutor(() -> {
            ServiceBuilder builder = new ServiceBuilder()
                    .provider(api.getApiClass())
                    .apiKey(key.get())
                    .apiSecret(secret.get())
                    .callback(callbackUrl.get());
            if (api.getScope() != null) {
                builder = builder.scope(api.getScope());
            }
            return builder.build();
        }).run();

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        webView.getEngine().locationProperty().addListener(e -> {
            String newLocation = webView.getEngine().getLocation();
            if (newLocation != null && newLocation.startsWith(callbackUrl.get())) {
                boolean locked = lock.tryLock();
                if (!locked) {
                    throw new RuntimeException("Can't lock!");
                }
                try {

                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        });

        Task<Token> requestTokenProvider = ProcessChain.create().addSupplierInExecutor(() -> {
            try {
                return oAuthServiceProvider.get().getRequestToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();

        Task<Void> setUrlTask = ProcessChain.create().addSupplierInExecutor(() -> {
                    try {
                        return api.getAuthUrl(oAuthServiceProvider.get(), requestTokenProvider);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).addConsumerInPlatformThread(authUrl -> webView.getEngine().load(authUrl)).run();

        return ProcessChain.create().addSupplierInExecutor(() -> {
            lock.lock();
            try {
                setUrlTask.get();
                condition.await();
                String newLocation = ConcurrentUtils.runCallableAndWait(() -> webView.getEngine().getLocation());
                List<NameValuePair> values = URLEncodedUtils.parse(newLocation, Charset.defaultCharset());

                Verifier v = new Verifier(api.getVerifier(values));
                Token accessToken = api.getAccessToken(oAuthServiceProvider.get(), v, requestTokenProvider);

                OAuthRequest request = new OAuthRequest(Verb.GET, api.getVerifyCredentialsUrl());
                oAuthServiceProvider.get().signRequest(accessToken, request);

                request.addHeader("GData-Version", "3.0");

                return api.getUser(request.send());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).run();
    }

    public OAuthApi<T> getApi() {
        return api;
    }
}
