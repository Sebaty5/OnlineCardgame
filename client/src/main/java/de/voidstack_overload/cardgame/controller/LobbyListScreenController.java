package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import de.voidstack_overload.cardgame.records.Lobby;
import de.voidstack_overload.cardgame.service.LobbyService;
import de.voidstack_overload.cardgame.utility.FxUtility;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class LobbyListScreenController extends BaseController {

    public static LobbyListScreenController INSTANCE = null;
    private static ScheduledExecutorService refreshScheduler;

    public LobbyListScreenController() {
        INSTANCE = this;
    }

    @FXML
    private GridPane masterPane;

    @FXML
    private ListView<Lobby> listView;

    @FXML
    private void initialize() {
        listView.setFixedCellSize(80);
        listView.setCellFactory(v -> new LobbyCell());

        masterPane.sceneProperty().addListener((obsScene, oldScene, newScene) -> {
            if (newScene != null) {
                startScheduler();
            } else {
                stopScheduler();
            }
        });
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        super.setSceneManager(sceneManager);
    }

    private void startScheduler() {
        if (refreshScheduler == null || refreshScheduler.isShutdown()) {
            refreshScheduler = Executors.newSingleThreadScheduledExecutor();
            refreshScheduler.scheduleAtFixedRate(LobbyService::requestLobbyList, 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    private void stopScheduler() {
        if (refreshScheduler != null && !refreshScheduler.isShutdown()) {
            refreshScheduler.shutdownNow();
        }
    }


    public void setList(List<Lobby> list) {
        SortedList<Lobby> sorted = new SortedList<>(FXCollections.observableArrayList(list), Comparator.comparing(l -> l.lobbyName().toLowerCase()));
        listView.setItems(sorted);
    }

    public void switchToMenu() {
        try {
            sceneManager.switchScene(SceneFXML.PROFILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class LobbyCell extends ListCell<Lobby> {
        private final GridPane grid = new GridPane();
        private final Label name   = new Label();
        private final Label count  = new Label();
        private final ImageView lock = new ImageView("textures/lock.png");
        private final Button join  = new Button("Join");

        public LobbyCell() {
            //grid.getStyleClass().add("lobby");            // neon background
            grid.setHgap(50);
            grid.setVgap(0);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.getStyleClass().add("lobby");

            name.getStyleClass().add("lobbyListeLabel");
            count.getStyleClass().add("lobbyListeLabel");

            double[] widths = {78, 5, 5, 10};
            for (double w : widths) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(w);
                grid.getColumnConstraints().add(cc);
            }

            grid.add(name, 0, 0);

            GridPane.setHalignment(count, HPos.CENTER);
            grid.add(count, 1, 0);

            lock.setFitWidth(36);
            lock.setPreserveRatio(true);
            GridPane.setHalignment(lock, HPos.CENTER);
            grid.add(lock, 2, 0);

            join.getStyleClass().add("joinButton");
            join.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHalignment(join, HPos.RIGHT);
            grid.add(join, 3, 0);

            setPrefWidth(0);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            setGraphic(grid);
        }

        @Override
        protected void updateItem(Lobby lobby, boolean empty) {
            super.updateItem(lobby, empty);

            if (empty || lobby == null) {               // ← cell being recycled or off‑screen
                setGraphic(null);
                join.setDisable(false);                 // ① reset all mutable state!
                join.setOnAction(null);
                return;
            }
            name.setText(lobby.lobbyName());
            count.setText(lobby.currentPlayerCount() + "/" + lobby.maxPlayerCount());
            lock.setVisible(lobby.isPasswordProtected());

            join.setOnAction(e -> {
                if (lobby.isPasswordProtected()) {
                    FxUtility.showPasswordPrompt(lobby.lobbyID());
                } else {
                    LobbyService.lobbyJoin(lobby.lobbyID(),"");
                }
            });
            join.setDisable(lobby.currentPlayerCount() == lobby.maxPlayerCount());

            setGraphic(grid);
        }
    }

}
