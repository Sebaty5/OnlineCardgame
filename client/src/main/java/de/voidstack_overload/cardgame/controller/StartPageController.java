package de.voidstack_overload.cardgame.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartPageController implements ControllerInterface {

    @FXML
    private Pane fxmlHolder;

    @Override
    public void init() {}

    public void setFxml(String fxml) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
            Node debugging_helper = loader.load();
            fxmlHolder.getChildren().setAll(debugging_helper);
            de.voidstack_overload.cardgame.controller_OLD.ControllerInterface Controller = loader.getController();

            Controller.init();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
