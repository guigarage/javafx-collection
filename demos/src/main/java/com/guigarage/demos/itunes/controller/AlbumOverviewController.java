package com.guigarage.demos.itunes.controller;

import com.guigarage.controls.SimpleMediaListCell;
import com.guigarage.demos.itunes.data.Album;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.demos.itunes.ui.ImageTableCell;
import com.guigarage.material.MaterialToolbar;
import com.guigarage.responsive.DeviceType;
import io.datafx.controller.FXMLController;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@FXMLController("album-overview.fxml")
public class AlbumOverviewController {

    @FXML
    private TableView<Album> tableView;

    @FXML
    private ListView<Album> listView;

    @Inject
    private ITunesDataModel dataModel;

    @ActionHandler
    private FlowActionHandler actionHandler;

    private MaterialToolbar toolbar;

    @PostConstruct
    public void init() {
        toolbar = ApplicationContext.getInstance().getRegisteredObject(MaterialToolbar.class);

        initList();
        initTable();
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setOnMouseClicked(null);
    }

    private void initList() {
        listView.setId("album-overview-list");
        listView.getStyleClass().addAll(DeviceType.STYLE_CLASS_SM_VISIBLE, DeviceType.STYLE_CLASS_XS_VISIBLE);
        listView.setCellFactory(v -> new SimpleMediaListCell<>());
        listView.setItems(dataModel.getAlbums());

        listView.setOnKeyPressed(e -> {
            dataModel.setSelectedAlbum(listView.getSelectionModel().getSelectedItem());
            if (dataModel.getSelectedAlbum() != null) {
                try {
                    actionHandler.navigate(AlbumDetailController.class);
                    ToolbarHelper.changeToAlbumDetail(toolbar, actionHandler, dataModel);
                } catch (Exception e1) {
                    actionHandler.getExceptionHandler().setException(e1);
                }
            }
        });
    }

    private void initTable() {
        TableColumn<Album, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        TableColumn<Album, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getArtist()));

        TableColumn<Album, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGenre()));

        TableColumn<Album, Integer> trackNumberColumn = new TableColumn<>("Number");
        trackNumberColumn.setCellValueFactory(c -> new SimpleObjectProperty<Integer>(c.getValue().getTracks().size()));

        TableColumn<Album, String> imageColumn = new TableColumn<>("Cover");
        imageColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCoverUrl()));
        imageColumn.setCellFactory(v -> new ImageTableCell());

        tableView.getColumns().addAll(imageColumn, nameColumn, artistColumn, trackNumberColumn, genreColumn);
        tableView.getStyleClass().addAll(DeviceType.STYLE_CLASS_LG_VISIBLE, DeviceType.STYLE_CLASS_MD_VISIBLE);
        tableView.setItems(dataModel.getAlbums());

        tableView.setOnKeyPressed(e -> {
            dataModel.setSelectedAlbum(tableView.getSelectionModel().getSelectedItem());
            if (dataModel.getSelectedAlbum() != null) {
                try {
                    actionHandler.navigate(AlbumDetailController.class);
                    ToolbarHelper.changeToAlbumDetail(toolbar, actionHandler, dataModel);
                } catch (Exception e1) {
                    actionHandler.getExceptionHandler().setException(e1);
                }
            }
        });
    }
}
