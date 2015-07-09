package com.guigarage.incubator.emoji;

import com.guigarage.controls.EmojiUtil;
import com.guigarage.controls.MaximizePane;
import com.guigarage.ui.IconFonts;
import com.sun.glass.ui.mac.PasteboardHack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.xml.transform.sax.SAXSource;
import java.nio.charset.Charset;

public class EmojiTextField extends Application {

    public static void main(String... args) {
        launch(args);
    }

    Popup popup;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        textField.getStyleClass().add("emoji");
        textField.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.DOWN)) {
                if(popup == null || !popup.isShowing()) {
                    popup = EmojiUtil.showEmojiPopup(textField, true, ev -> textField.insertText(textField.getCaretPosition(), ev.getEmoji().toString()));
                    e.consume();
                }
            } else if(e.getCode().equals(KeyCode.UP)) {
                if(popup != null && popup.isShowing()) {
                    popup.hide();
                    e.consume();
                }
            } else if(e.getCode().equals(KeyCode.V) && e.isMetaDown()) {
                textField.insertText(textField.getCaretPosition(), PasteboardHack.getUTF16String());
                e.consume();
            }
        });

        textField.textProperty().addListener(e -> {
            System.out.println("Bytes in Textfield:");
            byte[] rawBytes = textField.getText().getBytes(Charset.forName("UTF-16"));
            for(byte b : rawBytes) {
                System.out.println(b);
            }

            try {
                PasteboardHack.getUTF16String();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

           // textField.getText().codePoints().forEach(codePoint -> System.out.println("c " + codePoint));
        });


        MaximizePane pane = new MaximizePane();
        pane.setPadding(new Insets(24));
        pane.getChildren().add(textField);
        pane.setMaxWidth(800);
        Scene scene = new Scene(pane);
        IconFonts.addToScene(scene);
        scene.getStylesheets().add(EmojiTextField.class.getResource("skin.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
