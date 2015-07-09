package com.guigarage.flatterfx.skin;

import javafx.scene.control.ComboBoxBase;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;

public class FlatterComboBoxBehavior<T> extends ComboBoxBaseBehavior<T> {

	public FlatterComboBoxBehavior(ComboBoxBase<T> comboBox) {
		super(comboBox, null);
	}

}
