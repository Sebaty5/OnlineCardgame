package de.voidstack_overload.cardgame.application;
import de.voidstack_overload.cardgame.controller_OLD.MainController;
import de.voidstack_overload.cardgame.PaneResizer;
import de.voidstack_overload.cardgame.utility.FXMLNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class GameClient extends Application {


    public static Stage stage;

    @Override
    public void start(Stage stage)  {
        GameClient.stage = stage;
        GameClient.stage.setResizable(true);
        GameClient.stage.setMinWidth(600);
        GameClient.stage.setMinHeight(400);

        GameClient.stage.setTitle("Game Client");
        GameClient.stage.setScene(new Scene(loadMainPane()));

        GameClient.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Pane loadMainPane()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLNavigator.MAIN));
            Pane mainPane = loader.load();
            MainController mainController = loader.getController();
            FXMLNavigator.setMainController(mainController);

            
            return mainPane;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new Pane();
    }

    @Override
    public void stop() {
        //TODO

        stage.close();
    }

    public static void shutDown() {
        //TODO
        stage.close();
        System.exit(0);
    }

    private void changeSize(Number newValue, boolean isHeight) {
        PaneResizer.resizePane(newValue, isHeight);
    }

    private static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    private void initEventListener()
    {
        Scene scene = stage.getScene();
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> changeSize(newSceneWidth, false));
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> changeSize(newSceneHeight, true));
    }





}
