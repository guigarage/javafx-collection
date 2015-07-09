package com.guigarage.demos.itunes.controller;

import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.demos.itunes.slideshow.ITunesSlideshow;
import com.guigarage.material.IconType;
import com.guigarage.material.MaterialToolbar;
import com.guigarage.material.ToolbarAction;
import io.datafx.controller.flow.context.FlowActionHandler;
import javafx.scene.paint.Color;

public class ToolbarHelper {

    public static void changeToAlbumOverview(MaterialToolbar toolbar, FlowActionHandler actionHandler, ITunesDataModel dataModel) {
        toolbar.animateTo(Color.RED, "Albums", IconType.MENU, new ToolbarAction(e -> {
            try {
                actionHandler.navigate(SearchController.class);
                changeToSearch(toolbar, actionHandler);
            } catch (Exception e1) {
                actionHandler.getExceptionHandler().setException(e1);
            }
        }, "\uf002"), new ToolbarAction(e -> {
            try {
                new ITunesSlideshow().start(dataModel.getAlbums());
            } catch (Exception e1) {
                actionHandler.getExceptionHandler().setException(e1);
            }
        }, "\uf008"));
    }

    public static void changeToSearch(MaterialToolbar toolbar, FlowActionHandler actionHandler) {
        toolbar.animateTo(Color.DARKSLATEBLUE, "Search", IconType.PLAY);
    }

    public static void changeToAlbumDetail(MaterialToolbar toolbar, FlowActionHandler actionHandler, ITunesDataModel dataModel) {
        toolbar.animateTo(Color.DEEPPINK, dataModel.getSelectedAlbum().getName(), IconType.ARROW);
    }

    public static void changeToPlay(MaterialToolbar toolbar, FlowActionHandler actionHandler, ITunesDataModel dataModel) {
        toolbar.animateTo(Color.DARKORANGE, dataModel.getSelectedTrack().getName(), IconType.ARROW, new ToolbarAction(e -> {
                    try {
                        actionHandler.handle("volumn-down");
                    } catch (Exception e1) {
                        actionHandler.getExceptionHandler().setException(e1);
                    }
                }, "\uf027"),
                new ToolbarAction(e -> {
                    try {
                        actionHandler.handle("volumn-up");
                    } catch (Exception e1) {
                        actionHandler.getExceptionHandler().setException(e1);
                    }
                }, "\uf028")
        );
    }
}
