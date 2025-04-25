package de.voidstack_overload.cardgame.dto.request;

import de.voidstack_overload.cardgame.connection.MessageType;

public class RegisterRequest extends BaseRequest {

    private final String username;
    private final String password;

    public RegisterRequest(String username, String password) {
        super(MessageType.ACCOUNT_REGISTER);
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
