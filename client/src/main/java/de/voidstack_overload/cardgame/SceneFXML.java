package de.voidstack_overload.cardgame;

public enum SceneFXML {
    STARTUP("StartupScreen.fxml"),
    LOADING("LoadingScreen.fxml"),
    MENU("MenuScreen.fxml"),
    CREDIT("CreditScreen.fxml"),
    QUIT_GAME("QuitGameScreen.fxml"),
    SETTINGS("SettingsScreen.fxml"),
    LOGIN("LoginScreen.fxml"),
    REGISTER("RegisterScreen.fxml"),
    PROFILE("ProfileScreen.fxml"),
    LOBBY_SETTINGS("LobbySettingsScreen.fxml"),
    LOBBY_LIST("LobbyListScreen.fxml"),
    GAMEBOARD("GameBoardScreen.fxml");
    private final String fxmlFile;

    SceneFXML(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
