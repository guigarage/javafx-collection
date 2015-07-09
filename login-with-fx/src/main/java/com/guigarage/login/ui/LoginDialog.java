package com.guigarage.login.ui;

import com.guigarage.login.LoginService;
import com.guigarage.login.OAuthApi;
import com.guigarage.login.User;
import io.datafx.core.ExceptionHandler;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.web.WebView;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.scribe.builder.api.Api;

import java.util.function.Consumer;

public class LoginDialog {

    private Dialog dialog;

    private LoginService loginService;

    private ExceptionHandler exceptionHandler;

    public LoginDialog(LoginService loginService, ExceptionHandler exceptionHandler) {
        this.loginService = loginService;
        this.exceptionHandler = exceptionHandler;
    }

    public Task<User> show(Object owner, Consumer<User> callback) {
        WebView webView = new WebView();
        webView.setPrefWidth(loginService.getApi().getPreferredPageWidth());
        webView.setPrefHeight(loginService.getApi().getPreferredPageHeight());
        dialog = new Dialog(null, "Login", false, DialogStyle.NATIVE);
        dialog.titleProperty().bind(webView.getEngine().titleProperty());
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(webView);
        dialog.getWindow().centerOnScreen();
        Task<User> loginTask = loginService.login(webView);
        loginTask.valueProperty().addListener(e -> close());
        loginTask.exceptionProperty().addListener(e -> exceptionHandler.setException(loginTask.getException()));
        loginTask.exceptionProperty().addListener(e -> close());
        loginTask.valueProperty().addListener(event -> {
            try {
                callback.accept(loginTask.get());
            } catch (Exception exception) {
                exceptionHandler.setException(exception);
            }
        });
        dialog.show();
        return loginTask;
    }

    public void close() {
        if (dialog != null) {
            dialog.hide();
        }
        dialog = null;
    }
}
