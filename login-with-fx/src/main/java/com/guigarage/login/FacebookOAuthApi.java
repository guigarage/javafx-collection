package com.guigarage.login;

import javafx.concurrent.Task;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.List;

public class FacebookOAuthApi extends OAuthApi<FacebookApi> {

    public FacebookOAuthApi() {
        super(FacebookApi.class, "https://graph.facebook.com/me");
    }

    @Override
    public User getUser(Response response) {
        JSONObject object = new JSONObject(response.getBody());
        User user = new User();
        user.setFirstName(object.getString("first_name"));
        user.setLastName(object.getString("last_name"));
        user.setNetworkId(object.getString("id"));
        return user;
    }

    @Override
    public String getVerifier(List<NameValuePair> requestParams) {
        String v = requestParams.get(0).getValue();
        return v.substring(0, v.length() - 2);
    }

    @Override
    public String getAuthUrl(OAuthService service, Task<Token> requestTokenProvider) {
        return service.getAuthorizationUrl(null);
    }

    @Override
    public Token getAccessToken(OAuthService service, Verifier verifier, Task<Token> requestTokenProvider) throws Exception {
        return service.getAccessToken(null, verifier);
    }

    @Override
    public int getPreferredPageWidth() {
        return 300;
    }

    @Override
    public int getPreferredPageHeight() {
        return 600;
    }
}
