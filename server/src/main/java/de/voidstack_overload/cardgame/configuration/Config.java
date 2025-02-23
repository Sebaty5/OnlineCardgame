package de.voidstack_overload.cardgame.configuration;

import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.io.IOException;
import java.nio.file.Paths;

public class Config extends JsonHandler<Config.Data> {
    protected static final StandardLogger LOGGER = new StandardLogger("Config");

    public static final Config INSTANCE;

    static {
        try {
            INSTANCE = new Config();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int serverPort = 8080;

    private Config() throws IOException {
        super(Paths.get("server/config/config.json"), 600_000L, Data.class, Data::new);
        try {
            super.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void init() {
        this.serverPort = fetchServerPort();
        forceSave();
    }

    public int getServerPort() {
        return serverPort;
    }

    // --------------------- //
    // Read from config-file //
    // --------------------- //
    private int fetchServerPort() {
        try {
            this.readLock.lock();
            return this.getData().serverPort;
        } finally {
            this.readLock.unlock();
        }
    }

    static final class Data {
        private int serverPort = 8080;
    }
}
