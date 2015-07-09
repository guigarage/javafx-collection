package com.guigarage.extreme;

import javafx.geometry.Side;
import javafx.scene.Node;

public class ExtendedExtremeMenu extends BasicExtremeMenu {

    private Node left;
    private Node right;
    private Node centered;

    public ExtendedExtremeMenu(Node left, Node centered, Node right) {
        this(left, centered, right, Side.BOTTOM);
    }

    public ExtendedExtremeMenu(Node left, Node centered, Node right, Side side) {
        super(side);
        this.left = left;
        this.right = right;
        this.centered = centered;

        if (left != null) {
            getChildren().add(left);
        }
        if (right != null) {
            getChildren().add(right);
        }
        if (centered != null) {
            getChildren().add(centered);
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double leftWidth = 0;
        if (left != null) {
            leftWidth = left.prefWidth(getHeight());
            left.relocate(0, 0);
            left.resize(leftWidth, left.prefHeight(0));
        }

        double rightWidth = 0;
        if (right != null) {
            rightWidth = right.prefWidth(getHeight());
            right.relocate(getWidth() - rightWidth, 0);
            right.resize(rightWidth, right.prefHeight(0));
        }

        if (centered != null) {
            double width = getWidth() - leftWidth - rightWidth;
            centered.relocate((getWidth() - width) / 2, 0);
            centered.resize(width, centered.prefHeight(0));
        }
    }

    @Override
    protected double computePrefHeight(double width) {
        return super.computePrefHeight(width);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

