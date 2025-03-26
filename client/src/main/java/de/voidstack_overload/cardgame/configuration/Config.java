package de.voidstack_overload.cardgame.configuration;

import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.io.IOException;
import java.nio.file.Paths;

public class Config extends JsonHandler<Config.Data> {
    private static final StandardLogger LOGGER = new StandardLogger("Config");

    private static Config INSTANCE;

    private String serverUri = "ws://localhost:8080";

    public static Config getInstance() {
        if(INSTANCE == null) {
            try {
                INSTANCE = new Config();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    private Config() throws IOException {
        super(Paths.get("client/config/config.json"), 600_000L, Data.class, Data::new);
        try {
            super.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void init() {
        this.serverUri = fetchServerUri();
        forceSave();
    }

    public String getServerUri() { return serverUri; }

    // --------------------- //
    // Read from config-file //
    // --------------------- //


    private String fetchServerUri() {
        try {
            this.readLock.lock();
            return this.getData().serverUri;
        } finally {
            this.readLock.unlock();
        }
    }

    static final class Data {
        private String serverUri = "ws://localhost:8080";
    }
}
