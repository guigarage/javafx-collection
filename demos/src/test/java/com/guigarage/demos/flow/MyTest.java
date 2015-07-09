package com.guigarage.demos.flow;

import com.guigarage.demos.itunes.api.FlowTest;
import javafx.scene.control.Label;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by hendrikebbers on 01.10.14.
 */
public class MyTest extends FlowTest {

    @Override
    protected Class<?> getFlowStartController() {
        return View1Controller.class;
    }

    @Test
    public void testTheButton() {
        click("#actionButton");
        Label label = find("#resultLabel");
        assertEquals("This is view 2", label.getText());
    }


    @Test
    public void brokenTest() {
        click("#actionButton");
        Label myLabel = find("#resultLabel");
        assertEquals("This is view 1", myLabel.getText());

    }
}
