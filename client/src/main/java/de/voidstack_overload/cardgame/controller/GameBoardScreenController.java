package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.fxNodes.HandPane;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.service.GameService;
import de.voidstack_overload.cardgame.service.LobbyService;
import de.voidstack_overload.cardgame.records.GameState;

import de.voidstack_overload.cardgame.service.RessourceService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayDeque;
import java.util.Deque;


public class GameBoardScreenController extends BaseController
{
    private static final StandardLogger LOGGER = new StandardLogger();

    public static GameBoardScreenController INSTANCE = null;

    public GameBoardScreenController() {
        INSTANCE = this;
    }

    @FXML
    private Button settingsButton;
    @FXML
    private Button leaveButton;
    @FXML
    private ImageView trumpColorImage;
    @FXML
    public GridPane stackArea;
    @FXML
    private ImageView cardStack;
    @FXML
    private Label cardStackLabel;
    @FXML
    private ImageView smallStack1;
    @FXML
    private ImageView smallStack2;
    @FXML
    private ImageView smallStack3;
    @FXML
    private ImageView smallStack4;
    @FXML
    private ImageView smallStack5;
    @FXML
    private ImageView smallStack6;
    @FXML
    private VBox lobbyInfoPane;
    @FXML
    private Label lobbyActionInfoLabel;
    @FXML
    private VBox playerInfoPane;
    @FXML
    private Label chatLabel;
    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField chatTextInputField;
    @FXML
    private Button sendText;
    @FXML
    private Label defendOrTakeLabel;
    @FXML
    private HandPane playerHand;
    @FXML
    private Button takeOrPassButton;

    @FXML
    public void initialize() {
        playerHand.prefWidthProperty().bind(stackArea.widthProperty());
        playerHand.minWidthProperty().bind(stackArea.widthProperty());
        playerHand.maxWidthProperty().bind(stackArea.widthProperty());

        cardStack.setImage(RessourceService.getImage(RessourceService.ImageKey.CARD_BACK_LOW_SAT));
        playerHand.setPadding(new Insets(10, 500, 10, 500));
        // TODO: Remove default initialization when no longer templating layout
        trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.CLUB_GLOW_SYMBOL));

        int handSize = 52;
        playerHand.getChildren().clear();
        for (int i = 0; i < handSize; i++)
        {
            drawHandCard(i + 11);
        }
    }

    public void drawHandCard(int cardNumber) {
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20, 0, 0, 0));

        ImageView imageView = new ImageView();
        Image image = RessourceService.getImage(cardNumber);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(128);
        imageView.setOnMouseClicked(event -> {
           GameService.sendPlayedCard(cardNumber);
        });
        stackPane.setOnMouseEntered(e -> {
            imageView.setViewOrder(-1);
            imageView.setTranslateY(-20);
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);
        });
        stackPane.setOnMouseExited(e -> {
            imageView.setViewOrder(0);
            imageView.setTranslateY(0);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
        });
        stackPane.getChildren().add(imageView);
        playerHand.getChildren().add(stackPane);
    }

    public void updateGameState(GameState state) {
        updateTrumpColor(state);
        updateHand(state);
    }

    private void updateTrumpColor(GameState state) {
        switch (state.trumpColor()) {
            // Club
            case 0 -> {
                trumpColorImage.setImage(RessourceService.getImage(66));
            }
            // Diamond
            case 1 -> {
                trumpColorImage.setImage(RessourceService.getImage(67));
            }
            // Heart
            case 2 -> {
                trumpColorImage.setImage(RessourceService.getImage(68));
            }
            // Spade
            case 3 -> {
                trumpColorImage.setImage(RessourceService.getImage(69));
            }
            // No Color
            default -> {
                // no change
            }
        }
    }

    private void updateHand(GameState state) {
        playerHand.getChildren().clear();
        for (int i = 0; i < state.hand().length; i++)
        {
            drawHandCard(state.hand()[i]);
        }
    }

    @FXML
    private void openSettings(ActionEvent actionEvent) {
    }

    @FXML
    private void lobbyLeave(ActionEvent actionEvent) {
        LobbyService.lobbyLeave();
    }

    @FXML
    private void checkButtonAvailability(ActionEvent actionEvent) {
    }

    @FXML
    private void sendChatMessage(ActionEvent actionEvent) {
    }

    @FXML
    private void playCard(MouseEvent actionEvent)
    {
        ImageView source = (ImageView) actionEvent.getSource();
        if (source == null) {
            LOGGER.error("Tried to play a card but source is null?");
        }
        LOGGER.log("Playing card: " + source);
        // TODO: Call play card method
    }

    @FXML
    private void takeOrPassAction(ActionEvent actionEvent) {
        GameService.pass();
    }

    private final Deque<String> history = new ArrayDeque<>(16);

    public void attachToChatHistory(String message) {
        if (history.size() == 15) {
            history.pollFirst();
        }
        history.addLast(message);
        chatHistory.setText(String.join("\n", history));
    }
}