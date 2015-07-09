package com.guigarage.demos.itunes.ui;

import com.guigarage.demos.itunes.data.Album;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;import java.lang.Exception;import java.lang.Override;import java.lang.String;

public class ImageTableCell extends TableCell<Album, String> {

	public ImageTableCell() {
		final ImageView view = new ImageView();
		setGraphic(view);
        setPadding(Insets.EMPTY);
        view.fitWidthProperty().bind(widthProperty());
        view.setPreserveRatio(true);
        view.setSmooth(true);

		itemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observableValue,
					String oldValue, String newValue) {
				try {
					if (newValue != null) {
						view.setImage(new Image(newValue, true));
					} else {
						view.setImage(null);
					}
				} catch (Exception e) {
					view.setImage(null);
					e.printStackTrace();
				}
			}
		});
	}
}
