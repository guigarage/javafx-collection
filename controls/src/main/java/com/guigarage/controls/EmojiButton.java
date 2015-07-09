package com.guigarage.controls;

import com.guigarage.ui.BasicUtils;
import com.guigarage.ui.IconFonts;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EmojiButton extends Button {

    private ObjectProperty<Emoji> emoji;

    private BooleanProperty focusable;

    private ObjectProperty<EventHandler<EmojiActionEvent>> onEmojiAction = new ObjectPropertyBase<EventHandler<EmojiActionEvent>>() {
        @Override
        protected void invalidated() {
            setEventHandler(EmojiActionEvent.EMOJI_ACTION, get());
        }

        @Override
        public Object getBean() {
            return EmojiButton.this;
        }

        @Override
        public String getName() {
            return "onEmojiAction";
        }
    };

    public EmojiButton(Emoji emoji) {
        this.emoji = new SimpleObjectProperty<>(emoji);
        this.emoji.addListener(e -> setText(emoji.getEmoji()));
        focusable = new SimpleBooleanProperty(false);
        setText(emoji.getEmoji());
        getStyleClass().add("emoji-button");
        IconFonts.addToParent(this);
        getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        addEventHandler(ActionEvent.ACTION, e -> fireEvent(new EmojiActionEvent(getEmoji())));
    }

    @Override
    public void requestFocus() {
        if(isFocusable()) {
            super.requestFocus();
        }
    }

    public boolean isFocusable() {
        return focusable.get();
    }

    public BooleanProperty focusableProperty() {
        return focusable;
    }

    public void setFocusable(boolean focusable) {
        this.focusable.set(focusable);
    }

    public Emoji getEmoji() {
        return emoji.get();
    }

    public void setEmoji(Emoji emoji) {
        this.emoji.set(emoji);
    }

    public ObjectProperty<Emoji> emojiProperty() {
        return emoji;
    }

    public final ObjectProperty<EventHandler<EmojiActionEvent>> onEmojiActionProperty() {
        return onEmojiAction;
    }

    public final EventHandler<EmojiActionEvent> getOnEmojiAction() {
        return onEmojiActionProperty().get();
    }

    public final void setOnEmojiAction(EventHandler<EmojiActionEvent> value) {
        onEmojiActionProperty().set(value);
    }
}
