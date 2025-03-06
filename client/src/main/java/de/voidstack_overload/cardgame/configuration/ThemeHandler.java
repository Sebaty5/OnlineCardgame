package de.voidstack_overload.cardgame.configuration;

import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.controller_OLD.CSSController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemeHandler {

    enum Theme {
        BLUE, RED, DEFAULT
    }

    private static Theme currentTheme;

    public static void setTheme(Theme theme) {
        String themeFile;
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();

        switch (theme) {
            case BLUE:
                themeFile = Objects.requireNonNull(CSSController.class.getClassLoader().getResource("styles/CardGameStyle_Blue.css")).toExternalForm();
                break;
            case RED:
                themeFile = Objects.requireNonNull(CSSController.class.getClassLoader().getResource("styles/CardGameStyle_Red.css")).toExternalForm();
                break;
            default:
                themeFile = Objects.requireNonNull(CSSController.class.getClassLoader().getResource("styles/CardGameStyle_Default.css")).toExternalForm();
                break;
        }

        Pane main = (Pane) scene.lookup("#fxmlHolder");
        main.getStylesheets().clear();
        main.getStylesheets().add(themeFile);

        currentTheme = theme;
        stage.setScene(scene);
        stage.show();
    }

    public static Theme getTheme() {
        return currentTheme;
    }
}
