package com.guigarage.controls;

import com.guigarage.ui.BasicUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendrikebbers on 20.09.14.
 */
public class EmojiUtil {

    public static List<Emoji> lastUsedEmojies = new ArrayList<>();

    private static int maxLastUsedEmojies = 32;

    public static Popup showEmojiPopup(Node node, boolean closeAfterOneEvent, EventHandler<EmojiActionEvent> onEmojiAction) {
        Popup popup = new Popup();
        BorderPane pane = new BorderPane();
        popup.getContent().add(pane);

        if (closeAfterOneEvent) {
            pane.setCenter(createEmoticonsPane(e -> {
                popup.hide();
                onEmojiAction.handle(e);
            }));
        } else {
            pane.setCenter(createEmoticonsPane(onEmojiAction));
        }

        Bounds onScreen = node.localToScreen(node.getLayoutBounds());
        popup.show(node, onScreen.getMinX(), onScreen.getMaxY());
        return popup;
    }

    public static Node createEmoticonsPane(EventHandler<EmojiActionEvent> onEmojiAction) {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("emoji-pane");
        pane.getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        //TODO: change to right emojies
        addRowToGrid(pane, 0, onEmojiAction, Emoji.E_0000, Emoji.E_0001, Emoji.E_0002, Emoji.E_0003, Emoji.E_0004, Emoji.E_0005, Emoji.E_0006, Emoji.E_0007);
        addRowToGrid(pane, 1, onEmojiAction, Emoji.E_0008, Emoji.E_0009, Emoji.E_0010, Emoji.E_0011, Emoji.E_0012, Emoji.E_0013, Emoji.E_0014, Emoji.E_0015);
        addRowToGrid(pane, 2, onEmojiAction, Emoji.E_0016, Emoji.E_0017, Emoji.E_0018, Emoji.E_0019, Emoji.E_0020, Emoji.E_0021, Emoji.E_0022, Emoji.E_0023);
        addRowToGrid(pane, 3, onEmojiAction, Emoji.E_0024, Emoji.E_0025, Emoji.E_0026, Emoji.E_0027, Emoji.E_0028, Emoji.E_0029, Emoji.E_0030, Emoji.E_0031);
        addRowToGrid(pane, 4, onEmojiAction, Emoji.E_0032, Emoji.E_0033, Emoji.E_0034, Emoji.E_0035, Emoji.E_0036, Emoji.E_0037, Emoji.E_0038, Emoji.E_0039);
        addRowToGrid(pane, 5, onEmojiAction, Emoji.E_0040, Emoji.E_0041, Emoji.E_0042, Emoji.E_0043, Emoji.E_0044, Emoji.E_0045, Emoji.E_0046, Emoji.E_0047);
        addRowToGrid(pane, 6, onEmojiAction, Emoji.E_0048, Emoji.E_0049, Emoji.E_0050, Emoji.E_0051, Emoji.E_0052, Emoji.E_0053, Emoji.E_0054, Emoji.E_0055);
        return pane;
    }

