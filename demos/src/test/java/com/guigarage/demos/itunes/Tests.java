package com.guigarage.demos.itunes;

import com.guigarage.controls.IconifiedButtonSkin;
import com.guigarage.demos.itunes.api.FlowTest;
import com.guigarage.demos.itunes.controller.SearchController;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.demos.itunes.views.SearchView;
import com.guigarage.material.MaterialDesignPane;
import com.guigarage.responsive.ResponsiveHandler;
import com.guigarage.ui.BasicUtils;
import io.datafx.controller.context.ApplicationContext;
import io.datafx.controller.injection.Injector;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Test;

public class Tests extends FlowTest {

    @Override
    protected Class<?> getFlowStartController() {
        return SearchController.class;
    }

    @Override
    protected void injectTestData(Injector injector) {
        injector.inject(new TestDataModel(), ITunesDataModel.class);
    }

    @Override
    protected Parent getRootNode() {
        MaterialDesignPane materialDesignPane = new MaterialDesignPane();
        ApplicationContext.getInstance().register(materialDesignPane.getToolbar());
        materialDesignPane.setContent(super.getRootNode());
        materialDesignPane.getStylesheets().add(BasicUtils.getResourceUrl(ITunesSearchDemo.class, "skin.css"));
        IconifiedButtonSkin.addStylesheet(materialDesignPane);
        return materialDesignPane;
    }

    @Override
    public void setupStages(Stage primaryStage) {
        super.setupStages(primaryStage);
        ResponsiveHandler.addResponsiveToWindow(primaryStage);
    }

    @Test
    public void test1() {
        new SearchView(this).search("Rise Against").checkAlbumCount(1);
    }

    @Test
    public void test2() {
        new SearchView(this).search("Rise Against").assertContainsAlbum("The Black Market");
    }

    @Test
    public void test3() {
        new SearchView(this).search("Rise Against").openAlbum("The Black Market").checkTrackCountOfSelectedAlbum(4);
    }

    @Test
    public void test4() {
        new SearchView(this).search("Rise Against").openAlbum("The Black Market").assertContainsTrack("Track 1");
    }

    @Test
    public void test5() {
        new SearchView(this).search("Rise Against").openAlbum("The Black Market").assertContainsTrack("Track 1").assertContainsTrack("Track 2").assertContainsTrack("Track 3").assertContainsTrack("Track 4");
    }

    @Test
    public void test6() {
        new SearchView(this).search("Rise Against").openAlbum("The Black Market").play("Track 1");
    }

}
