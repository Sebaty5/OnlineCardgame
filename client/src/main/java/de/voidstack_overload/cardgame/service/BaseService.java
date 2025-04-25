package de.voidstack_overload.cardgame.service;

import de.voidstack_overload.cardgame.connection.ConnectionManager;

public abstract class BaseService {
    private final ConnectionManager connectionManager;

    public BaseService() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
