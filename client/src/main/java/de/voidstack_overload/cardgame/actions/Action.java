package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;

public interface Action {
    void execute(JsonObject json);
}
