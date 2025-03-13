package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneManager;

public abstract class BaseController {
    protected SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
