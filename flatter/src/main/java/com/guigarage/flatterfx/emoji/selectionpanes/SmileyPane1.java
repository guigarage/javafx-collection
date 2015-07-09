package com.guigarage.flatterfx.emoji.selectionpanes;

import com.guigarage.flatterfx.emoji.Emoji;
import com.guigarage.flatterfx.emoji.controls.EmojiEvent;
import com.guigarage.flatterfx.emoji.controls.EmojiIconView;

import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class SmileyPane1 extends GridPane implements EventHandler<EmojiEvent>{

	public SmileyPane1() {
		addRow(0, createIconView(Emoji.U_F627));
	}
	
	private EmojiIconView createIconView(Emoji emoji) {
		EmojiIconView emojiIconView = new EmojiIconView(emoji);
		emojiIconView.setOnAction(this);
		return emojiIconView;
	}

	@Override
	public void handle(EmojiEvent event) {
		
	}
	
	
}
