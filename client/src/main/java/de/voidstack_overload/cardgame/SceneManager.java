package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.configuration.SettingData;
import de.voidstack_overload.cardgame.configuration.Settings;
import de.voidstack_overload.cardgame.controller.BaseController;

import java.awt.Toolkit;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneManager {

    private final Stage stage;
    public Stage getStage() {
        return stage;
    }

    private final StackPane sceneRoot = new StackPane();
    private final Group scalableRoot = new Group();

    private final Scale scale = new Scale(1, 1, REF_W /2 , REF_H /2);
    private final DoubleProperty scaleFactor = new SimpleDoubleProperty(1);

    private static final double REF_W = 1920;
    private static final double REF_H = 1080;

    private final DoubleProperty targetW = new SimpleDoubleProperty(width);
    private final DoubleProperty targetH = new SimpleDoubleProperty(height);

    private static int width = Math.max(Toolkit.getDefaultToolkit().getScreenSize().width, 1920);
    private static int height = Math.max(Toolkit.getDefaultToolkit().getScreenSize().height, 1080);
    private Locale locale = changeLanguage(Settings.INSTANCE.getSettingData().language());
    private String language = Settings.INSTANCE.getSettingData().language();
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
    private final double decoW;
    private final double decoH;

    private boolean isFullScreen = false;

    public String getLanguage() {
        return language;
    }

    public void setFullScreen(boolean fs)
    {
        this.isFullScreen = fs;
    }
    private final MediaPlayer player;

    public SceneManager(Stage stage) {
        this.stage = stage;

        final double initialSceneWidth = 720;
        final double initialSceneHeight = 640;
        final Parent root = new Pane();
        final Scene sc = new Scene(root, initialSceneWidth, initialSceneHeight);

        stage.setScene(sc);
        stage.show();

        decoW = Math.abs(initialSceneWidth - stage.getWidth());
        decoH = Math.abs(initialSceneHeight - stage.getHeight());

        stage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            SettingData data = Settings.INSTANCE.getSettingData();
            setSize(data.width(), data.height());
            setFullScreen(newVal);
            Settings.INSTANCE.setSettingData(new SettingData(data.volume(), data.language(), data.width(), data.height(), newVal));
            resizeStageIfNeeded();
            if (!newVal)
            {
                stage.centerOnScreen();
            }
        });

        scalableRoot.getTransforms().add(scale);
        sceneRoot.getChildren().add(scalableRoot);

        Scene scene = new Scene(sceneRoot, width, height);
        sceneRoot.setStyle("-fx-background-color: #2F2F2F;");
        stage.setScene(scene);
        bindScale();
        stage.show();

        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("audio/Durak.mp3")).toString();
        Media media = new Media(filePath);
        player = new MediaPlayer(media);
    }
    public void switchScene(SceneFXML fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile.getFxmlFile()));
        loader.setResources(ResourceBundle.getBundle("messages", locale));
        Parent view = loader.load();
        BaseController controller = loader.getController();
        controller.setSceneManager(this);
        scalableRoot.getChildren().setAll(view);
        SettingData data = Settings.INSTANCE.getSettingData();
        setFullScreen(data.fullscreen());
        stage.setFullScreenExitHint("");
        if (!isFullScreen) {
            setSize(data.width(), data.height());
            resizeStageIfNeeded();
        }

        if (fxmlFile.equals(SceneFXML.MENU) || fxmlFile.equals(SceneFXML.SETTINGS) || fxmlFile.equals(SceneFXML.CREDIT) || fxmlFile.equals(SceneFXML.QUIT_GAME)) {
            adjustVolume(data.volume());
            player.play();
        } else {
            player.stop();
        }
    }

    public void adjustVolume(double volume) {
        player.setVolume(volume / 100.0);
    }

    public Locale changeLanguage(String language) {
        switch (language) {
            case "Deutsch":
                locale = new Locale("de", "DE");
                break;
            case "English":
                locale = new Locale("en", "EN");
                break;
            default:
                break;
        }
        this.language = language;
        return this.locale;
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
        if (!isFullScreen)
        {
            stage.setHeight(height + decoH);
            stage.setWidth(width + decoW);
        }
    }

    public Locale getLocale() {
        return locale;
    }
}
