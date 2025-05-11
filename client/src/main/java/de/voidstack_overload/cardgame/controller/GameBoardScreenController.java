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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

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
    public Label LobbyInfoLabel;
    @FXML
    private Label lobbyActionInfoLabel;
    @FXML
    public GridPane playerList;
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
        //LEFT
        cardStack.setImage(RessourceService.getImage(RessourceService.ImageKey.CARD_BACK_LOW_SAT));
        //BOTTOM
        playerHand.prefWidthProperty().bind(stackArea.widthProperty());
        playerHand.minWidthProperty().bind(stackArea.widthProperty());
        playerHand.maxWidthProperty().bind(stackArea.widthProperty());
        playerHand.setPadding(new Insets(10, 500, 10, 500));
        //RIGHT

        // TODO: Remove default initialization when no longer templating layout

        Player sebaty1 = new Player("Sebaty1", 1);
        Player sebaty2 = new Player("Sebaty2", 2);
        Player sebaty3 = new Player("Sebaty3", 3);
        Player sebaty4 = new Player("Sebaty4", 4);
        Player sebaty5 = new Player("Sebaty5", 5);
        Player sebaty6 = new Player("Sebaty6", 6);
        int[] hand = new int[]{11, 12, 13, 14};
        int[][] stacks = new int[][]{{15, 16}, {17, 18}, {19, 20}, {21, 22}, {-1, -1}, {-1, -1}};

        GameState gameState = new GameState(sebaty4.name(), new String[]{sebaty4.name(), sebaty2.name()}, sebaty3.name(), new Player[]{sebaty5, sebaty1, sebaty4, sebaty2, sebaty3, sebaty6}, 30, 1, hand, stacks);
        updateGameState(gameState);
    }

    public void updateGameState(GameState state) {
        updateTrumpColor(state);
        setCardStackSize(state);
        updateHand(state);
        updateCardStacks(state);
        updatePlayerList(state);
    }

    private void updateTrumpColor(GameState state) {
        switch (state.trumpColor()) {
            // Club
            case 0 -> trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.CLUB_GLOW_SYMBOL));
            // Diamond
            case 1 -> trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.DIAMOND_GLOW_SYMBOL));
            // Heart
            case 2 -> trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.HEART_GLOW_SYMBOL));
            // Spade
            case 3 -> trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.SPADE_GLOW_SYMBOL));
            // No Color
            default -> trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.EMPTY_IMAGE));
        }
    }

    private void setCardStackSize(GameState state) {
        cardStackLabel.textProperty().set(String.format("%d", state.drawPileHeight()));
    }

    private void updateHand(GameState state) {
        playerHand.getChildren().clear();
        for (int i = 0; i < state.hand().length; i++)
        {
            drawHandCard(state.hand()[i]);
        }
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

    private void updatePlayerList(GameState state) {
        playerList.getChildren().clear();
        for (int i = 0; i < state.players().length; i++) {
            Player p = state.players()[i];

            boolean attacker = false;
            for (int j = 0; j < state.attackers().length; j++) {
                if (p.name().equals(state.attackers()[j])) {
                    attacker = true;
                    break;
                }
            }

            String role = "";
            if (attacker) role = "Attacker";
            else if (p.name().equals(state.defender())) role = "Defender";

            playerList.add(new Label(p.name().equals(state.activePlayer()) ? "+" : ""), 0 ,i);
            playerList.add(new Label(role), 1, i);
            playerList.add(new Label(p.name()), 2 ,i);
            playerList.add(new Label(String.format("%d",p.handSize())), 3 ,i);
        }
    }

    public void drawHandCard(int cardNumber) {
    StackPane stackPane = new StackPane();
    stackPane.setPadding(new Insets(20, 0, 0, 0));

    ImageView imageView = createImageView(cardNumber);
    imageView.setOnMouseClicked(event -> playCard(cardNumber));
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
    private void playCard(int cardNumber)
    {
        GameService.sendPlayedCard(cardNumber);
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