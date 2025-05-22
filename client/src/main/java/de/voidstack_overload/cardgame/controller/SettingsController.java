package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import de.voidstack_overload.cardgame.configuration.SettingData;
import de.voidstack_overload.cardgame.configuration.Settings;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

import java.util.ResourceBundle;


public class SettingsController extends BaseController {
    private static final StandardLogger LOGGER = new StandardLogger();

    @FXML
    private Slider volumeSlider;
    @FXML
    private ChoiceBox<String> choiceBoxLanguage;
    @FXML
    private ChoiceBox<String> choiceBoxResolution;
    @FXML
    private CheckBox checkFullScreen;
    @FXML
    private ResourceBundle resources;
    private int lastWindowedW;
    private int lastWindowedH;
    private Stage stage;



    @Override
    public void setSceneManager(SceneManager sm) {
        super.setSceneManager(sm);
        stage = sm.getStage();

        SettingData data = Settings.INSTANCE.getSettingData();

        volumeSlider.setValue(data.volume());
        choiceBoxLanguage.setValue(data.language());

        choiceBoxResolution.setValue(data.width() + "x" + data.height());
        checkFullScreen.setSelected(data.fullscreen());

        lastWindowedW = data.width();
        lastWindowedH = data.height();

        sceneManager.getStage().fullScreenProperty().addListener((obs, oldVal, newVal) -> checkFullScreen.setSelected(newVal));
    }

    @FXML
    private void initialize() {

        choiceBoxResolution.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            if (checkFullScreen.isSelected()) return;
            Pair<Integer, Integer> s = parse(newVal);
            applyWindowedSize(s.getKey(), s.getValue());
            LOGGER.log("Saving Values: choiceBoxResolution ComboBox");
            saveValues();
            sceneManager.getStage().centerOnScreen();
        });

        checkFullScreen.selectedProperty().addListener((obs, was, is) -> {
            if (is) {

                lastWindowedW = SceneManager.getWidth();
                lastWindowedH = SceneManager.getHeight();

                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);

                Pair<Integer, Integer> monitor = monitorPixels();
                SceneManager.setSize(monitor.getKey(), monitor.getValue());
                choiceBoxResolution.setDisable(true);
                choiceBoxResolution.setValue(resources.getString("fullscreenMessage"));
            } else {
                stage.setFullScreen(false);
                applyWindowedSize(lastWindowedW, lastWindowedH);
                choiceBoxResolution.setDisable(false);
                choiceBoxResolution.setValue(lastWindowedW + "x" + lastWindowedH);
                sceneManager.getStage().centerOnScreen();
            }
            LOGGER.log("Saving Values: checkFullScreen Checkbox");
            saveValues();
            sceneManager.setFullScreen(is);
        });

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            sceneManager.adjustVolume(newVal.doubleValue());
            LOGGER.log("Saving Values: VolumeSlider Slider");
            saveValues();
        });

        choiceBoxLanguage.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            LOGGER.log("Saving Values: choiceBoxLanguage ComboBox");
            String language = choiceBoxLanguage.getValue();
            saveValues();
            System.out.println("ABC 1 " + language + " " + sceneManager.getLanguage());
            if (!language.equals(sceneManager.getLanguage())) {
                System.out.println("ABC 2");
                sceneManager.changeLanguage(language);
                try {
                    sceneManager.switchScene(SceneFXML.SETTINGS);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    private void saveValues()
    {
        int volume = (int)volumeSlider.getValue();
        String language = choiceBoxLanguage.getValue();
        int width;
        int height;
        try
        {
            width = Integer.parseInt(choiceBoxResolution.getValue().split("x")[0]);
            height = Integer.parseInt(choiceBoxResolution.getValue().split("x")[1]);
        }
        catch (NumberFormatException ex)
        {
            width = Settings.INSTANCE.getWidth();
            height = Settings.INSTANCE.getHeight();
        }
        boolean fullscreen = checkFullScreen.isSelected();

        SettingData data = new SettingData(volume, language, width, height, fullscreen);
        Settings.INSTANCE.setSettingData(data);
    }

    private void applyWindowedSize(int w, int h) {
        SceneManager.setSize(w, h);

        sceneManager.resizeStageIfNeeded();
    }

    private static Pair<Integer,Integer> parse(String s) {
        String[] p = s.split("x");
        return new Pair<>(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    private static Pair<Integer,Integer> monitorPixels() {
        var b = Screen.getPrimary().getBounds();
        return new Pair<>((int) b.getWidth(), (int) b.getHeight());
    }

    public void switchToMenu() {
        try {
            sceneManager.switchScene(SceneFXML.MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
