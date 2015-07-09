package com.guigarage.flatterfx.emoji;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import com.guigarage.flatterfx.controls.RichTextFlow;
import com.guigarage.flatterfx.emoji.controls.EmojiIconView;

public class EmojiFactory {

	private static EmojiFactory instance;

	private EmojiFactory() {
	}

	public static EmojiFactory getInstance() {
		if (instance == null) {
			instance = new EmojiFactory();
		}
		return instance;
	}

	public List<Node> createChildrenForTextFlow(String text) {
		return createChildrenForTextFlow(text, 64.0);
	}

	public List<Node> createChildrenForTextFlow(String text, double size) {
		List<Node> ret = new ArrayList<Node>();
		Text currentText = null;
		for (int i = 0; i < text.length(); i++) {
			int codePoint = text.codePointAt(i);

			if (codePoint >= 0xF300 && codePoint <= 0xF6FF) {
				currentText = null;
				ret.add(createEmojiView(codePoint, size));
			} else {
				if (currentText == null) {
					currentText = new Text();
					currentText.getStyleClass().add("emoji-text");
					currentText.setTextOrigin(VPos.CENTER);
					ret.add(currentText);
				}
				currentText.setText(currentText.getText() + text.charAt(i));
			}
		}
		return ret;
	}

	public TextFlow create(String text) {
		return create(text, 64.0);
	}

	public TextFlow create(String text, double size) {
		TextFlow flow = new RichTextFlow();
		flow.getChildren().addAll(createChildrenForTextFlow(text, size));
		return flow;
	}

	public ImageView createEmojiView(int codePoint) {
		return createEmojiView(codePoint, 64.0);
	}

	public ImageView createEmojiView(Emoji emoji) {
		return createEmojiView(emoji.getCodePoint(), 64.0);
	}

	public ImageView createEmojiView(int codePoint, double size) {
		return createEmojiView(Emoji.getByCodePoint(codePoint), size);
	}

	public ImageView createEmojiView(Emoji emoji, double size) {
		ImageView view = new EmojiIconView(emoji);
		view.setFitHeight(size);
		view.setFitWidth(size);
		return view;
	}

	public Image createEmojiImage(int codePoint) {
		return new Image(EmojiFactory.class.getResource(
				"1" + Integer.toHexString(codePoint) + ".png").toString());
	}

	public Image createEmojiImage(Emoji emoji) {
		return createEmojiImage(emoji.getCodePoint());
	}
}
