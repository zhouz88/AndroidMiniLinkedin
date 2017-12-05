package com.example.kerwinzhou.minilinkedin.utils;

/**
 * Created by kerwinzhou on 12/5/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Created by kerwinzhou on 11/26/17.
 */

public class ModelUtils {
    private static Gson gson = new Gson();
    private static String PREF_NAME = "models";


    public static void save(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = gson.toJson(object);
        sp.edit().putString(key, jsonString).apply();
    }

    public static<T> T read(Context context, String key, TypeToken<T> typeToken) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        try{
            return gson.fromJson(sp.getString(key,""), typeToken.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
