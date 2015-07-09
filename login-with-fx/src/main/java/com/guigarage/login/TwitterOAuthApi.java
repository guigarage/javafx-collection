package com.guigarage.login;

import javafx.concurrent.Task;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.List;

/**
 * Created by hendrikebbers on 10.09.14.
 */
public class TwitterOAuthApi extends OAuthApi<TwitterApi.SSL> {

    public TwitterOAuthApi() {
        super(TwitterApi.SSL.class, "https://api.twitter.com/1.1/account/verify_credentials.json");
    }

    @Override
    public User getUser(Response response) {
        JSONObject object = new JSONObject(response.getBody());
        User user = new User();
        user.setFirstName(object.getString("name").split(" ")[0]);
        user.setLastName(object.getString("name").split(" ")[1]);
        user.setNetworkId(object.getString("id_str"));
        user.setUserName(object.getString("screen_name"));
        return user;
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
        return service.getAuthorizationUrl(requestTokenProvider.get());
    }

    @Override
    public Token getAccessToken(OAuthService service, Verifier verifier, Task<Token> requestTokenProvider) throws Exception {
        return service.getAccessToken(requestTokenProvider.get(), verifier);
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
