package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void switchScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
        Pane root = loader.load();

        BaseController controller = loader.getController();
        controller.setSceneManager(this);


        Scene scene;
        if(stage.getScene() != null) {
            scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        } else {
            scene = new Scene(root, stage.getWidth(), stage.getHeight());
        }
        stage.setScene(scene);
        stage.show();
    }
}
