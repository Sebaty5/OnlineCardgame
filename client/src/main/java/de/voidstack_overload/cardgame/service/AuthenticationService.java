package de.voidstack_overload.cardgame.service;

import de.voidstack_overload.cardgame.connection.ConnectionManager;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.dto.request.LoginRequest;
import de.voidstack_overload.cardgame.dto.request.RegisterRequest;
import de.voidstack_overload.cardgame.dto.response.AuthenticationResponse;

public class AuthenticationService {

    private final ConnectionManager connectionManager;

    public AuthenticationService() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    public ResponseEntity<AuthenticationResponse> login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        return connectionManager.sendRequest(request);
    }

    public ResponseEntity<AuthenticationResponse> register(String username, String password) {
        RegisterRequest request = new RegisterRequest(username, password);
        return connectionManager.sendRequest(request);
    }

    //TODO
    public void logout() {
//        JsonBuilder jsonBuilder = new JsonBuilder();
//        jsonBuilder.add("type", "ACCOUNT_LOGOUT");
//        sendMessage(jsonBuilder.toString());
    }
}
