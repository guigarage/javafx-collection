package com.guigarage.flatterfx.skin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

import com.guigarage.flatterfx.controls.RichTextFlow;
import com.guigarage.flatterfx.emoji.EmojiFactory;
import com.sun.javafx.scene.control.skin.LabelSkin;

public class FlatterLabelSkin extends LabelSkin {

	private RichTextFlow flow;

	public FlatterLabelSkin(Label label) {
		super(label);
		flow = new RichTextFlow();
		label.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateChildren();
			}
		});
	}

	protected void updateChildren() {
		final Labeled labeled = getSkinnable();
		flow.getChildren().addAll(
				EmojiFactory.getInstance().createChildrenForTextFlow(
						labeled.getText(), 32.0));
		// TODO: Text entfenen und flow rein packen...
	}

	protected void layoutLabelInArea(double x, double y, double w, double h,
			Pos alignment) {
		super.layoutLabelInArea(x, y, w, h, alignment);
		// TODO: position vom TextFlow anpassen...
	}

	
}
