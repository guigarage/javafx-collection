package com.guigarage.demos.itunes.controller;

import com.guigarage.controls.IconifiedButtonSkin;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.material.MaterialToolbar;
import io.datafx.controller.FXMLController;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@FXMLController("search.fxml")
public class SearchController {

    @FXML
    @ActionTrigger("search")
    private TextField searchField;

    @FXML
    @ActionTrigger("search")
    private Button goButton;

    @FXML
    private StackPane logoPane;

    @Inject
    private ITunesDataModel dataModel;

    @ActionHandler
    private FlowActionHandler actionHandler;

    @PostConstruct
    public void init() {
        Button logoButton = new Button();
        IconifiedButtonSkin.addStyleClass(logoButton);
        logoButton.setId("logoButton");
        logoButton.setOnAction(e -> System.out.println(logoButton.getFont()));

        IconifiedButtonSkin.addStyleClass(goButton);
        System.out.println(logoButton.getFont());
        logoPane.getChildren().add(logoButton);
    }

    @ActionMethod("search")
    public void search() {
        dataModel.updateBySearch(searchField.getText());
        try {
            actionHandler.navigate(AlbumOverviewController.class);
            ToolbarHelper.changeToAlbumOverview(ApplicationContext.getInstance().getRegisteredObject(MaterialToolbar.class), actionHandler, dataModel);
        } catch (Exception e) {
            actionHandler.getExceptionHandler().setException(e);
        }
    }
}
