package de.voidstack_overload.cardgame.controller_OLD;

import de.voidstack_overload.cardgame.utility.FXMLNavigator;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.PaneResizer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScreenController implements ControllerInterface {

    @FXML
    private void openSettings() {
        FXMLNavigator.loadFxml(FXMLNavigator.SETTINGS);
    }

    @FXML
    private void openCredits() {
        FXMLNavigator.loadFxml(FXMLNavigator.CREDITS);
    }

    @FXML
    private void shutDown() {
        GameClient.shutDown();
    }

    @Override
    public void init() {
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();
        PaneResizer.resizePane(scene.getHeight(), true);
    }
}
