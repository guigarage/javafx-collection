package com.guigarage.animations.slideshow;

import com.guigarage.animations.transitions.TimerTransition;
import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.util.Duration;

public class SlideshowScene {

    private Node sceneNode;
    private Animation sceneAnimation;
    private SlideshowScene nextScene;
    private SlideshowSceneState sceneState = SlideshowSceneState.NOT_STARTED;
    private Duration onScreen;


    public SlideshowScene(Node sceneNode, Animation sceneAnimation, SlideshowScene nextScene, Duration onScreen) {
        this.nextScene = nextScene;
        this.sceneNode = sceneNode;
        this.onScreen = onScreen;
        this.sceneAnimation = sceneAnimation;
        if(this.sceneAnimation == null) {
            this.sceneAnimation = new TimerTransition(Duration.millis(10));
        }
    }

    public void play() {
        sceneState = SlideshowSceneState.PLAYING;
        sceneAnimation.play();
    }

    public void pause() {
        sceneState = SlideshowSceneState.PAUSE;
        sceneAnimation.pause();
    }

    public void stop() {
        sceneState = SlideshowSceneState.STOPPED;
        sceneAnimation.stop();
    }

    public Node getSceneNode() {
        return sceneNode;
    }

    public SlideshowScene getNextScene() {
        return nextScene;
    }

    public Duration getOnScreen() {
        return onScreen;
    }

    public SlideshowSceneState getSceneState() {
        return sceneState;
    }


}
