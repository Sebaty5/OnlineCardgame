package de.voidstack_overload.cardgame.model.request;

import de.voidstack_overload.cardgame.connection.MessageType;

public class LoginRequest extends BaseRequest {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        super(MessageType.ACCOUNT_LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
