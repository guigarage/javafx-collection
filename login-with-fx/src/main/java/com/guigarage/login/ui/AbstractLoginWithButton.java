package com.guigarage.login.ui;

import com.guigarage.login.LoginService;
import com.guigarage.login.User;
import com.guigarage.login.ui.skin.LoginButtonSkin;
import io.datafx.core.ExceptionHandler;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import org.scribe.builder.api.Api;

import java.util.function.Consumer;

public abstract class AbstractLoginWithButton<S extends Api> extends Button {

    public AbstractLoginWithButton(LoginService<S> loginService, Consumer<User> callback) {
        this(loginService, callback, ExceptionHandler.getDefaultInstance());
    }

    public AbstractLoginWithButton(LoginService<S> loginService, Consumer<User> callback, ExceptionHandler exceptionHandler) {
        setOnAction((e) -> {
            new LoginDialog(loginService, exceptionHandler).show(AbstractLoginWithButton.this, callback);
        });
        getStyleClass().add("login-button");
        getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LoginButtonSkin(this);
    }

}

