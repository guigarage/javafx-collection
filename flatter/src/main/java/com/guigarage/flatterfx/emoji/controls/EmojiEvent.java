package com.guigarage.flatterfx.emoji.controls;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

import com.guigarage.flatterfx.emoji.Emoji;

public class EmojiEvent extends Event {

	private static final long serialVersionUID = 1L;

    public static final EventType<EmojiEvent> EMOJI =
            new EventType<EmojiEvent>(Event.ANY, "EMOJI");

    private Emoji emoji;
    
    public EmojiEvent(Emoji emoji) {
        super(EMOJI);
        this.emoji = emoji;
    }
    
    public EmojiEvent(Object source, EventTarget target, Emoji emoji) {
        super(source, target, EMOJI);
        this.emoji = emoji;
    }
    
    public Emoji getEmoji() {
		return emoji;
	}
}
