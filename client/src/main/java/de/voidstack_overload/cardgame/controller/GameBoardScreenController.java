package de.voidstack_overload.cardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameBoardScreenController extends BaseController
{
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
    private StackPane lobbyInfoPane;
    @FXML
    private Label lobbyActionInfoLabel;
    @FXML
    private StackPane playerInfoPane;
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
    private Button takeButton;
    @FXML
    private Button passButton;

    @FXML
    private void openSettings(ActionEvent actionEvent)
    {
    }

    @FXML
    private void lobbyLeave(ActionEvent actionEvent)
    {
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
    private void playCard(ActionEvent actionEvent)
    {
        //This method is called via each card, so get the corresponding card button via the action event to assess the value of card
        Button source = (Button) actionEvent.getSource();
        if (source == null)
        {
            return;
        }
        //you now have the source Button of this event to work with
    }
}