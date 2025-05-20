package de.voidstack_overload.cardgame.configuration;

import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.io.IOException;
import java.nio.file.Paths;

public class Settings extends JsonHandler<Settings.Data> {
    private static final StandardLogger LOGGER = new StandardLogger("Settings");

    public static Settings INSTANCE;

    static {
        try {
            INSTANCE = new Settings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int volume = 50;
    private String language = "English";
    private int width = 1280;
    private int height = 720;
    private boolean fullscreen = false;

    private Settings() throws IOException {
        super(Paths.get("client/config/settings.json"), 600_000L, Data.class, Data::new);
        try {
            super.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        this.volume = fetchVolume();
        this.language = fetchLanguage();
        this.width = fetchWidth();
        this.height = fetchHeight();
        this.fullscreen = fetchFullscreen();
        forceSave();
    }

    public SettingData getSettingData() {
        return new SettingData(volume, language, width, height, fullscreen);
    }

    public void setSettingData(SettingData data) {
        this.getData().volume = data.volume();
        this.getData().language = data.language();
        this.getData().width = data.width();
        this.getData().height = data.height();
        this.getData().fullscreen = data.fullscreen();
        this.volume = data.volume();
        this.language = data.language();
        this.width = data.width();
        this.height = data.height();
        this.fullscreen = data.fullscreen();
        forceSave();
    }

    public int getVolume() { return volume; }
    public String getLanguage() { return language; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isFullscreen() { return fullscreen; }

    // --------------------- //
    // Read from config-file //
    // --------------------- //

    private int fetchVolume() {
        try {
            this.readLock.lock();
            return this.getData().volume;
        } finally {
            this.readLock.unlock();
        }
    }

    private String fetchLanguage() {
        try {
            this.readLock.lock();
            return this.getData().language;
        } finally {
            this.readLock.unlock();
        }
    }

    private int fetchHeight() {
        try {
            this.readLock.lock();
            return this.getData().height;
        } finally {
            this.readLock.unlock();
        }
    }

    private int fetchWidth() {
        try {
            this.readLock.lock();
            return this.getData().width;
        } finally {
            this.readLock.unlock();
        }
    }

    private boolean fetchFullscreen() {
        try {
            this.readLock.lock();
            return this.getData().fullscreen;
        } finally {
            this.readLock.unlock();
        }
    }

    static final class Data {
        private int volume = 50;
        private String language = "English";
        private int width = 1280;
        private int height = 720;
        private boolean fullscreen = false;
    }
}
