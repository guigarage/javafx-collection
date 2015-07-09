package com.guigarage.demos.itunes.controller;

import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.material.AnimatedIcon;
import com.guigarage.material.MaterialToolbar;
import com.guigarage.responsive.DeviceType;
import com.guigarage.responsive.ResponsiveHandler;
import io.datafx.controller.FXMLController;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.function.Consumer;

@FXMLController("play-track.fxml")
public class PlayTrackController {

    @FXML
    @ActionTrigger("play")
    private Button playButton;

    @FXML
    private Label collectionLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Slider durationSlider;

    @Inject
    private ITunesDataModel dataModel;

    @ActionHandler
    private FlowActionHandler actionHandler;

    private MaterialToolbar toolbar;

    private MediaPlayer player;

    private boolean playing;

    private AnimatedIcon playIcon;

    @FXML
    private VBox coverBox;

    @FXML
    private ImageView coverView;

    @PreDestroy
    public void stop() {
        player.stop();
    }

    @PostConstruct
    public void init() {
        toolbar = ApplicationContext.getInstance().getRegisteredObject(MaterialToolbar.class);
        initInfoPanel();
        initPlayButton();
        initPlayer();
        initCoverBackground();
        initToolbar();

    }

    private void initCoverBackground() {
        coverBox.setMinHeight(0);
        coverBox.setMinWidth(0);
        coverBox.setPrefHeight(0);
        coverBox.setPrefWidth(0);
        Rectangle clip = new Rectangle();
        clip.setFill(Color.BLACK);
        clip.widthProperty().bind(coverBox.widthProperty());
        clip.heightProperty().bind(coverBox.heightProperty());
        coverBox.setClip(clip);


        InvalidationListener listener = e -> {
            if (coverView.getImage() != null) {
                if (coverView.getImage().getHeight() > coverView.getImage().getWidth()) {
                    coverView.setFitWidth(coverBox.getWidth());
                    coverView.setFitHeight(-1);
                    double factor = coverView.getImage().getWidth() / coverBox.getWidth();
                    coverView.setViewport(new Rectangle2D(0, (coverView.getImage().getHeight() - coverBox.getHeight() * factor) / 2, coverView.getImage().getWidth(), coverView.getImage().getHeight()));
                } else {
                    coverView.setFitHeight(coverBox.getHeight());
                    coverView.setFitWidth(-1);
                    double factor = coverView.getImage().getHeight() / coverBox.getHeight();
                    coverView.setViewport(new Rectangle2D((coverView.getImage().getWidth() - coverBox.getWidth() * factor) / 2, 0, coverView.getImage().getWidth(), coverView.getImage().getHeight()));
                }
            }
        };
        coverBox.widthProperty().addListener(listener);
        coverBox.heightProperty().addListener(listener);


        coverView.setSmooth(true);
        coverView.setPreserveRatio(true);
        coverView.setImage(new Image(dataModel.getSelectedAlbum().getCoverUrl()));
    }

    private void initInfoPanel() {
        collectionLabel.setText(dataModel.getSelectedTrack().getAlbum().getName());
        artistLabel.setText(dataModel.getSelectedTrack().getAlbum().getArtist());
    }

    private void initPlayer() {
        Media media = new Media(dataModel.getSelectedTrack().getPreviewUrl());
        player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(0.5);
        play();

        player.currentTimeProperty().addListener(e -> {
            durationSlider.setValue(player.getCurrentTime().toMinutes());
            durationSlider.setMax(player.getCycleDuration().toMinutes());
        });
    }

    private void initPlayButton() {
        playIcon = new AnimatedIcon();
        Circle circle = new Circle();
        circle.setId("play-circle");
        circle.centerXProperty().bind(playIcon.widthProperty().divide(2));
        circle.centerYProperty().bind(playIcon.heightProperty().divide(2));
        circle.radiusProperty().bind(playIcon.widthProperty().divide(2).add(4));
        //circle.setManaged(false);

        StackPane pane = new StackPane(circle, playIcon);
        playIcon.setScaleY(5);
        playIcon.setScaleX(5);
        circle.setScaleY(5);
        circle.setScaleX(5);

        Consumer<DeviceType> changeByDeviceType = d -> {
            switch (d) {
                case LARGE:
                    playIcon.setScaleY(6);
                    playIcon.setScaleX(6);
                    circle.setScaleY(6);
                    circle.setScaleX(6);
                    break;
                case MEDIUM:
                    playIcon.setScaleY(5);
                    playIcon.setScaleX(5);
                    circle.setScaleY(5);
                    circle.setScaleX(5);
                    break;
                case SMALL:
                    playIcon.setScaleY(4);
                    playIcon.setScaleX(4);
                    circle.setScaleY(4);
                    circle.setScaleX(4);
                    break;
                case EXTRA_SMALL:
                    playIcon.setScaleY(3);
                    playIcon.setScaleX(3);
                    circle.setScaleY(3);
                    circle.setScaleX(3);
                    break;
            }
        };

        changeByDeviceType.accept(ResponsiveHandler.deviceTypeProperty.get());

        ResponsiveHandler.setOnDeviceTypeChanged((ov, oldType, newType) -> {
            changeByDeviceType.accept(newType);
        });

        playIcon.jumpDirectlyToPlay();
        playButton.setGraphic(pane);
        pane.layout();
    }

    private void initToolbar() {
        toolbar.setOnMouseClicked(e -> {
            ToolbarHelper.changeToAlbumDetail(toolbar, actionHandler, dataModel);
            try {
                actionHandler.navigateBack();
            } catch (Exception e1) {
                actionHandler.getExceptionHandler().setException(e1);
            }
        });
    }

    @ActionMethod("play")
    public void play() {
        if (playing) {
            playIcon.toPlay();
            player.pause();
            playing = false;
        } else {
            playIcon.toPause();
            player.play();
            playing = true;
        }
    }

    @ActionMethod("volumn-up")
    public void volumnUp() {
        player.setVolume(Math.min(1, player.getVolume() + 0.2));
    }

    @ActionMethod("volumn-down")
    public void volumnDown() {
        player.setVolume(Math.max(0, player.getVolume() - 0.2));
    }
}
