package com.guigarage.flatterfx.skin.touch;

import java.util.ArrayList;

import javafx.scene.control.ComboBoxBase;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;

public class FlatterTouchComboBoxBehavior<T> extends ComboBoxBaseBehavior<T> {

	public FlatterTouchComboBoxBehavior(ComboBoxBase<T> comboBox) {
		super(comboBox, new ArrayList<KeyBinding>());
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
