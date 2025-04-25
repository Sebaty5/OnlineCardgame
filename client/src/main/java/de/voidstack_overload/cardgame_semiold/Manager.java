package de.voidstack_overload.cardgame_semiold;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Manager extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Durak");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("LobbyListeScreen.fxml"));
        Pane mainPane = fxmlLoader.load();

        stage.setScene(new Scene(mainPane, stage.getWidth(), stage.getHeight()));

        Manager.stage = stage;
        Manager.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