    public static Node createNaturePane(EventHandler<EmojiActionEvent> onEmojiAction) {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("emoji-pane");
        pane.getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        //TODO: change to right emojies
        addRowToGrid(pane, 0, onEmojiAction, Emoji.E_0000, Emoji.E_0001, Emoji.E_0002, Emoji.E_0003, Emoji.E_0004, Emoji.E_0005, Emoji.E_0006, Emoji.E_0007);
        addRowToGrid(pane, 1, onEmojiAction, Emoji.E_0008, Emoji.E_0009, Emoji.E_0010, Emoji.E_0011, Emoji.E_0012, Emoji.E_0013, Emoji.E_0014, Emoji.E_0015);
        addRowToGrid(pane, 2, onEmojiAction, Emoji.E_0016, Emoji.E_0017, Emoji.E_0018, Emoji.E_0019, Emoji.E_0020, Emoji.E_0021, Emoji.E_0022, Emoji.E_0023);
        addRowToGrid(pane, 3, onEmojiAction, Emoji.E_0024, Emoji.E_0025, Emoji.E_0026, Emoji.E_0027, Emoji.E_0028, Emoji.E_0029, Emoji.E_0030, Emoji.E_0031);
        addRowToGrid(pane, 4, onEmojiAction, Emoji.E_0032, Emoji.E_0033, Emoji.E_0034, Emoji.E_0035, Emoji.E_0036, Emoji.E_0037, Emoji.E_0038, Emoji.E_0039);
        addRowToGrid(pane, 5, onEmojiAction, Emoji.E_0040, Emoji.E_0041, Emoji.E_0042, Emoji.E_0043, Emoji.E_0044, Emoji.E_0045, Emoji.E_0046, Emoji.E_0047);
        addRowToGrid(pane, 6, onEmojiAction, Emoji.E_0048, Emoji.E_0049, Emoji.E_0050, Emoji.E_0051, Emoji.E_0052, Emoji.E_0053, Emoji.E_0054, Emoji.E_0055);
        return pane;
    }

    public static Node createEnvironmentsPane(EventHandler<EmojiActionEvent> onEmojiAction) {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("emoji-pane");
        pane.getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        //TODO: change to right emojies
        addRowToGrid(pane, 0, onEmojiAction, Emoji.E_0000, Emoji.E_0001, Emoji.E_0002, Emoji.E_0003, Emoji.E_0004, Emoji.E_0005, Emoji.E_0006, Emoji.E_0007);
        addRowToGrid(pane, 1, onEmojiAction, Emoji.E_0008, Emoji.E_0009, Emoji.E_0010, Emoji.E_0011, Emoji.E_0012, Emoji.E_0013, Emoji.E_0014, Emoji.E_0015);
        addRowToGrid(pane, 2, onEmojiAction, Emoji.E_0016, Emoji.E_0017, Emoji.E_0018, Emoji.E_0019, Emoji.E_0020, Emoji.E_0021, Emoji.E_0022, Emoji.E_0023);
        addRowToGrid(pane, 3, onEmojiAction, Emoji.E_0024, Emoji.E_0025, Emoji.E_0026, Emoji.E_0027, Emoji.E_0028, Emoji.E_0029, Emoji.E_0030, Emoji.E_0031);
        addRowToGrid(pane, 4, onEmojiAction, Emoji.E_0032, Emoji.E_0033, Emoji.E_0034, Emoji.E_0035, Emoji.E_0036, Emoji.E_0037, Emoji.E_0038, Emoji.E_0039);
        addRowToGrid(pane, 5, onEmojiAction, Emoji.E_0040, Emoji.E_0041, Emoji.E_0042, Emoji.E_0043, Emoji.E_0044, Emoji.E_0045, Emoji.E_0046, Emoji.E_0047);
        addRowToGrid(pane, 6, onEmojiAction, Emoji.E_0048, Emoji.E_0049, Emoji.E_0050, Emoji.E_0051, Emoji.E_0052, Emoji.E_0053, Emoji.E_0054, Emoji.E_0055);
        return pane;
    }

