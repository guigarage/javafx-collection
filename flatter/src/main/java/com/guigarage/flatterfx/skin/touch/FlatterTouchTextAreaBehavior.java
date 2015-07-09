package com.guigarage.flatterfx.skin.touch;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;

public class FlatterTouchTextAreaBehavior extends TextAreaBehavior {

	public FlatterTouchTextAreaBehavior(TextArea textArea) {
		super(textArea);
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
