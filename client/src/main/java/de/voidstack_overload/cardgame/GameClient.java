package de.voidstack_overload.cardgame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameClient extends Application {


    public static Stage stage;

    @Override
    public void start(Stage stage)  {
        GameClient.stage = stage;
        GameClient.stage.setResizable(true);
        GameClient.stage.setMinWidth(600);
        GameClient.stage.setMinHeight(400);

        GameClient.stage.setTitle("Game Client");
        GameClient.stage.setScene(new Scene(new Pane()));

        GameClient.stage.show();
    }
}
