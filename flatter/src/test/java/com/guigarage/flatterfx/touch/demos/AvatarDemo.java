package com.guigarage.flatterfx.touch.demos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.controls.AvatarView;

public class AvatarDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlatterFX.style();
		AvatarView avatarView1 = new AvatarView();
		avatarView1.setPadding(new Insets(12));
		avatarView1.imageProperty().set(new Image(AvatarDemo.class.getResource("avatar1.png").toString()));
		
		
		AvatarView avatarView2 = new AvatarView();
		avatarView2.setPadding(new Insets(12));
		avatarView2.imageProperty().set(new Image(AvatarDemo.class.getResource("avatar2.png").toString()));
		
		VBox box = new VBox();
		box.setPadding(new Insets(12));
		box.setSpacing(24.0);
		
		box.getChildren().add(avatarView1);
		box.getChildren().add(avatarView2);
		
		Scene myScene = new Scene(box);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
