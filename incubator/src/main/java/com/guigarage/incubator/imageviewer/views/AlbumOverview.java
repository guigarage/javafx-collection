package com.guigarage.incubator.imageviewer.views;

import com.guigarage.extreme.ExtendedExtremeMenu;
import com.guigarage.extreme.ImageDragSupport;
import com.guigarage.extreme.WithMenuPane;
import com.guigarage.incubator.imageviewer.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.ui.ImageObjectGridCell;
import com.guigarage.ui.UiUtilities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.GridView;

import java.io.File;
import java.net.MalformedURLException;

public class AlbumOverview extends WithMenuPane {

    private ObjectProperty<Album> album;

    private GridView<ImageObject> gridView;

    public AlbumOverview() {
        gridView = new GridView<>();

        gridView.setCellHeight(128);
        gridView.setCellWidth(128);

        gridView.setCellFactory((e) -> new ImageObjectGridCell());

        setContent(ImageDragSupport.addSupport(gridView, files -> {
            for (File imageFile : files) {
                try {
                    getAlbum().getImages().add(new ImageObject(imageFile.toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }));

        Slider slider = new Slider(32, 1024, 128);
        Text sliderIcon = UiUtilities.createIconText('\uf065', 16);
        sliderIcon.setFill(Color.WHITE);
        HBox sliderBox = new HBox(sliderIcon, slider);
        sliderBox.setAlignment(Pos.CENTER_LEFT);
        sliderBox.setPadding(new Insets(12, 12, 12, 12));

        Label albumNameLabel = new Label("Santorin 2014");
        albumNameLabel.setTextFill(Color.WHITE);
        albumNameLabel.setFont(new Font(40));
        albumNameLabel.setAlignment(Pos.TOP_LEFT);

        Text backIcon = UiUtilities.createIconText('\uf053', 48);
        backIcon.setFill(Color.WHITE);

        HBox nameBox = new HBox(backIcon, albumNameLabel);
        nameBox.setSpacing(16);
        nameBox.setAlignment(Pos.TOP_LEFT);
        nameBox.setPadding(new Insets(12, 12, 52, 12));


        ExtendedExtremeMenu menu = new ExtendedExtremeMenu(nameBox, null, sliderBox, Side.TOP);
        menu.setStyle("-fx-background-color: rgba(3, 101, 192, 0.9);" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-color: rgba(6, 47, 107);");
        menu.setArrowFill(Color.rgb(255, 255, 255, 0.9));

        setTopMenu(menu);

        gridView.cellHeightProperty().bind(slider.valueProperty());
        gridView.cellWidthProperty().bind(slider.valueProperty());

        album = new SimpleObjectProperty<>();
        album.addListener(e -> gridView.setItems(album.get() == null ? null : album.get().getImages()));
    }

    public Album getAlbum() {
        return album.get();
    }

    public ObjectProperty<Album> albumProperty() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album.set(album);
    }
}
