package com.guigarage.demos.flatter;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import com.guigarage.responsive.ResponsiveHandler;
import com.guigarage.ui.BasicUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 12.09.14
 * Time: 17:04
 */
public class ResponsiveFlatterDemo extends Application {
    private static final int LARGE_SPACER         = 15;
    private static final int MEDIUM_SPACER        = 10;
    private static final int SMALL_SPACER         = 5;
    private static final int EXTREME_SMALL_SPACER = 2;

    private ImageView        javaOneImage;
    private ImageView        dukeImage;
    private ImageView        javaCupImage;
    private Button           topButton1;
    private Button           topButton2;
    private GridPane         topPane;

    private TextArea         textArea;
    private StackPane        centerPane;

    private Button           bottomButton1;
    private Button           bottomButton2;
    private Button           bottomButton3;
    private Button           bottomButton4;
    private GridPane         bottomPane;

    private VBox             mainPane;

    private StackPane        pane;



    // ******************** Initialization ************************************
    @Override public void init() {
        initGraphics();
        registerListeners();
    }

    private void initGraphics() {
        // Top area
        javaOneImage = new ImageView(new Image(BasicUtils.getResourceUrl(ResponsiveFlatterDemo.class, "JavaOne.png")));
        javaOneImage.setId("java-one-image");

        dukeImage = new ImageView(new Image(BasicUtils.getResourceUrl(ResponsiveFlatterDemo.class, "duke.png")));
        dukeImage.setPreserveRatio(true);
        topButton1 = new Button("Responsive");
        topButton1.setGraphic(dukeImage);
        topButton1.getStyleClass().addAll("large-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        topButton1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        javaCupImage = new ImageView(new Image(BasicUtils.getResourceUrl(ResponsiveFlatterDemo.class, "javacup.png")));
        javaCupImage.setPreserveRatio(true);
        topButton2 = new Button("Responsive");
        topButton2.setGraphic(javaCupImage);
        topButton2.getStyleClass().addAll("large-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        topButton2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        topPane = new GridPane();
        topPane.getChildren().addAll(javaOneImage, topButton1, topButton2);
        topPane.setAlignment(Pos.CENTER);
        topPane.getStyleClass().addAll("grid", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        GridPane.setHgrow(javaOneImage, Priority.NEVER);
        GridPane.setHgrow(topButton1, Priority.ALWAYS);
        GridPane.setHgrow(topButton2, Priority.ALWAYS);


        // Center area
        textArea = new TextArea("The quick brown fox jumps over the lazy dog");
        textArea.setId("text-area");
        textArea.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "visible-xs");
        textArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        centerPane = new StackPane(textArea);


        // Bottom area
        bottomButton1 = new Button("Button");
        bottomButton1.getStyleClass().addAll("small-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        bottomButton1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        bottomButton2 = new Button("Button");
        bottomButton2.getStyleClass().addAll("small-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        bottomButton2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        bottomButton3 = new Button("Button");
        bottomButton3.getStyleClass().addAll("small-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        bottomButton3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        bottomButton4 = new Button("Button");
        bottomButton4.getStyleClass().addAll("small-button", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        bottomButton4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        bottomPane = new GridPane();
        bottomPane.getChildren().addAll(bottomButton1, bottomButton2, bottomButton3, bottomButton4);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getStyleClass().addAll("grid", "visible-lg", "visible-md", "visible-sm", "visible-xs");
        GridPane.setHgrow(bottomButton1, Priority.ALWAYS);
        GridPane.setHgrow(bottomButton2, Priority.ALWAYS);
        GridPane.setHgrow(bottomButton3, Priority.ALWAYS);
        GridPane.setHgrow(bottomButton4, Priority.ALWAYS);


        // Main pane
        mainPane = new VBox(topPane, centerPane, bottomPane);
        mainPane.setId("main-pane");
        mainPane.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "visible-xs");
        VBox.setVgrow(textArea, Priority.ALWAYS);

        pane = new StackPane(mainPane);
    }

    private void registerListeners() {
        ResponsiveHandler.setOnDeviceTypeChanged((ov, oldDeviceType, newDeviceType) -> {
            switch (newDeviceType) {
                case LARGE      : adjustToLargeDevice();        break;
                case MEDIUM     : adjustToMediumDevice();       break;
                case SMALL      : adjustToSmallDevice();        break;
                case EXTRA_SMALL: adjustToExtremeSmallDevice(); break;
            }
        });
    }


    // ******************** Methods *******************************************
    private void adjustToLargeDevice() {
        javaOneImage.setVisible(true);
        javaOneImage.setManaged(true);

        textArea.setManaged(true);

        dukeImage.setFitWidth(24);
        javaCupImage.setFitWidth(32);

        GridPane.setColumnIndex(javaOneImage, 0);
        GridPane.setRowIndex(javaOneImage, 0);
        GridPane.setColumnIndex(topButton1, 1);
        GridPane.setRowIndex(topButton1, 0);
        GridPane.setColumnIndex(topButton2, 2);
        GridPane.setRowIndex(topButton2, 0);

        bottomPane.setHgap(LARGE_SPACER);
        GridPane.setColumnIndex(bottomButton1, 0);
        GridPane.setRowIndex(bottomButton1, 0);
        GridPane.setColumnIndex(bottomButton2, 1);
        GridPane.setRowIndex(bottomButton2, 0);
        GridPane.setColumnIndex(bottomButton3, 2);
        GridPane.setRowIndex(bottomButton3, 0);
        GridPane.setColumnIndex(bottomButton4, 3);
        GridPane.setRowIndex(bottomButton4, 0);

        mainPane.setSpacing(LARGE_SPACER);
        mainPane.setPadding(new Insets(LARGE_SPACER));
    }
    private void adjustToMediumDevice() {
        javaOneImage.setVisible(false);
        javaOneImage.setManaged(false);

        textArea.setManaged(true);

        dukeImage.setFitWidth(19);
        javaCupImage.setFitWidth(26);

        GridPane.setColumnIndex(topButton1, 0);
        GridPane.setRowIndex(topButton1, 0);
        GridPane.setColumnIndex(topButton2, 0);
        GridPane.setRowIndex(topButton2, 1);

        GridPane.setColumnIndex(bottomButton1, 0);
        GridPane.setRowIndex(bottomButton1, 0);
        GridPane.setColumnIndex(bottomButton2, 1);
        GridPane.setRowIndex(bottomButton2, 0);
        GridPane.setColumnIndex(bottomButton3, 0);
        GridPane.setRowIndex(bottomButton3, 1);
        GridPane.setColumnIndex(bottomButton4, 1);
        GridPane.setRowIndex(bottomButton4, 1);

        mainPane.setSpacing(MEDIUM_SPACER);
        mainPane.setPadding(new Insets(MEDIUM_SPACER));
    }
    private void adjustToSmallDevice() {
        javaOneImage.setVisible(false);
        javaOneImage.setManaged(false);

        textArea.setManaged(true);

        dukeImage.setFitWidth(18);
        javaCupImage.setFitWidth(24);

        GridPane.setColumnIndex(topButton1, 0);
        GridPane.setRowIndex(topButton1, 0);
        GridPane.setColumnIndex(topButton2, 0);
        GridPane.setRowIndex(topButton2, 1);

        GridPane.setColumnIndex(bottomButton1, 0);
        GridPane.setRowIndex(bottomButton1, 0);
        GridPane.setColumnIndex(bottomButton2, 0);
        GridPane.setRowIndex(bottomButton2, 1);
        GridPane.setColumnIndex(bottomButton3, 0);
        GridPane.setRowIndex(bottomButton3, 2);
        GridPane.setColumnIndex(bottomButton4, 0);
        GridPane.setRowIndex(bottomButton4, 3);

        mainPane.setSpacing(SMALL_SPACER);
        mainPane.setPadding(new Insets(SMALL_SPACER));
    }
    private void adjustToExtremeSmallDevice() {
        javaOneImage.setVisible(false);
        javaOneImage.setManaged(false);

        textArea.setManaged(false);

        GridPane.setColumnIndex(topButton1, 0);
        GridPane.setRowIndex(topButton1, 0);
        GridPane.setColumnIndex(topButton2, 1);
        GridPane.setRowIndex(topButton2, 0);

        GridPane.setColumnIndex(bottomButton1, 0);
        GridPane.setRowIndex(bottomButton1, 0);
        GridPane.setColumnIndex(bottomButton2, 1);
        GridPane.setRowIndex(bottomButton2, 0);
        GridPane.setColumnIndex(bottomButton3, 0);
        GridPane.setRowIndex(bottomButton3, 1);
        GridPane.setColumnIndex(bottomButton4, 1);
        GridPane.setRowIndex(bottomButton4, 1);

        mainPane.setSpacing(EXTREME_SMALL_SPACER);
        mainPane.setPadding(new Insets(EXTREME_SMALL_SPACER));
    }


    // ******************** Application related *******************************
    @Override public void start(Stage stage) throws Exception {
        FlatterFX.style(FlatterInputType.TOUCH);

        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(BasicUtils.getResourceUrl(ResponsiveFlatterDemo.class, "demo-skin.css"));

        stage.setScene(scene);
        stage.setTitle("ResponsiveFlatterFX");
        ResponsiveHandler.addResponsiveToWindow(stage);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
