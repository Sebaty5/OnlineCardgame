package de.voidstack_overload.cardgame.service;

import de.voidstack_overload.cardgame.connection.ConnectionManager;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.dto.request.LoginRequest;
import de.voidstack_overload.cardgame.dto.request.RegisterRequest;
import de.voidstack_overload.cardgame.dto.response.AuthenticationResponse;
import de.voidstack_overload.cardgame.utility.User;

public class AuthenticationService {
    private User user;

    private final ConnectionManager connectionManager;

    private static AuthenticationService INSTANCE;
    public static AuthenticationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationService();
        }
        return INSTANCE;
    }

    private AuthenticationService() {
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        this.user = null;
        connectionManager.disconnect();
    }
}
