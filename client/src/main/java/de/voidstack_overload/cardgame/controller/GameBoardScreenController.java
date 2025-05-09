package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.service.GameService;
import de.voidstack_overload.cardgame.service.LobbyService;
import de.voidstack_overload.cardgame.records.GameState;

import de.voidstack_overload.cardgame.service.RessourceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

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
    private FlowPane playerHand;
    @FXML
    private Button takeOrPassButton;

    @FXML
    public void initialize() {
        cardStack.setImage(RessourceService.getImage(RessourceService.ImageKey.CARD_BACK_LOW_SAT));
        // TODO: Remove default initialization when no longer templating layout
        trumpColorImage.setImage(RessourceService.getImage(RessourceService.ImageKey.CLUB_GLOW_SYMBOL));

        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(RessourceService.getImage(RessourceService.ImageKey.CARD_BLANK));
            playerHand.getChildren().add(imageView);
        }

        int handStart = 120;
        int handEnd = 480;
        int cardSize = 55;
        int handSpace = handEnd - handStart - cardSize;

        int handSize = 20;
        for (int i = 0; i < handSize; i++)
        {
            int x = handStart + i * (handSpace / handSize);
            int y = 315;
            drawHandCard(RessourceService.ImageKey.CARD_BLANK.getIndex(), x, y);
        }



    }

    public void drawHandCard(int cardNumber, int x, int y)
    {
        playerHand.getChildren().clear();
        ImageView imageView = new ImageView();
        Image image = RessourceService.getImage(cardNumber);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(55);
        imageView.setOnMouseClicked(event -> {
           GameService.sendPlayedCard(cardNumber);
        });
        //imageView.setOnMouseEntered(this::cardOnHover);         //Listener for onHoverEntered
        //imageView.setOnMouseExited(event -> draw(originalMessage));     //Listener for onHoverExited //For fast travel across all cards: exit is always before entrance, confirmed in testing
        playerHand.getChildren().add(imageView);
    }

    public void updateGameState(GameState state) {
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