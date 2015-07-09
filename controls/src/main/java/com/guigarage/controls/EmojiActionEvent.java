package com.guigarage.controls;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by hendrikebbers on 20.09.14.
 */
public class EmojiActionEvent extends Event {

    public static final EventType<EmojiActionEvent> EMOJI_ACTION =
            new EventType<EmojiActionEvent>(Event.ANY, "EMOJI-ACTION");
    private Emoji emoji;

    public EmojiActionEvent(Emoji emoji) {
        super(EMOJI_ACTION);
        this.emoji = emoji;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
