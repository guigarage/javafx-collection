package com.guigarage.extreme;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public class WithMenuPane extends Region {

    private Node topMenu;

    private Node bottomMenu;

    private Node content;

    public WithMenuPane(){
        this(null, null, null);
    }

    public WithMenuPane(Node topMenu, Node bottomMenu, Node content) {
        this.bottomMenu = bottomMenu;
        this.topMenu = topMenu;
        this.content = content;

        if(content != null) {
            getChildren().add(content);
        }

        if(bottomMenu != null) {
            getChildren().add(bottomMenu);
            bottomMenu.toFront();
        }

        if(topMenu != null) {
            getChildren().add(topMenu);
            topMenu.toFront();
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if(topMenu != null) {
            double menuHeight = topMenu.prefHeight(getWidth());
            topMenu.resize(getWidth(), menuHeight);
            topMenu.relocate(0, 0);
            topMenu.toFront();
        }
        if(bottomMenu != null) {
            double menuHeight = bottomMenu.prefHeight(getWidth());
            bottomMenu.resize(getWidth(), menuHeight);
            bottomMenu.relocate(0, getHeight() - menuHeight);
            bottomMenu.toFront();
        }
        if(content != null) {
            content.relocate(0, 0);
            content.resize(getWidth(), getHeight());
        }
    }

    public void setTopMenu(Node topMenu) {
        if(this.topMenu != null) {
            getChildren().remove(this.topMenu);
        }
        this.topMenu = topMenu;
        getChildren().add(topMenu);
    }

    public void setBottomMenu(Node bottomMenu) {
        if(this.bottomMenu != null) {
            getChildren().remove(this.bottomMenu);
        }
        this.bottomMenu = bottomMenu;
        getChildren().add(bottomMenu);
    }

    public void setContent(Node content) {
        if(this.content != null) {
            getChildren().remove(this.content);
        }
        this.content = content;
        getChildren().add(content);
    }
}
