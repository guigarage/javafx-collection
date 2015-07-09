package com.guigarage.flatterfx.emoji.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import com.guigarage.flatterfx.emoji.Emoji;
import com.guigarage.flatterfx.emoji.EmojiFactory;

public class EmojiIconView extends ImageView {

	private Emoji emoji;

	public EmojiIconView(Emoji emoji) {
		this(emoji, 32.0);
	}
	
	public EmojiIconView(Emoji emoji, double size) {
		this.emoji = emoji;
		setImage(EmojiFactory.getInstance().createEmojiImage(this.emoji));
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				EventHandler<EmojiEvent> emojiEventHandler = getOnAction();
				if(emojiEventHandler != null) {
					emojiEventHandler.handle(new EmojiEvent(getEmoji()));
				}
			}
		});
		setFitHeight(size);
		setFitWidth(size);
	}

	public Emoji getEmoji() {
		return emoji;
	}
	
	public final ObjectProperty<EventHandler<EmojiEvent>> onActionProperty() {
		return onAction;
	}

	public final void setOnAction(EventHandler<EmojiEvent> value) {
		onActionProperty().set(value);
	}

	public final EventHandler<EmojiEvent> getOnAction() {
		return onActionProperty().get();
	}

	private ObjectProperty<EventHandler<EmojiEvent>> onAction = new ObjectPropertyBase<EventHandler<EmojiEvent>>() {
		@Override
		protected void invalidated() {
			setEventHandler(EmojiEvent.EMOJI, get());
		}

		@Override
		public Object getBean() {
			return EmojiIconView.this;
		}

		@Override
		public String getName() {
			return "onEmoji";
		}
	};

}
