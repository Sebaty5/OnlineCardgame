package de.voidstack_overload.cardgame_semiold;

public enum SceneFXML {
    STARTUP("StartupScreen.fxml"),
    LOADING("LoadingScreen.fxml"),
    MENU("MenuScreen.fxml"),
    CREDIT("CreditScreen.fxml"),
    QUIT_GAME("QuitGameScreen.fxml"),
    LOGIN("LoginScreen.fxml"),
    REGISTRATION("RegistrationScreen.fxml"),
    CREATE_LOBBY("CreateLobbyScreen.fxml");

    private final String fxmlFile;

    SceneFXML(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
