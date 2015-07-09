package com.guigarage.demos.extreme;

import com.guigarage.extreme.ExtremeMenuButton;
import com.guigarage.extreme.ExtremeMenuButtonSkin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ExtremeMenuButton button1 = new ExtremeMenuButton("Click me", '\uf040');
        button1.setSkin(new ExtremeMenuButtonSkin(button1, Duration.millis(360)));

        ExtremeMenuButton button2 = new ExtremeMenuButton("Click me", '\uf040');
        button2.setSkin(new ExtremeMenuButtonSkin(button2, Duration.seconds(1)));

        ExtremeMenuButton button3 = new ExtremeMenuButton("Click me", '\uf040');
        button3.setSkin(new ExtremeMenuButtonSkin(button3, Duration.seconds(4)));

        HBox pane = new HBox(button1, button2, button3);
        pane.setPadding(new Insets(64));
        pane.setSpacing(64);

        Slider slider = new Slider();
        slider.setMax(4);
        slider.setValue(1);
        pane.scaleXProperty().bind(slider.valueProperty());
        pane.scaleYProperty().bind(slider.valueProperty());

        VBox box = new VBox(pane, slider);


        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
