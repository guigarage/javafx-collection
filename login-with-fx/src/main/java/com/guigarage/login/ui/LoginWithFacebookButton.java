package com.guigarage.login.ui;


import com.guigarage.login.FacebookOAuthApi;
import com.guigarage.login.LoginService;
import com.guigarage.login.User;
import io.datafx.core.ExceptionHandler;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LoginWithFacebookButton extends AbstractLoginWithButton {

    public LoginWithFacebookButton(String key, String secret, String callbackUrl, Consumer<User> callback) {
        this(key, secret, callbackUrl, callback, ExceptionHandler.getDefaultInstance());
    }

    public LoginWithFacebookButton(String key, String secret, String callbackUrl, Consumer<User> callback, ExceptionHandler exceptionHandler) {
        this(() -> key, () -> secret, () -> callbackUrl, callback, exceptionHandler);
    }

    public LoginWithFacebookButton(Supplier<String> key, Supplier<String> secret, Supplier<String> callbackUrl, Consumer<User> callback) {
        this(key, secret, callbackUrl, callback, ExceptionHandler.getDefaultInstance());
    }

    public LoginWithFacebookButton(Supplier<String> key, Supplier<String> secret, Supplier<String> callbackUrl, Consumer<User> callback, ExceptionHandler exceptionHandler) {
        super(new LoginService(new FacebookOAuthApi(), key, secret, callbackUrl), callback, exceptionHandler);
        setText("Login with Facebook");
        getStyleClass().add("facebook-login-button");
    }
}
