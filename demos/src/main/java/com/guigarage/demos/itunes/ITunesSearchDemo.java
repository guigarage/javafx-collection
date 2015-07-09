package com.guigarage.demos.itunes;

import com.guigarage.controls.IconifiedButtonSkin;
import com.guigarage.demos.itunes.controller.SearchController;
import com.guigarage.material.MaterialDesignPane;
import com.guigarage.responsive.ResponsiveHandler;
import com.guigarage.ui.BasicUtils;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ITunesSearchDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MaterialDesignPane materialDesignPane = new MaterialDesignPane();
        ApplicationContext.getInstance().register(materialDesignPane.getToolbar());

        Flow flow = new Flow(SearchController.class);
        FlowHandler handler = flow.createHandler();
        materialDesignPane.setContent(handler.start());

        Scene scene = new Scene(materialDesignPane);
        scene.getStylesheets().add(BasicUtils.getResourceUrl(ITunesSearchDemo.class, "skin.css"));
        IconifiedButtonSkin.addStylesheet(scene);

        primaryStage.setScene(scene);
        ResponsiveHandler.addResponsiveToWindow(primaryStage);
        primaryStage.setWidth(360);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
