package de.voidstack_overload.cardgame_semiold.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonBuilder {
    private final JsonObject jsonObject;

    public JsonBuilder() {
        this.jsonObject = new JsonObject();
    }

    public JsonBuilder add(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonBuilder add(String key, Number value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonBuilder add(String key, Boolean value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonBuilder add(String key, JsonElement element) {
        jsonObject.add(key, element);
        return this;
    }

    public JsonBuilder addArray(String key, JsonArray jsonArray) {
        jsonObject.add(key, jsonArray);
        return this;
    }

    public JsonObject build() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return jsonObject.toString(); // Return JSON string
    }
}