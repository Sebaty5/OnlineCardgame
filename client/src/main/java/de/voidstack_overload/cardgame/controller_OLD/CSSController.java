package de.voidstack_overload.cardgame.controller_OLD;

import de.voidstack_overload.cardgame.application.GameClient;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class CSSController {

    enum Theme {
        BLUE, RED, DEFAULT
    }


    /*
    * styles/CardGameStyle_Blue.css
    * styles/CardGameStyle_Red.css
    * styles/CardGameStyle_Default.css
    * */
    public static void changeTheme(Theme theme) {
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
        stage.setScene(scene);
        stage.show();
    }

}
