package com.guigarage.demos.scrollendless;

import com.sun.javafx.scene.control.skin.ListViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.javafx.scene.control.skin.VirtualScrollBar;
import io.datafx.core.concurrent.ProcessChain;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.control.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hendrikebbers on 06.11.14.
 */
public class EndlessListSkin<T> extends ListViewSkin<T> {

    private Worker<List<T>> dataLoader;

    private int maxSize = 60;

    public EndlessListSkin(ListView<T> listView) {
        super(listView);
        try {
            Method m = VirtualFlow.class.getDeclaredMethod("getVbar");
            m.setAccessible(true);
            VirtualScrollBar bar = (VirtualScrollBar) m.invoke(flow);
            getSkinnable().getItems().addListener((ListChangeListener<T>) (e) -> {
                if(getSkinnable().getItems().size() > maxSize) {
                    getSkinnable().getItems().remove(0, getSkinnable().getItems().size() - maxSize);
                }
            });
            bar.valueProperty().addListener(e -> {
                load(bar);
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void load(VirtualScrollBar bar) {
        if (bar.getValue() >= bar.getMax() - bar.getVisibleAmount()) {
            System.out.println("load");
            if(dataLoader == null || !dataLoader.isRunning()) {
                dataLoader = loadNewData(bar);
                dataLoader.valueProperty().addListener(e -> {
                    load(bar);
                });
            }
        }
    }

    public Worker<List<T>> loadNewData(VirtualScrollBar bar) {
        return ProcessChain.create().addPublishingTask(() -> getSkinnable().getItems(), p -> {
            for (int i = 0; i < 10; i++) {
                p.publish((T) ("loaded " + UUID.randomUUID().toString()));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }
}
