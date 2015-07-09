package com.guigarage.flatterfx.skin;

import javafx.scene.control.TextField;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import com.guigarage.flatterfx.skin.touch.FlatterTouchTextFieldBehavior;
import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
import com.sun.javafx.scene.control.skin.TextFieldSkin;

public class FlatterTextFieldSkin extends TextFieldSkin{

	public FlatterTextFieldSkin(TextField textField) {
		super(textField, getBehavior(textField));
	}

	private static TextFieldBehavior getBehavior(TextField textField) {
		if(FlatterFX.getInstance().getInputType().equals(FlatterInputType.TOUCH)) {
			return new FlatterTouchTextFieldBehavior(textField);
		}
		return new TextFieldBehavior(textField);
	}
}
