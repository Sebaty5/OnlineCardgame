package de.voidstack_overload.cardgame.model.response;

public class AuthenticationResponse {

    private String username;

    public AuthenticationResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
