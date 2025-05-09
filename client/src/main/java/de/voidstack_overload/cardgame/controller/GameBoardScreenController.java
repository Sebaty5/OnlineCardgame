package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.fxNodes.CardStackPane;
import de.voidstack_overload.cardgame.fxNodes.HandPane;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.records.Player;
import de.voidstack_overload.cardgame.service.GameService;
import de.voidstack_overload.cardgame.service.LobbyService;
import de.voidstack_overload.cardgame.records.GameState;

import de.voidstack_overload.cardgame.service.RessourceService;
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
    private CardStackPane smallStack1;
    @FXML
    private CardStackPane smallStack2;
    @FXML
    private CardStackPane smallStack3;
    @FXML
    private CardStackPane smallStack4;
    @FXML
    private CardStackPane smallStack5;
    @FXML
    private CardStackPane smallStack6;
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

        Player sebaty1 = new Player("Sebaty1", 1);
        Player sebaty2 = new Player("Sebaty2", 2);
        Player sebaty3 = new Player("Sebaty3", 3);
        Player sebaty4 = new Player("Sebaty4", 4);
        Player sebaty5 = new Player("Sebaty5", 5);
        int[] hand = new int[]{11, 12, 13, 14};
        int[][] stacks = new int[][]{{15, 16}, {17, 18}, {19, 20}, {21, 22}, {-1, -1}, {-1, -1}};

        GameState gameState = new GameState(sebaty4.name(), new String[]{sebaty4.name(), sebaty2.name()}, sebaty3.name(), new Player[]{sebaty5, sebaty1, sebaty4, sebaty2, sebaty3}, 30, 1, hand, stacks);
        updateGameState(gameState);
    }



    public void updateGameState(GameState state) {
        updateTrumpColor(state);
        updateHand(state);
        updateCardStacks(state);
    }

    private void updateTrumpColor(GameState state) {
        switch (state.trumpColor()) {
            // Club
            case 0 -> {
                trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.CLUB_GLOW_SYMBOL));
            }
            // Diamond
            case 1 -> {
                trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.DIAMOND_GLOW_SYMBOL));
            }
            // Heart
            case 2 -> {
                trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.HEART_GLOW_SYMBOL));
            }
            // Spade
            case 3 -> {
                trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.SPADE_GLOW_SYMBOL));
            }
            // No Color
            default -> {
                trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.EMPTY_IMAGE));
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

    public void drawHandCard(int cardNumber) {
    StackPane stackPane = new StackPane();
    stackPane.setPadding(new Insets(20, 0, 0, 0));

    ImageView imageView = createImageView(cardNumber);
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

    private static ImageView createImageView(int cardNumber) {
        ImageView imageView = new ImageView();
        Image image = RessourceService.getImage(cardNumber);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(128);
        return imageView;
    }

    private void updateCardStacks(GameState state) {
        int[][] stacks = state.cardStacks();

        smallStack1.getChildren().add(createImageView(stacks[0][0]));
        smallStack1.getChildren().add(createImageView(stacks[0][1]));
        smallStack2.getChildren().add(createImageView(stacks[1][0]));
        smallStack2.getChildren().add(createImageView(stacks[1][1]));
        smallStack3.getChildren().add(createImageView(stacks[2][0]));
        smallStack3.getChildren().add(createImageView(stacks[2][1]));
        smallStack4.getChildren().add(createImageView(stacks[3][0]));
        smallStack4.getChildren().add(createImageView(stacks[3][1]));
        smallStack5.getChildren().add(createImageView(stacks[4][0]));
        smallStack5.getChildren().add(createImageView(stacks[4][1]));
        smallStack6.getChildren().add(createImageView(stacks[5][0]));
        smallStack6.getChildren().add(createImageView(stacks[5][1]));
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