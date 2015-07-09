package com.guigarage.flatterfx.touch.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.emoji.Emoji;
import com.guigarage.flatterfx.emoji.controls.EmojiIconView;

public class AllEmojis extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlatterFX.style();
		FlowPane pane = new FlowPane();
		
		for(Emoji emoji : Emoji.values()) {
			pane.getChildren().add(new EmojiIconView(emoji, 32.0));
		}
		
		Scene myScene = new Scene(pane);
		primaryStage.setScene(myScene);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
