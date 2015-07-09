package com.guigarage.demos.itunes.api;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.injection.Injector;
import javafx.scene.Parent;
import org.loadui.testfx.GuiTest;

public abstract class FlowTest extends GuiTest {

    private FlowHandler handler;

    public FlowHandler getHandler() {
        return handler;
    }

    protected abstract Class<?> getFlowStartController();

    protected void injectTestData(Injector injector) {

    }

    @Override
    protected Parent getRootNode() {
        try {
            Flow flow = new Flow(getFlowStartController());
            handler = flow.createHandler();
            injectTestData(new Injector(handler));
            return handler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