    public static Node createVehiclesPane(EventHandler<EmojiActionEvent> onEmojiAction) {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("emoji-pane");
        pane.getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        //TODO: change to right emojies
        addRowToGrid(pane, 0, onEmojiAction, Emoji.E_0000, Emoji.E_0001, Emoji.E_0002, Emoji.E_0003, Emoji.E_0004, Emoji.E_0005, Emoji.E_0006, Emoji.E_0007);
        addRowToGrid(pane, 1, onEmojiAction, Emoji.E_0008, Emoji.E_0009, Emoji.E_0010, Emoji.E_0011, Emoji.E_0012, Emoji.E_0013, Emoji.E_0014, Emoji.E_0015);
        addRowToGrid(pane, 2, onEmojiAction, Emoji.E_0016, Emoji.E_0017, Emoji.E_0018, Emoji.E_0019, Emoji.E_0020, Emoji.E_0021, Emoji.E_0022, Emoji.E_0023);
        addRowToGrid(pane, 3, onEmojiAction, Emoji.E_0024, Emoji.E_0025, Emoji.E_0026, Emoji.E_0027, Emoji.E_0028, Emoji.E_0029, Emoji.E_0030, Emoji.E_0031);
        addRowToGrid(pane, 4, onEmojiAction, Emoji.E_0032, Emoji.E_0033, Emoji.E_0034, Emoji.E_0035, Emoji.E_0036, Emoji.E_0037, Emoji.E_0038, Emoji.E_0039);
        addRowToGrid(pane, 5, onEmojiAction, Emoji.E_0040, Emoji.E_0041, Emoji.E_0042, Emoji.E_0043, Emoji.E_0044, Emoji.E_0045, Emoji.E_0046, Emoji.E_0047);
        addRowToGrid(pane, 6, onEmojiAction, Emoji.E_0048, Emoji.E_0049, Emoji.E_0050, Emoji.E_0051, Emoji.E_0052, Emoji.E_0053, Emoji.E_0054, Emoji.E_0055);
        return pane;
    }

    public static Node createSymbolesPane(EventHandler<EmojiActionEvent> onEmojiAction) {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("emoji-pane");
        pane.getStylesheets().add(BasicUtils.getResourceUrl(EmojiButton.class, "emoji.css"));
        //TODO: change to right emojies
        addRowToGrid(pane, 0, onEmojiAction, Emoji.E_0000, Emoji.E_0001, Emoji.E_0002, Emoji.E_0003, Emoji.E_0004, Emoji.E_0005, Emoji.E_0006, Emoji.E_0007);
        addRowToGrid(pane, 1, onEmojiAction, Emoji.E_0008, Emoji.E_0009, Emoji.E_0010, Emoji.E_0011, Emoji.E_0012, Emoji.E_0013, Emoji.E_0014, Emoji.E_0015);
        addRowToGrid(pane, 2, onEmojiAction, Emoji.E_0016, Emoji.E_0017, Emoji.E_0018, Emoji.E_0019, Emoji.E_0020, Emoji.E_0021, Emoji.E_0022, Emoji.E_0023);
        addRowToGrid(pane, 3, onEmojiAction, Emoji.E_0024, Emoji.E_0025, Emoji.E_0026, Emoji.E_0027, Emoji.E_0028, Emoji.E_0029, Emoji.E_0030, Emoji.E_0031);
        addRowToGrid(pane, 4, onEmojiAction, Emoji.E_0032, Emoji.E_0033, Emoji.E_0034, Emoji.E_0035, Emoji.E_0036, Emoji.E_0037, Emoji.E_0038, Emoji.E_0039);
        addRowToGrid(pane, 5, onEmojiAction, Emoji.E_0040, Emoji.E_0041, Emoji.E_0042, Emoji.E_0043, Emoji.E_0044, Emoji.E_0045, Emoji.E_0046, Emoji.E_0047);
        addRowToGrid(pane, 6, onEmojiAction, Emoji.E_0048, Emoji.E_0049, Emoji.E_0050, Emoji.E_0051, Emoji.E_0052, Emoji.E_0053, Emoji.E_0054, Emoji.E_0055);
        return pane;
    }

    private static void addRowToGrid(GridPane pane, int row, EventHandler<EmojiActionEvent> onEmojiAction, Emoji... emojis) {
        int columnIndex = 0;
        for (Emoji emoji : emojis) {
            EmojiButton button = new EmojiButton(emoji);
            button.setOnEmojiAction(e -> {
                lastUsedEmojies.add(e.getEmoji());
                while(lastUsedEmojies.size() > maxLastUsedEmojies) {
                    lastUsedEmojies.remove(0);
                }
                onEmojiAction.handle(e);
            });
            GridPane.setColumnIndex(button, columnIndex);
            GridPane.setRowIndex(button, row);
            pane.getChildren().add(button);
            columnIndex++;
        }
    }

}
