package com.guigarage.login;

import javafx.concurrent.Task;
import org.apache.http.NameValuePair;
import org.scribe.builder.api.Api;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.List;
import java.util.function.Function;

public abstract class OAuthApi<T extends Api> {

    private Class<T> apiClass;

    private String verifyCredentialsUrl;

    public OAuthApi(Class<T> apiClass, String verifyCredentialsUrl) {
        this.apiClass = apiClass;
        this.verifyCredentialsUrl = verifyCredentialsUrl;
    }

    public abstract User getUser(Response response);

    public abstract String getVerifier(List<NameValuePair> requestParams);

    public abstract String getAuthUrl(OAuthService service, Task<Token> requestTokenProvider) throws Exception;

    public abstract Token getAccessToken(OAuthService service, Verifier verifier, Task<Token> requestTokenProvider) throws Exception;

    public String getVerifyCredentialsUrl() {
        return verifyCredentialsUrl;
    }

    public Class<T> getApiClass() {
        return apiClass;
    }

    public String getScope() {
        return null;
    }

    public abstract int getPreferredPageWidth();

    public abstract int getPreferredPageHeight();
}
