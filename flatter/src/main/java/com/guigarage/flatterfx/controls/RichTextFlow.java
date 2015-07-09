package com.guigarage.flatterfx.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.TextFlow;

import com.guigarage.flatterfx.emoji.EmojiFactory;

public class RichTextFlow extends TextFlow {

	private StringProperty text;
	
	public StringProperty textProperty() {
		if(text == null) {
			text = new SimpleStringProperty();
			text.addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					getChildren().clear();
					getChildren().addAll(EmojiFactory.getInstance().createChildrenForTextFlow(newValue, 32.0));	
				}
			});
		}
		return text;
	}
	
	public void setText(String text) {
		textProperty().set(text);
	}
	
	public String getText() {
		return textProperty().get();
	}
	
}
