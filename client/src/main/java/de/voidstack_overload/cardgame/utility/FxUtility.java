package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

public class FxUtility {
    private static final StandardLogger LOGGER = new StandardLogger();

    public static void showAlert(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public static void showErrorAlert(String errorMessage) {
        showAlert("Error", "", errorMessage);
    }

    public static void switchScene(SceneFXML sceneFXML) {
        Platform.runLater(() -> {
            try {
                GameClient.getSceneManager().switchScene(sceneFXML);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        });
    }

}
