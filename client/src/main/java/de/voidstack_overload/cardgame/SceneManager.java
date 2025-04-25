package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    private final Stage stage;
    private static int width = 1920;
    private static int height = 1080;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void switchScene(SceneFXML fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile.getFxmlFile()));
        Pane root = loader.load();

        BaseController controller = loader.getController();
        controller.setSceneManager(this);

        Scene scene = new Scene(root, width, height);

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

    public static void setWidth(int width) {
        SceneManager.width = width;
    }

    public static void setHeight(int height) {
        SceneManager.height = height;
    }

    public static void setSize(int width, int height) {
        SceneManager.width = width;
        SceneManager.height = height;
    }
    public static int getWidth() {
        return SceneManager.width;
    }

    public static int getHeight() {
        return SceneManager.height;
    }

}
