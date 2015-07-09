package com.guigarage.ui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class WindowUtilities {

    public static void registerListenerForWindowFocus(Node n, InvalidationListener l) {

        ChangeListener<Window> windowListener = (observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.focusedProperty().removeListener(l);
            }
            if (newValue != null) {
                newValue.focusedProperty().addListener(l);
            }
        };

        n.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.windowProperty().removeListener(windowListener);
            }
            if (newValue != null) {
                newValue.windowProperty().addListener(windowListener);
                if (newValue.getWindow() != null) {
                    newValue.getWindow().focusedProperty().addListener(l);
                    l.invalidated(newValue.getWindow().focusedProperty());
                }
            }
        });
        if (n.getScene() != null) {
            n.getScene().windowProperty().addListener(windowListener);
        }
    }

    public static void bindToStageTitle(Node n, StringProperty property) {

        ChangeListener<Window> windowListener = (observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue instanceof Stage) {
                ((Stage) oldValue).titleProperty().unbindBidirectional(property);
            }
            if (newValue != null && newValue instanceof Stage) {
                ((Stage) newValue).titleProperty().bindBidirectional(property);
            }
        };

        n.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.windowProperty().removeListener(windowListener);
            }
            if (newValue != null) {
                newValue.windowProperty().addListener(windowListener);
                if (newValue.getWindow() != null && newValue.getWindow() instanceof Stage) {
                    ((Stage) newValue.getWindow()).titleProperty().bindBidirectional(property);
                }
            }
        });
        if (n.getScene() != null) {
            n.getScene().windowProperty().addListener(windowListener);
        }
    }

    public static boolean isInFullscreenMode(Node n) {
        Scene scene = n.getScene();
        if (scene != null) {
            Window window = scene.getWindow();
            if (window != null && window instanceof Stage) {
                return ((Stage) window).isFullScreen();
            }
        }
        return false;
    }

    public static void registerListenerForStageFullscreen(Node n, InvalidationListener l) {

        ChangeListener<Window> windowListener = (observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue instanceof Stage) {
                ((Stage) oldValue).fullScreenProperty().removeListener(l);
            }
            if (newValue != null && newValue instanceof Stage) {
                ((Stage) newValue).fullScreenProperty().addListener(l);
            }
        };

        n.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.windowProperty().removeListener(windowListener);
            }
            if (newValue != null) {
                newValue.windowProperty().addListener(windowListener);
                if (newValue.getWindow() != null && newValue.getWindow() instanceof Stage) {
                    ((Stage) newValue.getWindow()).fullScreenProperty().addListener(l);
                    l.invalidated(((Stage) newValue.getWindow()).fullScreenProperty());
                }
            }
        });
        if (n.getScene() != null) {
            n.getScene().windowProperty().addListener(windowListener);
        }
    }

    private static ListChangeListener<Node> createRecursiveChildObserver(Consumer<Node> onRemove, Consumer<Node> onAdd) {
        return new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Node addedChild : change.getAddedSubList()) {
                            if (addedChild instanceof Parent) {
                                ((Parent) addedChild).getChildrenUnmodifiable().addListener(this);
                            }
                            onAdd.accept(addedChild);
                        }
                    }
                    if (change.wasRemoved()) {
                        for (Node removedChild : change.getRemoved()) {
                            if (removedChild instanceof Parent) {
                                ((Parent) removedChild).getChildrenUnmodifiable().removeListener(this);
                            }
                            onRemove.accept(removedChild);
                        }
                    }
                }
            }
        };
    }

    public static void registerRecursiveChildObserver(Window window, Consumer<Node> onRemove, Consumer<Node> onAdd) {
        List<Node> allNodes = getAllNodesInWindow(window);
        ListChangeListener<Node> listener = createRecursiveChildObserver(onRemove, onAdd);
        for (Node child : allNodes) {
            if (child instanceof Parent) {
                Parent parent = (Parent) child;
                parent.getChildrenUnmodifiable().addListener(listener);
            }
        }
    }

    public static void bindStyleSheetToWindow(Window window, StringProperty stylesheet) {
        window.sceneProperty().addListener(e -> {
            if (window.getScene() != null) {
                window.getScene().getStylesheets().add(stylesheet.get());
            }
        });
        if (window.getScene() != null) {
            window.getScene().getStylesheets().add(stylesheet.get());
        }

        stylesheet.addListener((obs, o, n) -> {
            if (window.getScene() != null) {
                int oldPos = -1;
                if (o != null) {
                    oldPos = window.getScene().getStylesheets().indexOf(o);
                    window.getScene().getStylesheets().remove(o);
                }
                if (n != null) {
                    if (oldPos >= 0) {
                        window.getScene().getStylesheets().add(oldPos, n);
                    } else {
                        window.getScene().getStylesheets().add(n);
                    }
                }
            }
        });
    }

    public static List<Node> getAllNodesInWindow(Window window) {
        Parent root = Optional.of(window).map(w -> w.getScene()).map(s -> s.getRoot()).get();
        if (root == null) {
            return new ArrayList<>();
        } else {
            List<Node> ret = new ArrayList<>();
            ret.add(root);
            ret.addAll(getAllNodesInParent(root));
            return ret;
        }
    }

    public static List<Node> getAllNodesInParent(Parent parent) {
        List<Node> ret = new ArrayList<>();
        for (Node child : parent.getChildrenUnmodifiable()) {
            ret.add(child);
            if (child instanceof Parent) {
                ret.addAll(getAllNodesInParent((Parent) child));
            }
        }
        return ret;
    }


}
