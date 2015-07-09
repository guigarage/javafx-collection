package com.guigarage.incubator.coverflow;

import com.guigarage.animations.transitions.DurationBasedTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Coverflow<T> extends Group {

    public ObservableList<CoverflowCell<T>> cells;

    private Path path;

    private double fractionOffsetPerCell = 0.1;

    private DurationBasedTransition transition;

    public Coverflow() {
        path = new Path();
        path.getElements().add(new MoveTo(-800, -200));
        path.getElements().add(new LineTo(-200, 0));
        path.getElements().add(new LineTo(0, 0));
        path.getElements().add(new LineTo(200, 0));
        path.getElements().add(new LineTo(800, -200));

        setRotationAxis(new Point3D(1, 0, 0));
        setRotate(-90);

        cells = FXCollections.observableArrayList();
        for(int i = 0; i < 11; i++) {
            cells.add(new CoverflowCell<T>(this));
        }
        animateTo(5);

    }

    public void animateTo(int index) {
        if(transition != null) {
            transition.stop();
        }
        final double[] startFraction = getFractionForAllCells();


        transition = new DurationBasedTransition(Duration.seconds(Math.abs(0.5 - startFraction[index]) * 5)) {


            @Override
            protected void interpolate(double frac) {
                for(int i = 0; i < cells.size(); i++) {
                    int diff = i - index;
                    double endFractionForCell = 0.5 + diff * fractionOffsetPerCell;
                    double fractionDiffForCell = endFractionForCell - startFraction[i];
                    cells.get(i).setToFraction(startFraction[i] + fractionDiffForCell * frac);
                }
            }
        };
        transition.play();
    }

    private double[] getFractionForAllCells() {
        double[] currentFraction = new double[cells.size()];
        for(int i = 0; i < cells.size(); i++) {
            currentFraction[i] = cells.get(i).getCurrentFraction();
        }
        return currentFraction;
    }

    public Path getPath() {
        return path;
    }
}
