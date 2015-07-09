package com.guigarage.demos.material;

import com.guigarage.imaging.ImageViewer;
import com.guigarage.material.IconType;
import com.guigarage.material.MaterialDesignPane;
import com.guigarage.material.MaterialToolbar;
import com.guigarage.material.ToolbarAction;
import com.guigarage.responsive.ResponsiveHandler;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MaterialDesignDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MaterialDesignPane pane = new MaterialDesignPane();

        pane.getToolbar().setOnMouseClicked(e -> {
            double rand = Math.random();
            if (rand < 0.2) {
                pane.getToolbar().animateTo(Color.DARKORANGE, "Settings", IconType.ARROW, new ToolbarAction(null, "\uf085"));
            } else if (rand < 0.4) {
                pane.getToolbar().animateTo(Color.DARKSLATEBLUE, "Music", IconType.BACK, new ToolbarAction(null, "\uf006"), new ToolbarAction(null, "\uf0ed"));
            } else if (rand < 0.6) {
                pane.getToolbar().animateTo(Color.RED, "Photos", IconType.CLOSE, new ToolbarAction(null, "\uf02c"), new ToolbarAction(null, "\uf06e"), new ToolbarAction(null, "\uf041"));
            } else if (rand < 0.8) {
                pane.getToolbar().animateTo(Color.DEEPPINK, "Chat", IconType.PLAY, new ToolbarAction(null, "\uf095"), new ToolbarAction(null, "\uf064"), new ToolbarAction(null, "\uf03d"));
            } else {
                pane.getToolbar().animateTo(Color.DARKOLIVEGREEN, "Mail", IconType.MENU, new ToolbarAction(null, "\uf044"), new ToolbarAction(null, "\uf021"));
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        ResponsiveHandler.addResponsiveToWindow(primaryStage);
        primaryStage.show();
    }
}
