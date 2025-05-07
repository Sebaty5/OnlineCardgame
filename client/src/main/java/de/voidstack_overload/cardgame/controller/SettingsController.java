package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class SettingsController extends BaseController {
    @FXML
    private Slider volumeSlider;
    @FXML
    private ChoiceBox<String> choiceBoxLanguage;
    @FXML
    private ChoiceBox<String> choiceBoxResolution;
    @FXML
    private CheckBox checkFullScreen;

    private int lastWindowedW;
    private int lastWindowedH;

    private Stage stage;


    @Override
    public void setSceneManager(SceneManager sm) {
        super.setSceneManager(sm);
        stage = sm.getStage();

        int  w = SceneManager.getWidth();
        int  h = SceneManager.getHeight();
        boolean fs = stage.isFullScreen();

        String key = w + "x" + h;
        if (!choiceBoxResolution.getItems().contains(key)) {
            choiceBoxResolution.getItems().add(key);   // in case user typed odd size
        }
        choiceBoxResolution.setValue(key);
        checkFullScreen.setSelected(fs);

        lastWindowedW = w;
        lastWindowedH = h;
    }

    @FXML
    private void initialize() {
        choiceBoxResolution.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal == null) return;
                    if (checkFullScreen.isSelected()) return;
                    Pair<Integer,Integer> s = parse(newVal);
                    applyWindowedSize(s.getKey(), s.getValue());
                });

        checkFullScreen.selectedProperty().addListener((obs, was, is) -> {
            if (is) {
                lastWindowedW = SceneManager.getWidth();
                lastWindowedH = SceneManager.getHeight();

                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);

                Pair<Integer,Integer> monitor = monitorPixels();
                SceneManager.setSize(monitor.getKey(), monitor.getValue());
                choiceBoxResolution.setDisable(true);
                choiceBoxResolution.setValue("fixed (full-screen)");
            } else {
                stage.setFullScreen(false);
                applyWindowedSize(lastWindowedW, lastWindowedH);
                choiceBoxResolution.setDisable(false);
                choiceBoxResolution.setValue(lastWindowedW + "x" + lastWindowedH);
            }
        });
    }

    private void applyWindowedSize(int w, int h) {
        SceneManager.setSize(w, h);

        double borderW = stage.getWidth()  - stage.getScene().getWidth();
        double borderH = stage.getHeight() - stage.getScene().getHeight();

        stage.setWidth (w + borderW);
        stage.setHeight(h + borderH);
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
