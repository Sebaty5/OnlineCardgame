package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.utility.FxUtility;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class NetworkClient extends WebSocketClient {
    protected static final StandardLogger LOGGER = new StandardLogger("Network Client");

    public NetworkClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.log("Connected to server");
    }

    @Override
    public void onMessage(String s) {
        LOGGER.log("Nachricht erhalten: " + s);
        MessageHandler.INSTANCE.handleMessage(s);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (remote) {
            LOGGER.log("Server terminated connection.");
            FxUtility.switchScene(SceneFXML.MENU);
            FxUtility.showAlert("Lost connection to server.");
        } else {
            LOGGER.log("Client terminated connection.");
        }
    }

    @Override
    public void onError(Exception e) {
        LOGGER.error("Error: " + e.getMessage());

    }
}
