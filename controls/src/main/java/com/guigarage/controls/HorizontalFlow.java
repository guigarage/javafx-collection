package com.guigarage.controls;

import com.guigarage.ui.util.Direction;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.reactfx.util.FxTimer;

public class HorizontalFlow extends Region {

    private DoubleProperty scrollDelta;

    private Rectangle clipRect;

    private Animation scrollAnimation;

    private Direction currentDirection = Direction.FORWARD;

    private boolean inAnimationInterpolation;

    private ContentOverViewFlowViewPort viewPort;

    public HorizontalFlow() {
        viewPort = new ContentOverViewFlowViewPort();
        getChildren().add(viewPort);

        scrollDelta = new SimpleDoubleProperty(0) {
            @Override
            protected void invalidated() {
                super.invalidated();
                if (!inAnimationInterpolation) {
                    stopAnimation();
                    //TODO: Do not animate if node not in scene graph!
                    if (getScene() != null) {
                        FxTimer.runLater(java.time.Duration.ofMillis(3000), () -> updateScrollAnimation());
                    }
                }
                if (scrollDelta.get() > getMaxScrollDelta()) {
                    scrollDelta.setValue(getMaxScrollDelta());
                } else if (scrollDelta.get() < getMinScrollDelta()) {
                    scrollDelta.setValue(getMinScrollDelta());
                }
            }
        };

        sceneProperty().addListener(e -> {
            if (getScene() == null) {
                stopAnimation();
            } else {
                updateScrollAnimation();
            }
        });

        scrollDelta.addListener(e -> viewPort.setTranslateX(scrollDelta.getValue()));

        clipRect = new Rectangle();
        clipRect.setSmooth(false);
        setClip(clipRect);
        clipRect.setWidth(getWidth());
        clipRect.setHeight(getHeight());

        widthProperty().addListener(v -> {
            clipRect.setWidth(getWidth());
            updateScrollAnimation();
        });
        heightProperty().addListener(v -> {
            clipRect.setHeight(getHeight());
            updateScrollAnimation();
        });

        viewPort.widthProperty().addListener(v -> updateScrollAnimation());

        updateScrollAnimation();
    }

    protected void stopAnimation() {
        if (scrollAnimation != null) {
            scrollAnimation.pause();
        }
    }

    protected void updateScrollAnimation() {
        stopAnimation();
        double diff;
        double startValue = scrollDelta.get();
        if (currentDirection.equals(Direction.FORWARD)) {
            diff = startValue - getMinScrollDelta();
        } else {
            diff = startValue - getMaxScrollDelta();
        }
        if (diff != 0) {
            scrollAnimation = new Transition() {

                {
                    setCycleDuration(Duration.millis(Math.abs(diff * 14)));
                }

                @Override
                protected void interpolate(double frac) {
                    inAnimationInterpolation = true;
                    double newScrollDelta = startValue - frac * diff;
                    setScrollDelta(newScrollDelta);
                    inAnimationInterpolation = false;
                }
            };
            scrollAnimation.setOnFinished(e -> {
                currentDirection = currentDirection.getOppositDirection();
                updateScrollAnimation();
            });
            scrollAnimation.play();
        }
    }

    protected double getMaxScrollDelta() {
        return 0;
    }

    protected double getMinScrollDelta() {
        return Math.min(-viewPort.getWidth() + getWidth(), 0);
    }

    public double getScrollDelta() {
        return scrollDelta.get();
    }

    public void setScrollDelta(double scrollDelta) {
        this.scrollDelta.set(scrollDelta);
    }

    public DoubleProperty scrollDeltaProperty() {
        return scrollDelta;
    }

    public void clearContent() {
        viewPort.getChildren().clear();
    }

    public void addContent(Node content) {
        viewPort.getChildren().add(content);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        viewPort.relocate(getPadding().getLeft(), getPadding().getTop());
        viewPort.resize(viewPort.prefWidth(getHeight() - getPadding().getTop() - getPadding().getBottom()), getHeight() - getPadding().getTop() - getPadding().getBottom());
    }

    private class ContentOverViewFlowViewPort extends Pane {

        private int space = 6;

        @Override
        protected void layoutChildren() {
            double currentX = 0;
            for (Node child : getChildren()) {
                child.relocate(currentX, 0);
                double width = child.prefWidth(getHeight());
                child.resize(width, getHeight());
                currentX = currentX + width + space;
            }
        }

        @Override
        protected double computePrefWidth(double height) {
            double prefWidth = 0;
            for (Node child : getChildren()) {
                double width = child.prefWidth(getHeight());
                prefWidth = prefWidth + width + space;
            }
            return prefWidth;
        }
    }
}
