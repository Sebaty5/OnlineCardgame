package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.service.RessourceService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

public class LoadingController extends BaseController {

    @FXML
    private ProgressBar loadingBar;

    @FXML
    private void initialize() {
        Task<Void> loaderTask = createLoaderTask();
        loadingBar.progressProperty().bind(loaderTask.progressProperty());

        loaderTask.setOnSucceeded(e -> {
            try {
                sceneManager.switchScene(SceneFXML.MENU);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Thread worker = new Thread(loaderTask, "texture‑loader");
        worker.setDaemon(true);
        worker.start();
    }

    private static Task<Void> createLoaderTask() {
        int textureAmount = RessourceService.getImageBufferSize();
        Task<Void> loaderTask = new Task<>() {
            @Override
            protected Void call() {
                for (int i = 0; i < textureAmount;) {
                    RessourceService.getImage(i);
                    updateProgress(++i , textureAmount);
                }
                return null;
            }
        };
        return loaderTask;
    }
}
