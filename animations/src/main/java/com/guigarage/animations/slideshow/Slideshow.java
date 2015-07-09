package com.guigarage.animations.slideshow;

import com.guigarage.animations.transitions.DurationBasedTransition;
import com.guigarage.animations.transitions.TimerTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Slideshow {

    private SlideshowScene startScene;
    private SlideshowScene currentScene;
    private Pane stage;

    private SlideshowState state = SlideshowState.NOT_STARTED;

    private TimerTransition sceneOnScreenTimer;

    private DurationBasedTransition transitionToNextScene;

    private Duration transitionDuration;

    public Slideshow(SlideshowScene startScene, Duration transitionDuration) {
        this.startScene = startScene;
        this.currentScene = startScene;
        if (startScene.getNextScene() == null) {
            throw new RuntimeException("No next Scene?");
        }
        this.stage = new SlideshowPane(this);
        this.transitionDuration = transitionDuration;
    }

    public void toNextScene() {
        currentScene.stop();
        stage.getChildren().remove(currentScene.getSceneNode());
        onSceneSwitch(currentScene.getSceneNode(), nextScene().getSceneNode());
        currentScene = nextScene();
        stage.getChildren().add(nextScene().getSceneNode());
        currentScene.getSceneNode().toFront();
        configureTransitions();
    }

    private void configureTransitions() {
        sceneOnScreenTimer = new TimerTransition(currentScene.getOnScreen());
        sceneOnScreenTimer.setOnFinished(e -> {
            nextScene().play();
            transitionToNextScene = new DurationBasedTransition(transitionDuration) {

                @Override
                protected void interpolate(double frac) {
                    transition(frac, currentScene.getSceneNode(), nextScene().getSceneNode());
                }
            };
            transitionToNextScene.setOnFinished(event -> toNextScene());
            transitionToNextScene.play();
            state = SlideshowState.PLAYING_IN_TRANSITION;
        });
        sceneOnScreenTimer.play();
        currentScene.getSceneNode().toFront();
        state = SlideshowState.PLAYING_IN_SCENE;
    }

    private SlideshowScene nextScene() {
        if (currentScene.getNextScene() == null) {
            return startScene;
        }
        return currentScene.getNextScene();
    }

    private void init() {
        stage.getChildren().add(currentScene.getSceneNode());
        stage.getChildren().add(nextScene().getSceneNode());
        currentScene.getSceneNode().toFront();
        configureTransitions();
    }

    public void play() {
        if (state.equals(SlideshowState.NOT_STARTED) || state.equals(SlideshowState.STOPPED)) {
            init();
        }
        currentScene.play();
        if(state.equals(SlideshowState.PAUSE_IN_SCENE)) {
            sceneOnScreenTimer.play();
            state = SlideshowState.PLAYING_IN_SCENE;
        } else if(state.equals(SlideshowState.PAUSE_IN_TRANSITION)){
            transitionToNextScene.play();
            nextScene().play();
            state = SlideshowState.PLAYING_IN_TRANSITION;
        }
    }

    public void pause() {
        currentScene.pause();
        if(!nextScene().getSceneState().equals(SlideshowSceneState.NOT_STARTED)) {
            nextScene().pause();
        }
        if(state.equals(SlideshowState.PLAYING_IN_SCENE)) {
            sceneOnScreenTimer.pause();
            state = SlideshowState.PAUSE_IN_SCENE;
        } else {
            transitionToNextScene.pause();
            state = SlideshowState.PAUSE_IN_TRANSITION;
        }
    }

    public void stop() {
        //TODO
        if(transitionToNextScene != null) {
            transitionToNextScene.stop();
        }
        if(sceneOnScreenTimer != null) {
            sceneOnScreenTimer.stop();
        }
        currentScene.stop();
        nextScene().stop();
        init();
        state = SlideshowState.STOPPED;
    }

    public void pauseOrPlay() {
        if(state.equals(SlideshowState.PLAYING_IN_SCENE) || state.equals(SlideshowState.PLAYING_IN_TRANSITION)) {
            pause();
        } else {
            play();
        }
    }

    protected void onSceneSwitch(Node currentScene, Node nextScene) {
        nextScene.setOpacity(1);
        currentScene.setOpacity(1);
    }

    protected void transition(double frac, Node currentScene, Node nextScene) {
        currentScene.setOpacity(1 - frac);
    }

    public Pane getStage() {
        return stage;
    }

    public SlideshowState getState() {
        return state;
    }

    public boolean isPlaying() {
        if(state.equals(SlideshowState.PLAYING_IN_SCENE) || state.equals(SlideshowState.PLAYING_IN_TRANSITION)) {
            return true;
        }
        return false;
    }
}
