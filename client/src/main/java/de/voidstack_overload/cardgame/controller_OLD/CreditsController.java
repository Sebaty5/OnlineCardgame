package de.voidstack_overload.cardgame.controller_OLD;

import de.voidstack_overload.cardgame.utility.FXMLNavigator;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.PaneResizer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreditsController implements ControllerInterface {


    @Override
    public void init() {
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();

        PaneResizer.resizePane(scene.getHeight(), true);
    }

    @FXML
    private void goBack() {
        FXMLNavigator.loadFxml(FXMLNavigator.HOME);
    }
}
