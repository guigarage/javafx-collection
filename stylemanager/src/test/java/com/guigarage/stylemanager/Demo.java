package com.guigarage.stylemanager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import com.sun.javafx.scene.control.skin.caspian.CaspianStyle;
import com.sun.javafx.scene.control.skin.modena.ModenaStyle;

public class Demo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button switchToModenaSkinButton = new Button("switch to Modena");
		switchToModenaSkinButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ApplicationStyleManager.getInstance().styleApplication(ModenaStyle.class.getName());
			}
		});

		Button switchToCaspianSkinButton = new Button("switch to Caspian");
		switchToCaspianSkinButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ApplicationStyleManager.getInstance().styleApplication(CaspianStyle.class.getName());
			}
		});

		FlowPane pane = new FlowPane();
		pane.setVgap(12.0);
		pane.setHgap(12.0);
		pane.getChildren().add(switchToModenaSkinButton);
		pane.getChildren().add(switchToCaspianSkinButton);
		Scene myScene = new Scene(pane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
