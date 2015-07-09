package com.guigarage.flatterfx.skin.touch;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.NodeOrientation;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.behavior.KeyBinding;

public final class TouchFlattertTraversalHelper {

	public static final String TRAVERSE_UP = "TraverseUp";
	public static final String TRAVERSE_DOWN = "TraverseDown";
	public static final String TRAVERSE_LEFT = "TraverseLeft";
	public static final String TRAVERSE_RIGHT = "TraverseRight";
	
	public static final KeyBinding UP_BINDING = new KeyBinding(UP, TRAVERSE_UP);
	public static final KeyBinding DOWN_BINDING = new KeyBinding(DOWN, TRAVERSE_DOWN);
	public static final KeyBinding LEFT_BINDING = new KeyBinding(LEFT, TRAVERSE_LEFT);
	public static final KeyBinding RIGHT_BINDING = new KeyBinding(RIGHT, TRAVERSE_RIGHT);

	public static final List<KeyBinding> TRAVERSAL_BINDINGS = new ArrayList<>();

	static {
		TRAVERSAL_BINDINGS.add(UP_BINDING);
		TRAVERSAL_BINDINGS.add(DOWN_BINDING);
		TRAVERSAL_BINDINGS.add(LEFT_BINDING);
		TRAVERSAL_BINDINGS.add(RIGHT_BINDING);
	}

	private TouchFlattertTraversalHelper() {}
	
	public static boolean compare(KeyEvent e, KeyBinding b) {
		if (b.getAlt().equals(e.isAltDown())
				&& b.getCtrl().equals(e.isControlDown())
				&& b.getMeta().equals(e.isMetaDown())
				&& b.getShift().equals(e.isShiftDown())
				&& b.getType().equals(e.getEventType())
				&& b.getCode().equals(e.getCode())) {
			return true;
		}
		return false;
	}
	
	public static String matchActionForEvent(KeyEvent e, NodeOrientation effectiveNodeOrientation) {
		if (compare(e, TouchFlattertTraversalHelper.LEFT_BINDING)) {
			if (effectiveNodeOrientation == NodeOrientation.RIGHT_TO_LEFT) {
				return TouchFlattertTraversalHelper.TRAVERSE_RIGHT;
			} else {
				return TouchFlattertTraversalHelper.TRAVERSE_LEFT;
			}
		}else if (compare(e, TouchFlattertTraversalHelper.RIGHT_BINDING)) {
			if (effectiveNodeOrientation == NodeOrientation.RIGHT_TO_LEFT) {
				return TouchFlattertTraversalHelper.TRAVERSE_LEFT;
			} else {
				return TouchFlattertTraversalHelper.TRAVERSE_RIGHT;
			}
		} else if (compare(e, TouchFlattertTraversalHelper.UP_BINDING)) {
			return TouchFlattertTraversalHelper.UP_BINDING.getAction();
		} else if (compare(e, TouchFlattertTraversalHelper.DOWN_BINDING)) {
			return TouchFlattertTraversalHelper.DOWN_BINDING.getAction();
		}
		return null;
	}
}
