package de.voidstack_overload.cardgame.configuration;

public record SettingData(int volume, String language, int width, int height, boolean fullscreen) {
    public static SettingData defaultSetting() {
        return new SettingData(50, "English", 1280, 720, false);
    }
}
