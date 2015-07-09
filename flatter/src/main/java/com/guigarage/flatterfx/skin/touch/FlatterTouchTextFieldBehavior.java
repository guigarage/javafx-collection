package com.guigarage.flatterfx.skin.touch;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.behavior.TextFieldBehavior;

public class FlatterTouchTextFieldBehavior extends TextFieldBehavior {

	public FlatterTouchTextFieldBehavior(TextField textField) {
		super(textField);
	}

	@Override
	protected String matchActionForEvent(KeyEvent e) {
		String action = TouchFlattertTraversalHelper.matchActionForEvent(e, getControl().getEffectiveNodeOrientation());
		if(action != null) {
			return action;
		}
		return super.matchActionForEvent(e);
	}

}
