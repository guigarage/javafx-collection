package com.guigarage.extreme;

import com.guigarage.ui.UiUtilities;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ImageDragSupport {

    public static Pane addSupport(Node toDragIn, Consumer<List<File>> droppedImageConsumer) {
        Text icon = UiUtilities.createIconText('\uf0fe', 128);
        icon.setFill(Color.GREEN);

        Label addContentLabel = new Label("add Content");
        addContentLabel.setTextFill(Color.GREEN);
        addContentLabel.setFont(new Font("Pleasewritemeasong", 64));

        VBox pane = new VBox(icon, addContentLabel);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: white");
        pane.setPadding(new Insets(12));

        StackPane dragPane = new StackPane(pane, toDragIn) {

            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                toDragIn.relocate(0, 0);
                toDragIn.resize(getWidth(), getHeight());
            }
        };
        pane.setOpacity(0.7);
        pane.setVisible(false);
        pane.toFront();

        ScaleTransition dragTransition = new ScaleTransition(Duration.millis(480), icon);
        dragTransition.setCycleCount(Animation.INDEFINITE);
        dragTransition.setAutoReverse(true);
        dragTransition.setFromX(1);
        dragTransition.setToX(1.2);
        dragTransition.setFromY(1);
        dragTransition.setToY(1.2);

        dragPane.setOnDragExited(e -> {
            dragTransition.stop();
            pane.setVisible(false);
        });

        dragPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean canDrop = false;
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                    canDrop = true;
                    for (File file : db.getFiles()) {
                        if (!isImage(file)) {
                            event.acceptTransferModes(TransferMode.NONE);
                            canDrop = false;
                        }
                    }
                }
                if(canDrop && !dragTransition.getStatus().equals(Animation.Status.RUNNING)) {
                    dragTransition.play();
                    pane.setVisible(true);
                }
            }
        });

        dragPane.setOnDragDropped(e -> {
            List<File> droppedFiles = new ArrayList<File>();
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    if (isImage(file)) {
                        droppedFiles.add(file);
                    }
                }
            }
            if (!droppedFiles.isEmpty()) {
                droppedImageConsumer.accept(droppedFiles);
            }
            pane.setVisible(false);
            e.setDropCompleted(!droppedFiles.isEmpty());
        });
        return dragPane;
    }

    private static boolean isImage(File file) {
        if (file.getName().endsWith(".png")
                || file.getName().endsWith(".PNG")
                || file.getName().endsWith(".jpg")
                || file.getName().endsWith(".JPG")) {
            return true;
        }
        return false;
    }
}
