package com.guigarage.demos.itunes.views;

import com.guigarage.demos.itunes.api.FlowTest;
import com.guigarage.demos.itunes.api.FlowTestView;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hendrikebbers on 15.09.14.
 */
public class AlbumDetailView extends FlowTestView {

    public AlbumDetailView(FlowTest handler) {
        super(handler);
    }

    public AlbumDetailView checkTrackCountOfSelectedAlbum(int count) {
        assertEquals(count, getHandler().getFlowContext().getRegisteredObject(ITunesDataModel.class).getSelectedAlbum().getTracks().size());
        return this;
    }

    public AlbumDetailView assertContainsTrack(String name) {
        assertTrue(!getHandler().getFlowContext().getRegisteredObject(ITunesDataModel.class).getSelectedAlbum().getTracks().filtered(t -> t.getName().equals(name)).isEmpty());
        return this;
    }

    public void play(String name) {
        getTestHandler().click((Text t) -> t.getText().contains(name)).sleep(1, TimeUnit.SECONDS);
        getTestHandler().type(KeyCode.SPACE);
    }
}
