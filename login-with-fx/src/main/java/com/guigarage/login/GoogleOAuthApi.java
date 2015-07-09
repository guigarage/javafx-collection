package com.guigarage.login;

import javafx.concurrent.Task;
import org.apache.http.NameValuePair;
import org.scribe.builder.api.GoogleApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.List;

/**
 * Created by hendrikebbers on 10.09.14.
 */
public class GoogleOAuthApi extends OAuthApi<GoogleApi>{

    public GoogleOAuthApi() {
        super(GoogleApi.class, "https://docs.google.com/feeds/default/private/full/");
    }

    @Override
    public User getUser(Response response) {
        System.out.println(response.getBody());
        return new User();
    }

    @Override
    public String getVerifier(List<NameValuePair> requestParams) {
        for (NameValuePair p : requestParams) {
            if (p.getName().equals("oauth_verifier")) {
                return p.getValue();
            }
        }
        return null;
    }

    @Override
    public String getAuthUrl(OAuthService service, Task<Token> requestTokenProvider) throws Exception {
        return "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=" + requestTokenProvider.get().getToken();
    }

    @Override
    public Token getAccessToken(OAuthService service, Verifier verifier, Task<Token> requestTokenProvider) throws Exception {
        return requestTokenProvider.get();
    }

    @Override
    public String getScope() {
        return "https://docs.google.com/feeds/";
    }

    @Override
    public int getPreferredPageWidth() {
        return 690;
    }

    @Override
    public int getPreferredPageHeight() {
        return 730;
    }
}
