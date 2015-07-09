package com.guigarage.flatterfx.overlay;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PopupLayerController {

	@FXML
	private Label titleLabel;
	
	@FXML
	private ImageView iconView;
	
	@FXML
	private StackPane nodeWrapper;
	
	public PopupLayerController() {
	}
	
	public void update(String title, Image icon, Node node) {
		titleLabel.setText(title);
		iconView.setImage(icon);
		nodeWrapper.getChildren().clear();
		nodeWrapper.getChildren().add(node);
	}
}
