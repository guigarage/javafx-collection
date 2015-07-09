package com.guigarage.login.ui;

import com.guigarage.login.FacebookOAuthApi;
import com.guigarage.login.LoginService;
import com.guigarage.login.TwitterOAuthApi;
import com.guigarage.login.User;
import io.datafx.core.ExceptionHandler;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LoginWithTwitterButton extends AbstractLoginWithButton {

    public LoginWithTwitterButton(String key, String secret, String callbackUrl, Consumer<User> callback) {
        this(key, secret, callbackUrl, callback, ExceptionHandler.getDefaultInstance());
    }

    public LoginWithTwitterButton(String key, String secret, String callbackUrl, Consumer<User> callback, ExceptionHandler exceptionHandler) {
        this(() -> key, () -> secret, () -> callbackUrl, callback, exceptionHandler);
    }

    public LoginWithTwitterButton(Supplier<String> key, Supplier<String> secret, Supplier<String> callbackUrl, Consumer<User> callback) {
        this(key, secret, callbackUrl, callback, ExceptionHandler.getDefaultInstance());
    }

    public LoginWithTwitterButton(Supplier<String> key, Supplier<String> secret, Supplier<String> callbackUrl, Consumer<User> callback, ExceptionHandler exceptionHandler) {
        super(new LoginService(new TwitterOAuthApi(), key, secret, callbackUrl), callback, exceptionHandler);
        setText("Login with Twitter");
        getStyleClass().add("twitter-login-button");
    }
}
