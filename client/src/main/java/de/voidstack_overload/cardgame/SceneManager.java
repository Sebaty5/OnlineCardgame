package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.configuration.SettingData;
import de.voidstack_overload.cardgame.configuration.Settings;
import de.voidstack_overload.cardgame.controller.BaseController;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class SceneManager {

    private final Stage stage;
    public Stage getStage() {
        return stage;
    }

    private final StackPane sceneRoot = new StackPane();
    private final Group scalableRoot = new Group();

    private final Scale scale = new Scale(1, 1, REF_W /2 , REF_H /2);
    private final DoubleProperty scaleFactor = new SimpleDoubleProperty(1);

    private final Scene scene;

    private static final double REF_W = 1920;
    private static final double REF_H = 1080;

    private final DoubleProperty targetW = new SimpleDoubleProperty(width);
    private final DoubleProperty targetH = new SimpleDoubleProperty(height);

    private static int width = Math.max(Toolkit.getDefaultToolkit().getScreenSize().width, 1920);
    private static int height = Math.max(Toolkit.getDefaultToolkit().getScreenSize().height, 1080);

    public static void setSize(int w, int h) {
        width = w;
        height = h;
    }
    public static int getWidth () {
        return width;
    }
    public static int getHeight() {
        return height;
    }

    private boolean isFullScreen = false;

    public void setFullScreen(boolean fs)
    {
        this.isFullScreen = fs;
    }

    public SceneManager(Stage stage) {
        this.stage = stage;

        stage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            SettingData settingData = Settings.INSTANCE.getSettingData();
            setSize(settingData.width(), settingData.height());
            setFullScreen(newVal);
            Settings.INSTANCE.setSettingData(new SettingData(settingData.volume(), settingData.language(), settingData.width(), settingData.height(), newVal));
            resizeStageIfNeeded();
            if (!newVal)
            {
                stage.centerOnScreen();
            }
        });

        scalableRoot.getTransforms().add(scale);
        sceneRoot.getChildren().add(scalableRoot);

        scene = new Scene(sceneRoot, width, height);
        sceneRoot.setStyle("-fx-background-color: #2F2F2F;");
        stage.setScene(scene);
        bindScale();
        stage.show();
    }

    public void switchScene(SceneFXML fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile.getFxmlFile()));
        Parent view = loader.load();
        BaseController controller = loader.getController();
        controller.setSceneManager(this);
        scalableRoot.getChildren().setAll(view);
        SettingData data = Settings.INSTANCE.getSettingData();
        setFullScreen(data.fullscreen());
        if (!isFullScreen)
        {
            setSize(data.width(), data.height());
            resizeStageIfNeeded();
        }
    }

    private double[] getSceneSize(Stage stage) {
        if (stage.getScene() == null) {
            return new double[]{stage.getWidth(), stage.getHeight()};
        } else {
            return new double[]{stage.getScene().getWidth(), stage.getScene().getHeight()};
        }
    }

    private void bindScale() {
        scale.xProperty().bind(scaleFactor);
        scale.yProperty().bind(scaleFactor);

        targetW.addListener((o,ov,nv) -> updateScale());
        targetH.addListener((o,ov,nv) -> updateScale());
        sceneRoot.widthProperty() .addListener((o,ov,nv) -> updateScale());
        sceneRoot.heightProperty().addListener((o,ov,nv) -> updateScale());

        updateScale();
    }

    private void updateScale() {
        double kUser = Math.min(targetW.get() / REF_W,
                targetH.get() / REF_H);

        double w  = sceneRoot.getWidth();
        double h  = sceneRoot.getHeight();

        final double SLACK = 20;

        boolean bigEnough = w + SLACK >= targetW.get() && h + SLACK >= targetH.get();

        double kWindow = bigEnough ? kUser : Math.min(1.0, Math.min(w / REF_W, h / REF_H));

        scaleFactor.set(Math.min(kUser, kWindow));
    }

    public void resizeStageIfNeeded() {
        if (!isFullScreen) {
            stage.setWidth(width);
            //TODO: Better calculate offset
            stage.setHeight(height + 30);
        }
    }
}
