package com.guigarage.flatterfx.skin;

import java.util.ArrayList;
import java.util.UUID;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.util.StringConverter;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import com.guigarage.flatterfx.overlay.OverlayPane;
import com.guigarage.flatterfx.overlay.OverlayType;
import com.guigarage.flatterfx.skin.touch.FlatterTouchComboBoxBehavior;
import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.ComboBoxBaseSkin;

public class FlatterComboBoxSkin<T> extends ComboBoxBaseSkin<T> {

	private ListView<T> popup;

	private ListCell<T> buttonCell;

	private ChangeListener<Number> selectionBindingListener;

	private UUID popupShowId;

	public FlatterComboBoxSkin(ComboBoxBase<T> comboBox) {
		super(comboBox, getBehavior(comboBox));
		if (!(comboBox instanceof ComboBox<?>)) {
			throw new RuntimeException(
					"FlatterComboBoxSkin only works with ComboBox");
		}

		buttonCell = getCombobox().getButtonCell() != null ? getCombobox()
				.getButtonCell() : getDefaultCellFactory().call(getPopup());
		buttonCell.setMouseTransparent(true);
		buttonCell.updateListView(getPopup());
	}

	private static <U> ComboBoxBaseBehavior<U> getBehavior(ComboBoxBase<U> comboBox) {
		if(FlatterFX.getInstance().getInputType().equals(FlatterInputType.TOUCH)) {
			return new ComboBoxBaseBehavior<>(comboBox, new ArrayList<KeyBinding>());
		}
		return new FlatterTouchComboBoxBehavior<>(comboBox);
	}
	
	protected ComboBox<T> getCombobox() {
		return (ComboBox<T>) getSkinnable();
	}

	private ChangeListener<Number> getSelectionBindingListener() {
		if (selectionBindingListener == null) {
			selectionBindingListener = new ChangeListener<Number>() {

				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
					getCombobox().getSelectionModel().select(
							newValue.intValue());
				}
			};
		}
		return selectionBindingListener;
	}

	private void bindComboBoxSelectionToPopupSelection() {
		getPopup().getSelectionModel().selectedIndexProperty()
				.addListener(getSelectionBindingListener());
	}

	private void unbindComboBoxSelectionFromPopupSelection() {
		getPopup().getSelectionModel().selectedIndexProperty()
				.removeListener(getSelectionBindingListener());
	}

	public ListView<T> getPopup() {
		if (popup == null) {
			popup = new ListView<>();
			popup.cellFactoryProperty().bind(
					getCombobox().cellFactoryProperty());
			popup.itemsProperty().bind(getCombobox().itemsProperty());

			getCombobox().getSelectionModel().selectedIndexProperty()
					.addListener(new ChangeListener<Number>() {

						@Override
						public void changed(
								ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {
							unbindComboBoxSelectionFromPopupSelection();
							popup.getSelectionModel().select(
									newValue.intValue());

							if (newValue.intValue() > -1) {
								buttonCell.setItem(null);
								buttonCell.updateIndex(newValue.intValue());
							}

							bindComboBoxSelectionToPopupSelection();
						}
					});
			bindComboBoxSelectionToPopupSelection();
		}
		return popup;
	}

	private boolean updateDisplayText(ListCell<T> cell, T item, boolean empty) {
		if (empty) {
			if (cell == null)
				return true;
			cell.setGraphic(null);
			cell.setText(null);
			return true;
		} else if (item instanceof Node) {
			Node currentNode = cell.getGraphic();
			Node newNode = (Node) item;
			if (currentNode == null || !currentNode.equals(newNode)) {
				cell.setText(null);
				cell.setGraphic(newNode);
			}
			return newNode == null;
		} else {
			// run item through StringConverter if it isn't null
			StringConverter<T> c = getCombobox().getConverter();

			// prompt text used to be displayed in the display text of a
			// non-editable node, but was removed as per RT-29284
			String s = item == null ? /* comboBox.getPromptText() */""
					: (c == null ? item.toString() : c.toString(item));
			cell.setText(s);

			cell.setGraphic(null);
			return s == null || s.isEmpty();
		}
	}

	private Callback<ListView<T>, ListCell<T>> getDefaultCellFactory() {
		return new Callback<ListView<T>, ListCell<T>>() {
			@Override
			public ListCell<T> call(ListView<T> listView) {
				return new ListCell<T>() {
					@Override
					public void updateItem(T item, boolean empty) {
						super.updateItem(item, empty);
						updateDisplayText(this, item, empty);
					}
				};
			}
		};
	}

	@Override
	public Node getDisplayNode() {
		return buttonCell;
	}

	@Override
	public void show() {
		if (popupShowId == null) {
			popupShowId = FlatterFX.getInstance()
					.getOverlayLayer(getSkinnable().getScene())
					.show("Select", new Image(OverlayPane.class.getResource("defaultListIcon.png").toString()), getPopup(), OverlayType.POPUP);
		}
	}

	@Override
	public void hide() {
		if (popupShowId != null) {
			FlatterFX.getInstance().getOverlayLayer(getSkinnable().getScene())
					.hide(popupShowId);
			popupShowId = null;
		}
	}

}
