package de.voidstack_overload.cardgame.controller_OLD;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController implements ControllerInterface
{
    /**
     * The Master-Pane
     */
    @FXML
    private Pane fxmlHolder;

    /**
     * This method sets the given fxml-file as a child and will then display it
     *
     * @param fxml	String representation of the fxml-file to load as child
     */
    public void setFxml(String fxml)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
            Node debugging_helper = loader.load();
            fxmlHolder.getChildren().setAll(debugging_helper);
            ControllerInterface Controller = loader.getController();

            Controller.init();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method automatically resizes the content pane to the current stages size on its initial load
     * This method is left empty here because this pane is the Master-Pane to hold all other panes
     */
    @Override
    public void init()
    {

    }
}
