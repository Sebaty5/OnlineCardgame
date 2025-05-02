package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LobbyListeScreenController extends BaseController {

    @FXML
    private VBox lobbyListContainer;

    public void addLobby(String lobbyName, int currentPlayers, String password, int maxPlayers){
        GridPane lobbyGrid = new GridPane();
        lobbyGrid.getStyleClass().add("lobby");
        lobbyGrid.setPrefSize(700,80);
        lobbyGrid.setHgap(50);
        lobbyGrid.setAlignment(Pos.valueOf("CENTER"));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.CENTER);
        col1.setMinWidth(200);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.CENTER);
        col2.setMaxWidth(100);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHalignment(HPos.CENTER);
        col3.setMaxWidth(100);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHalignment(HPos.CENTER);
        col4.setMaxWidth(100);

        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHalignment(HPos.CENTER);
        col5.setMaxWidth(200);

        lobbyGrid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        Label name = new Label(lobbyName);
        name.getStyleClass().add("lobbyListeLabel");
        GridPane.setColumnIndex(name, 0);
        GridPane.setRowIndex(name, 0);
        lobbyGrid.getChildren().add(name);

        Label players = new Label(currentPlayers + "/" + maxPlayers);
        players.getStyleClass().add("lobbyListeLabel");
        GridPane.setColumnIndex(players, 2);
        GridPane.setRowIndex(name, 0);
        lobbyGrid.getChildren().add(players);

        if(password.isEmpty() || password.equals("null")) {
           ImageView lockIcon = new ImageView(new Image(getClass().getResourceAsStream("/textures/lock.png")));
           lockIcon.setFitHeight(30);
           lockIcon.setFitWidth(30);
           GridPane.setColumnIndex(lockIcon, 3);
           GridPane.setRowIndex(name, 0);
           lobbyGrid.getChildren().add(lockIcon);
        }

        Button joinButton = new Button("Join");
        joinButton.getStyleClass().add("joinButton");
        GridPane.setColumnIndex(joinButton, 4);
        lobbyGrid.getChildren().add(joinButton);

        lobbyListContainer.getChildren().add(lobbyGrid);
    }

    public void switchToLobbySettings() {
        try {
            sceneManager.switchScene(SceneFXML.LOBBY_SETTINGS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
