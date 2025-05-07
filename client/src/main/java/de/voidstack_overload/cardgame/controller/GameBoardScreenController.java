package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.service.GameService;
import de.voidstack_overload.cardgame.service.LobbyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameBoardScreenController extends BaseController
{
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
    private StackPane playerHand;
    @FXML
    private Button takeOrPassButton;

    private final Deque<String> history = new ArrayDeque<>(16);

    @FXML
    private void openSettings(ActionEvent actionEvent)
    {
    }

    @FXML
    private void lobbyLeave(ActionEvent actionEvent) {
        LobbyService.lobbyLeave();
    }

    @FXML
    private void checkButtonAvailability(ActionEvent actionEvent)
    {
    }

    @FXML
    private void sendChatMessage(ActionEvent actionEvent)
    {
    }

    @FXML
    private void playCard(ActionEvent actionEvent) {
        //This method is called via each card, so get the corresponding card button via the action event to assess the value of card
        Button source = (Button) actionEvent.getSource();
        if (source == null) {
            return;
        }
        //you now have the source Button of this event to work with
    }

    @FXML
    private void takeOrPassAction(ActionEvent actionEvent) {
        GameService.pass();
    }

    public void attachToChatHistory(String message) {
        if (history.size() == 15) {
            history.pollFirst();
        }
        history.addLast(message);
        chatHistory.setText(String.join("\n", history));
    }
}