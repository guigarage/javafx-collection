package com.guigarage.demos.itunes.api;

import io.datafx.controller.flow.FlowHandler;

public class FlowTestView {

    private FlowTest testHandler;

    public FlowTestView(FlowTest testHandler) {
        this.testHandler = testHandler;
    }

    public FlowTest getTestHandler() {
        return testHandler;
    }

    public FlowHandler getHandler() {
        return getTestHandler().getHandler();
    }
}
