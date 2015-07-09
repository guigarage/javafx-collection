package com.guigarage.material;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MaterialToolbarButtonSkin extends ButtonSkin {

    private Circle springCircle;

    public MaterialToolbarButtonSkin(Button button) {
        super(button);

        springCircle = new Circle();
        springCircle.setFill(Color.WHITESMOKE);
        springCircle.setManaged(false);
        springCircle.setOpacity(0);
        getChildren().add(springCircle);

        getSkinnable().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> springAnimation());
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        springCircle.setCenterX(x + w / 2);
        springCircle.setCenterY(y + h / 2);
    }

    public void springAnimation() {
        Transition transition = new Transition() {

            {
                setCycleDuration(Duration.millis(360));
            }

            @Override
            protected void interpolate(double frac) {
                springCircle.setRadius(Math.max(getSkinnable().getWidth(), getSkinnable().getHeight()) * frac);
                springCircle.setOpacity(frac * 0.5);
            }
        };
        transition.setOnFinished(e -> {
            springCircle.setOpacity(0);
            springCircle.setRadius(0);
        });
        transition.play();
    }
}
