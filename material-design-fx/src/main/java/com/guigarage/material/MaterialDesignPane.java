package com.guigarage.material;

import com.guigarage.controls.MaximizePane;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MaterialDesignPane extends VBox {

    private MaterialToolbar toolbar;

    private MaximizePane contentWrapper;

    public MaterialDesignPane() {
        toolbar = new MaterialToolbar();

        MaximizePane contentPadding = new MaximizePane();
        VBox.setVgrow(contentPadding, Priority.ALWAYS);
        contentPadding.setId("content-padding");

        contentWrapper = new MaximizePane();
        VBox.setVgrow(contentWrapper, Priority.ALWAYS);
        contentWrapper.setId("content-wrapper");
        contentPadding.getChildren().add(contentWrapper);

        //TODO: AncorPane nutzen damit der Toolbar im Mini-Ansicht to Front sein kann.
        setId("background-pane");
        getChildren().addAll(toolbar, contentPadding);
        getStylesheets().add(MaterialDesignPane.class.getResource("material-design-skin.css").toExternalForm());
    }

    public void setContent(Node content) {
        contentWrapper.getChildren().clear();
        contentWrapper.getChildren().add(content);
    }

    public MaterialToolbar getToolbar() {
        return toolbar;
    }
}
