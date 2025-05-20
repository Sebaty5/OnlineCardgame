package de.voidstack_overload.cardgame.service;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.NetworkManager;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.MessageBuilder;

public class LobbyService {
    public static void lobbyCreate(String lobbyName, String password, int maxPlayers, int botAmount) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("lobbyName", lobbyName);
        jsonBuilder.add("lobbyPassword", password);
        jsonBuilder.add("maxPlayers", maxPlayers);
        jsonBuilder.add("botCount", botAmount);
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.LOBBY_CREATE, jsonBuilder));
    }

    public static void requestLobbyList() {
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.LOBBY_LIST));
    }


   public static void lobbyJoin(String lobbyID, String password) {
       JsonBuilder jsonBuilder = new JsonBuilder();
       jsonBuilder.add("lobbyID", lobbyID);
       jsonBuilder.add("lobbyPassword", password);
       NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.LOBBY_JOIN,jsonBuilder));
   }

    public static void lobbyLeave() {
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.LOBBY_LEAVE));
    }

    public static void lobbyBroadcast(String message){

    }
//

//
//    public void lobbyUpdate(String lobbyName, String lobbyPassword, int maxPlayers, int botAmount) {
//        JsonBuilder jsonBuilder = new JsonBuilder();
//        jsonBuilder.add("type", "LOBBY_UPDATE");
//        jsonBuilder.add("lobbyName", lobbyName);
//        jsonBuilder.add("lobbyPassword", lobbyPassword);
//        jsonBuilder.add("maxPlayers", maxPlayers);
//        jsonBuilder.add("botCount", botAmount);
//        sendMessage(jsonBuilder.toString());
//    }
//
//    public void lobbyList() {
//        JsonBuilder jsonBuilder = new JsonBuilder();
//        jsonBuilder.add("type", "LOBBY_LIST");
//        sendMessage(jsonBuilder.toString());
//    }
}
