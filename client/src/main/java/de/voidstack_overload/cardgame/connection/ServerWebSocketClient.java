package de.voidstack_overload.cardgame.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.voidstack_overload.cardgame.connection.handler.LobbyCreateHandler;
import de.voidstack_overload.cardgame.connection.handler.LoginResponseHandler;
import de.voidstack_overload.cardgame.connection.handler.RegisterResponseHandler;
import de.voidstack_overload.cardgame.connection.handler.ServerResponseHandler;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerWebSocketClient extends WebSocketClient {

    private final StandardLogger logger;
    private final List<ServerResponseHandler> responseHandlers;
    private CompletableFuture<ResponseEntity<?>> pendingRequest;

    public ServerWebSocketClient(URI serverUri, CompletableFuture<ResponseEntity<?>> pendingRequest) {
        super(serverUri);
        this.logger = new StandardLogger("Client");
        this.pendingRequest = pendingRequest;
        this.responseHandlers = Arrays.asList(
                new LoginResponseHandler(),
                new RegisterResponseHandler(),
                new LobbyCreateHandler()
        );
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.log("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        logger.log("Nachricht erhalten: " + message);
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();
            String type = json.get("type").getAsString();

            for (ServerResponseHandler handler : responseHandlers) {
                if (handler.canHandle(type)) {
                    ResponseEntity<?> response = handler.handle(json);

                    if (!pendingRequest.isDone()) {
                        pendingRequest.complete(response);
                    }
                    return;
                }
            }

            logger.log("Unbekannter Nachrichtentyp: " + type);
        } catch (Exception e) {
            logger.log("Fehler beim Verarbeiten der Nachricht: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.log("Disconnected: " + reason);
    }

    @Override
    public void onError(Exception e) {
        logger.log("Error: " + e.getMessage());
    }
}
