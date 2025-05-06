package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneManager;
import de.voidstack_overload.cardgame.utility.FxUtility;

public abstract class BaseController {
    protected SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void showError(String message) {
        FxUtility.showCustomDialog(message);
    }
}
