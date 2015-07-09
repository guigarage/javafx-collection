package com.guigarage.incubator.imageviewer.views;

import com.guigarage.animations.slideshow.Slideshow;
import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.extreme.ExtendedExtremeMenu;
import com.guigarage.extreme.ExtremeMenuButton;
import com.guigarage.extreme.ImageDragSupport;
import com.guigarage.extreme.WithMenuPane;
import com.guigarage.imaging.InvertImageFilter;
import com.guigarage.incubator.imageviewer.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.diashow.KenBurnsScene;
import com.guigarage.incubator.imageviewer.ui.ImageObjectListCell;
import com.guigarage.imaging.ImageViewer;
import com.guigarage.ui.UiUtilities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class DetailView extends WithMenuPane {

    private ObjectProperty<Album> album;

    public DetailView() {
        ExtremeMenuButton diashowButton = new ExtremeMenuButton("slideshow", '\uf03d');
        diashowButton.setStyle("-fx-background-fill: green");

        ExtremeMenuButton editButton = new ExtremeMenuButton("edit", '\uf040');
        editButton.setStyle("-fx-background-fill: green");

        ExtremeMenuButton deleteButton = new ExtremeMenuButton("delete", '\uf014');
        deleteButton.setStyle("-fx-background-fill: red");

        HBox centeredMenu = new HBox(diashowButton, editButton, deleteButton);
        centeredMenu.setSpacing(20);
        centeredMenu.setPadding(new Insets(12, 12, 0, 12));
        centeredMenu.setAlignment(Pos.BOTTOM_CENTER);

        ExtremeMenuButton prevButton = new ExtremeMenuButton("prev", '\uf060');
        prevButton.setStyle("-fx-background-fill: blue");

        ExtremeMenuButton nextButton = new ExtremeMenuButton("next", '\uf061');
        nextButton.setStyle("-fx-background-fill: blue");

        HBox leftMenu = new HBox(prevButton, nextButton);
        leftMenu.setSpacing(20);
        leftMenu.setPadding(new Insets(12, 12, 0, 22));
        leftMenu.setAlignment(Pos.BOTTOM_LEFT);

        Label nameLabel = UiUtilities.createLabel("pic235.png", '\uf03e');
        Label sizeLabel = UiUtilities.createLabel("1024x768", '\uf0b2');
        Label cameraInfoLabel = UiUtilities.createLabel("1/125s, f/5.6, ISO 200", '\uf030');

        VBox infoBox = new VBox();
        infoBox.setSpacing(6);
        infoBox.setPadding(new Insets(12, 22, 26, 12));
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.getChildren().addAll(nameLabel, sizeLabel, cameraInfoLabel);


        ExtendedExtremeMenu bottomMenu = new ExtendedExtremeMenu(leftMenu, centeredMenu, infoBox);


        ListView<ImageObject> albumOverview = new ListView<>();
        albumOverview.setOrientation(Orientation.HORIZONTAL);
        albumOverview.setCellFactory(e -> new ImageObjectListCell());
        albumOverview.setPadding(new Insets(12, 12, 12, 12));
        albumOverview.setStyle("-fx-background-color: transparent");
        albumOverview.itemsProperty().addListener(e -> albumOverview.getSelectionModel().select(0));
        albumOverview.setPrefHeight(150);
        ExtendedExtremeMenu topMenu = new ExtendedExtremeMenu(null, albumOverview, null, Side.TOP);

        diashowButton.setOnAction(e -> showDiashow(albumOverview.getItems()));


        ImageViewer imageViewer = new ImageViewer(null);


        editButton.setOnAction(e -> {
            if (imageViewer.getFilter() != null) {
                imageViewer.setFilter(null);
            } else {
                imageViewer.setFilter(new InvertImageFilter());
            }
        });

        albumOverview.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (albumOverview.getSelectionModel().getSelectedItem() != null) {
                ImageObject selected = albumOverview.getSelectionModel().getSelectedItem();
                imageViewer.setImage(selected.getOriginalImage());
                sizeLabel.setText(selected.getOriginalImage().getWidth() + "x" + selected.getOriginalImage().getHeight());
            }
        });
        prevButton.setOnAction(e -> albumOverview.getSelectionModel().select(albumOverview.getSelectionModel().getSelectedIndex() - 1));
        nextButton.setOnAction(e -> albumOverview.getSelectionModel().select(albumOverview.getSelectionModel().getSelectedIndex() + 1));
        deleteButton.setOnAction(e -> albumOverview.getItems().remove(albumOverview.getSelectionModel().getSelectedItem()));

        setTopMenu(topMenu);
        setBottomMenu(bottomMenu);
        setContent(ImageDragSupport.addSupport(imageViewer, files -> {
            for (File imageFile : files) {
                try {
                    albumOverview.getItems().add(new ImageObject(imageFile.toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }));

        setStyle("-fx-background-color: white");

        album = new SimpleObjectProperty<>();
        album.addListener(e -> albumOverview.setItems(album.get() == null ? null : album.get().getImages()));
    }

    private void showDiashow(List<ImageObject> images) {
        Stage stage = new Stage();
        Slideshow diashow = new Slideshow(generateKenBurnsEffect(images), Duration.millis(2000));
        diashow.play();
        stage.setScene(new Scene(diashow.getStage()));
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    public SlideshowScene generateKenBurnsEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        for (ImageObject o : imageObjects) {
            s = new KenBurnsScene(o, s);
        }
        return s;
    }

    public Album getAlbum() {
        return album.get();
    }

    public void setAlbum(Album album) {
        this.album.set(album);
    }

    public ObjectProperty<Album> albumProperty() {
        return album;
    }
}
