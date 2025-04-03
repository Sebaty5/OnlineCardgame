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

    public void switchScene(SceneFXML fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile.getFxmlFile()));
        Pane root = loader.load();

        BaseController controller = loader.getController();
        controller.setSceneManager(this);

        double[] sceneSize = getSceneSize(stage);
        Scene scene = new Scene(root, sceneSize[0], sceneSize[1]);

        stage.setScene(scene);
        stage.show();
    }

    private double[] getSceneSize(Stage stage) {
        if (stage.getScene() == null) {
            return new double[]{stage.getWidth(), stage.getHeight()};
        } else {
            return new double[]{stage.getScene().getWidth(), stage.getScene().getHeight()};
        }
    }
}
