package de.voidstack_overload.cardgame.actions.game.passive;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.game.GameAction;
import de.voidstack_overload.cardgame.controller.GameBoardScreenController;
import de.voidstack_overload.cardgame.records.GameState;
import de.voidstack_overload.cardgame.service.GameService;
import javafx.application.Platform;

public class GameStateAction extends GameAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        GameState gameState = GameService.parseJsonToGameState(json);
        Platform.runLater(() -> GameBoardScreenController.INSTANCE.updateGameState(gameState));
    }
}
