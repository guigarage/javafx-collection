package com.guigarage.flatterfx.touch.demos;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.controls.RichTextFlow;
import com.guigarage.flatterfx.emoji.Emoji;

public class EmojiText extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlatterFX.style();
		VBox box = new VBox();
		box.setSpacing(10);
		box.setPadding(new Insets(15));
		final RichTextFlow emojiTextFlow = new RichTextFlow();
		emojiTextFlow.setText("Hello! This is Emojy style!");
		box.getChildren().add(emojiTextFlow);
		
		Button b = new Button("Emoji it");
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				emojiTextFlow.setText("Hello! This is Emoji style! " + Emoji.U_F627.getCodePoint() + " COOL! " +  Emoji.U_F47B.getCodePoint());
			}
		});
		box.getChildren().add(b);
		
		Scene myScene = new Scene(box);
		primaryStage.setScene(myScene);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
