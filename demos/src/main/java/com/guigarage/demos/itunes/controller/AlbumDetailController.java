package com.guigarage.demos.itunes.controller;

import com.guigarage.animations.transitions.LambdaTransition;
import com.guigarage.controls.IconifiedButtonSkin;
import com.guigarage.controls.MediaListCell;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.demos.itunes.data.Track;
import com.guigarage.material.MaterialToolbar;
import com.guigarage.responsive.DeviceType;
import io.datafx.controller.FXMLController;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import javafx.animation.Animation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@FXMLController("album-detail.fxml")
public class AlbumDetailController {

    @FXML
    @BackAction
    private Button backButton;

    @FXML
    private TableView<Track> tableView;

    @FXML
    private ListView<Track> listView;

    @FXML
    private ImageView coverView;

    @FXML
    private VBox coverPane;

    @Inject
    private ITunesDataModel dataModel;

    @ActionHandler
    private FlowActionHandler actionHandler;

    @FXML
    private VBox viewPanel;

    private MaterialToolbar toolbar;

    @PostConstruct
    public void init() {
        toolbar = ApplicationContext.getInstance().getRegisteredObject(MaterialToolbar.class);

        initTable();
        initList();
        initCoverView();
        initToolbar();
    }

    private void initCoverView() {

        coverPane.setMaxHeight(200);
        coverPane.setMinHeight(200);
        Rectangle clip = new Rectangle();
        clip.setFill(Color.BLACK);
        clip.widthProperty().bind(coverPane.widthProperty());
        clip.heightProperty().bind(coverPane.heightProperty());
        coverPane.setClip(clip);
        coverView.fitWidthProperty().bind(viewPanel.widthProperty());


        LambdaTransition transition = new LambdaTransition(Duration.seconds(10), frac -> {
            coverView.setTranslateY(-frac * (coverView.getLayoutBounds().getHeight() - coverPane.getHeight()));

        });
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();

        coverView.setSmooth(true);
        coverView.setPreserveRatio(true);
        coverView.setImage(new Image(dataModel.getSelectedAlbum().getCoverUrl()));
    }

    private void initToolbar() {
        toolbar.setOnMouseClicked(e -> {
            ToolbarHelper.changeToAlbumOverview(toolbar, actionHandler, dataModel);
            try {
                actionHandler.navigateBack();
            } catch (Exception e1) {
                actionHandler.getExceptionHandler().setException(e1);
            }
        });
    }

    private void initList() {
        listView.getStyleClass().addAll(DeviceType.STYLE_CLASS_SM_VISIBLE, DeviceType.STYLE_CLASS_XS_VISIBLE);
        listView.setCellFactory(v -> {
            MediaListCell cell = new MediaListCell<Track>() {
                @Override
                protected void updateItem(Track item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
                        setTitle(item.getNumber() + " " + item.getName());
                        setDescription(item.getAlbum().getArtist());
                    } else {
                        setTitle("");
                        setDescription("");
                    }
                }
            };
            Button musicButton = new Button();
            musicButton.setId("musicButton");
            IconifiedButtonSkin.addStyleClass(musicButton);
            cell.setLeftContent(musicButton);
            return cell;
        });
        listView.setItems(dataModel.getSelectedAlbum().getTracks());

        listView.setOnKeyPressed(e -> {
            dataModel.setSelectedTrack(listView.getSelectionModel().getSelectedItem());
            if (dataModel.getSelectedTrack() != null) {
                try {
                    actionHandler.navigate(PlayTrackController.class);
                    ToolbarHelper.changeToPlay(toolbar, actionHandler, dataModel);
                } catch (Exception e1) {
                    actionHandler.getExceptionHandler().setException(e1);
                }
            }
        });


    }

    private void initTable() {
        TableColumn<Track, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        TableColumn<Track, Integer> trackNumberColumn = new TableColumn<>("Tracknumber");
        trackNumberColumn.setCellValueFactory(c -> new SimpleObjectProperty<Integer>(c.getValue().getNumber()));

        tableView.getColumns().addAll(nameColumn, trackNumberColumn);
        tableView.getStyleClass().addAll(DeviceType.STYLE_CLASS_LG_VISIBLE, DeviceType.STYLE_CLASS_MD_VISIBLE);
        tableView.setItems(dataModel.getSelectedAlbum().getTracks());


        tableView.setOnKeyPressed(e -> {
            dataModel.setSelectedTrack(tableView.getSelectionModel().getSelectedItem());
            if (dataModel.getSelectedTrack() != null) {
                try {
                    actionHandler.navigate(PlayTrackController.class);
                    ToolbarHelper.changeToPlay(toolbar, actionHandler, dataModel);
                } catch (Exception e1) {
                    actionHandler.getExceptionHandler().setException(e1);
                }
            }
        });
    }
}
