package com.guigarage.demos.flatter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import com.guigarage.flatterfx.skin.FlatterTextFieldSkin;

public class FlatterDemoDialog extends Application {
    @Override public void start(Stage stage) throws Exception {
        stage.setTitle("FlatterFX");

        FlatterFX.style(FlatterInputType.TOUCH);
        
        HBox mainbox = new HBox();
        mainbox.setSpacing(30);
        mainbox.setPadding(new Insets(20));

        VBox col1 = new VBox();
        col1.setSpacing(30);
        col1.setPadding(new Insets(50));

        Label label = new Label("Label");
        col1.getChildren().add(label);
        label.setPrefWidth(300);

        Button button1 = new Button("Button 1");
        col1.getChildren().add(button1);

        Button button2 = new Button("Button 2");
        col1.getChildren().add(button2);

        ToggleButton button3 = new ToggleButton("Toggle");
        col1.getChildren().add(button3);

        ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList("Choice 1", "Choice 2", "Choice 3"));
        cb.getSelectionModel().selectFirst();
        cb.autosize();
        col1.getChildren().add(cb);

        VBox col2 = new VBox();
        col2.setSpacing(30);
        col2.setPadding(new Insets(50));
        
        HBox checks = new HBox();
        checks.setSpacing(30);
        CheckBox checkbox = new CheckBox("CheckBox");
        checks.getChildren().add(checkbox);
        CheckBox checkbox1 = new CheckBox("CheckBox");
        checkbox1.setSelected(true);
        checks.getChildren().add(checkbox1);
        CheckBox checkbox2 = new CheckBox("CheckBox");
        checkbox2.setIndeterminate(true);
        checks.getChildren().add(checkbox2);
        col2.getChildren().add(checks);
        
        HBox radios = new HBox();
        radios.setSpacing(30);
        RadioButton radiobutton = new RadioButton("RadioButton");
        radios.getChildren().add(radiobutton);
        RadioButton radiobutton1 = new RadioButton("RadioButton");
        radiobutton1.setSelected(true);
        radios.getChildren().add(radiobutton1);
        col2.getChildren().add(radios);
        
        HBox tfbox = new HBox();
        tfbox.setSpacing(10);
        TextField text = new TextField();
        text.setPromptText("Benutzername");
        text.setSkin(new FlatterTextFieldSkin(text));
        PasswordField pw = new PasswordField();
        pw.setPromptText("Password");
        tfbox.getChildren().addAll(text, pw);
        col2.getChildren().add(tfbox);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Benutzername in TextArea");
        textArea.setPrefRowCount(4);
        col2.getChildren().add(textArea);

        HBox progressBox = new HBox();
        progressBox.setSpacing(30);
        ProgressIndicator pi = new ProgressIndicator(-1.0);
        ProgressIndicator pi2 = new ProgressIndicator(0.67);
        pi2.setPrefWidth(100);
        ProgressBar pb = new ProgressBar(-1.0);
        pb.setPrefWidth(200);
        ProgressBar pb2 = new ProgressBar(0.67);
        pb2.setPrefWidth(200);
        progressBox.getChildren().addAll(pi, pi2, pb, pb2);
        col2.getChildren().add(progressBox);

        mainbox.getChildren().addAll(col1, col2);
        Scene scene = new Scene(mainbox, 1280, 720);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
