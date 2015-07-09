package com.guigarage.flatterfx.skin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import com.guigarage.flatterfx.controls.AvatarView;

public class FlatterAvatarViewSkin extends SkinBase<AvatarView>{

	private Circle circleClip;

	private Circle overlayCircle;

	private Circle backgroundCircle;
	
	private ImageView imageView;
	
	public FlatterAvatarViewSkin(AvatarView control) {
		super(control);
		
		imageView = new ImageView();
		circleClip = new Circle();
		backgroundCircle = new Circle();
		overlayCircle = new Circle();
		
		overlayCircle.setFill(Color.TRANSPARENT);
		overlayCircle.setStrokeWidth(6.0);
		overlayCircle.setStroke(Color.RED.deriveColor(0.5, 0.5, 1.0, 1.0));
		backgroundCircle.setFill(Color.RED);
	
		imageView.clipProperty().set(circleClip);
		
		getChildren().add(backgroundCircle);
		getChildren().add(imageView);
		getChildren().add(overlayCircle);
		
		getSkinnable().imageProperty().addListener(new ChangeListener<Image>() {

			@Override
			public void changed(
					ObservableValue<? extends Image> observable,
					Image oldValue, Image newValue) {
				imageView.setImage(newValue);
				getSkinnable().requestLayout();
			}
		});
		imageView.setImage(getSkinnable().imageProperty().get());
	}
		
	protected double computeSize() {
		Image image = getSkinnable().imageProperty().get();
		if(image == null) {
			return getSkinnable().defaultSizeProperty().doubleValue() + getSkinnable().gapProperty().doubleValue() * 2;
		}
		return Math.min(image.getHeight(), image.getWidth()) + getSkinnable().gapProperty().doubleValue() * 2;
	}
	
	@Override
	protected double computePrefHeight(double width, double topInset,
			double rightInset, double bottomInset, double leftInset) {
		return computeSize();
	}
	
	@Override
	protected double computePrefWidth(double height, double topInset,
			double rightInset, double bottomInset, double leftInset) {
		return computeSize();
	}
	
	@Override
	protected void layoutChildren(double contentX, double contentY,
			double contentWidth, double contentHeight) {
		
		double size = getSkinnable().defaultSizeProperty().doubleValue();
		
		Image image = imageView.getImage();
		if(image != null) {
			double w = image.getWidth();
			double h = image.getHeight();
			size = Math.min(w, h);
		}
		circleClip.radiusProperty().set(size / 2);

		double posX = (getSkinnable().getWidth() - size) / 2;
		double posY = (getSkinnable().getHeight() - size) / 2;
		
		if(image != null) {
			posX = (getSkinnable().getWidth() - image.getWidth()) / 2;
			posY = (getSkinnable().getHeight() - image.getHeight()) / 2;
			imageView.resize(image.getWidth(), image.getHeight());
		} else {
			imageView.resize(size, size);
		}
		imageView.relocate(posX, posY);

		
		circleClip.centerXProperty().set(imageView.getLayoutBounds().getWidth() / 2);
		circleClip.centerYProperty().set(imageView.getLayoutBounds().getHeight() / 2);

		backgroundCircle.centerXProperty().set(getSkinnable().getWidth() / 2);
		backgroundCircle.centerYProperty().set(getSkinnable().getHeight() / 2);
		backgroundCircle.radiusProperty().set(size / 2 + getSkinnable().gapProperty().get());

		overlayCircle.centerXProperty().set(getSkinnable().getWidth() / 2);
		overlayCircle.centerYProperty().set(getSkinnable().getHeight() / 2);
		overlayCircle.radiusProperty().set(size / 2);
		
	}
}
