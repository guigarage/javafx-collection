package com.guigarage.flatterfx.overlay;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import com.guigarage.flatterfx.util.BlurPane;

public class DefaultOverlay extends BlurPane implements OverlayPane {

    private Map<UUID, Node> visibleNodes;

    private Label titleLabel;
    private ImageView iconView;
    private StackPane nodeWrapper;
    private StackPane backgroundNode;

    public DefaultOverlay() {
        visibleNodes = new HashMap<>();
        setVisible(false);
    }

    @Override public UUID show(String title, Image icon, Node node, OverlayType type) {
        UUID id = UUID.randomUUID();
        Node visibleNode = createSkinnedNode(title, icon, node, type);
        visibleNodes.put(id, visibleNode);
        getChildren().add(visibleNode);
        setVisible(true);
        return id;
    }

    protected Node createSkinnedNode(String title, Image icon, Node node, OverlayType type) {
            BorderPane popupPane = new BorderPane();
            popupPane.getStyleClass().add("popup-overlay");

            backgroundNode = new StackPane();
            backgroundNode.setPrefSize(200, 150);
            backgroundNode.getStyleClass().add("content-background");
            popupPane.setCenter(backgroundNode);
            
            // ContentArea
            nodeWrapper = new StackPane();
            nodeWrapper.getStyleClass().add("content-area");
            backgroundNode.getChildren().add(nodeWrapper);

            FlowPane titlePane = new FlowPane();
            titlePane.getStyleClass().add("title-bar");
            Image image = new Image(DefaultOverlay.class.getResource("defaultListIcon.png").toExternalForm());
            iconView = new ImageView();
            iconView.setFitHeight(48);
            iconView.setFitWidth(48);
            iconView.setImage(image);
            iconView.getStyleClass().add("icon");
            titlePane.getChildren().add(iconView);
            
            titleLabel = new Label();
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setPrefWidth(getWidth() * (2d / 3d) - 100);
            titlePane.getChildren().add(titleLabel);
            
            popupPane.setTop(titlePane);
            
            update(title, icon, node);

            popupPane.setMaxWidth(getWidth() * (2d / 3d));
            popupPane.setMaxHeight(getHeight() * (2d / 3d));
            return popupPane;
    }

    private void update(String title, Image icon, Node node) {
        titleLabel.setText(title);
        iconView.setImage(icon);
        nodeWrapper.getChildren().clear();
        nodeWrapper.getChildren().add(node);
    }

    @Override public void hide(UUID showId) {
        Node n = visibleNodes.get(showId);
        if (n != null) {
            getChildren().remove(n);
            visibleNodes.remove(showId);
        }
        if (visibleNodes.isEmpty()) {
            setVisible(false);
        }
    }
}
