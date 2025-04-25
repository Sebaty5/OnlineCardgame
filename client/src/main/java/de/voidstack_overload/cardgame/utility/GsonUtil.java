package de.voidstack_overload.cardgame.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static <T> T toObject(JsonObject json, Class<T> objectClass) {
        try {
            return gson.fromJson(json, objectClass);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Gson toObject failed: " + e.getMessage());
        }
    }

    public static String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException("Gson toJson failed: " + e.getMessage());
        }
    }
}
