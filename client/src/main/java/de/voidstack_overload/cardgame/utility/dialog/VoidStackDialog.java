package de.voidstack_overload.cardgame.utility.dialog;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

public class VoidStackDialog extends Dialog<ButtonType>
{
    public VoidStackDialog(String promptMessage, ButtonType... types)
    {
        try {
            FXMLLoader fx = new FXMLLoader(getClass().getClassLoader().getResource("VoidStackDialog.fxml"));
            DialogPane pane = fx.load();

            Label msg = (Label) pane.lookup("#message");
            HBox box = (HBox) pane.lookup("#buttonBox");

            msg.setText(promptMessage);

            for (ButtonType bt : types) {
                Button btn = new Button(bt.getText());
                btn.getStyleClass().add("neon-btn");
                btn.setOnAction(e -> {
                    setResult(bt);
                    close();
                });
                box.getChildren().add(btn);
            }

            setDialogPane(pane);
            setResultConverter(dialogButton -> dialogButton);
            initStyle(StageStyle.TRANSPARENT);
            pane.setBackground(Background.EMPTY);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addContent(Node node)
    {
        VBox root = (VBox) this.getDialogPane().lookup("#root");
        root.getChildren().add(1, node);
    }
}