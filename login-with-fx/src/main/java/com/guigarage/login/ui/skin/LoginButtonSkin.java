package com.guigarage.login.ui.skin;

import com.guigarage.login.ui.AbstractLoginWithButton;
import com.guigarage.login.ui.LoginWithFacebookButton;
import com.guigarage.login.ui.LoginWithGoogleButton;
import com.guigarage.login.ui.LoginWithTwitterButton;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginButtonSkin extends ButtonSkin {

    public LoginButtonSkin(AbstractLoginWithButton control) {
        super(control);
        Text icon = null;
            if (control instanceof LoginWithTwitterButton) {
                icon = createIconText('\uf099');
            } else if (control instanceof LoginWithGoogleButton) {
                icon = createIconText('\uf0d5');
            } else if (control instanceof LoginWithFacebookButton) {
                icon = createIconText('\uf09a');
            }

        if (icon == null) {
            icon = createIconText('\uf007');
        }
        icon.fillProperty().bind(control.textFillProperty());
        control.setGraphic(icon);
        control.setGraphicTextGap(6);
    }

    private Text createIconText(Character c) {
        Text iconT = new Text(c + "");
        iconT.setFill(Color.WHITE);
        iconT.setFont(Font.loadFont("http://netdna.bootstrapcdn.com/font-awesome/3.2.1/font/fontawesome-webfont.ttf", 24));
        return iconT;
    }
}
