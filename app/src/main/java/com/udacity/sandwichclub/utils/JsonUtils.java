package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String SANDWICH_MAIN_NAME = "mainName";
    public static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String SANDWICH_DESCRIPTION = "description";
    public static final String SANDWICH_IMAGE = "image";
    public static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        String sandwichMainName = null, placeOfOrigin = null, sandwichDesc = null, sandwichImage = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> sandwichIngredients = new ArrayList<>();
        JSONObject sandwichObj = new JSONObject(json);
        if (sandwichObj != null) {
            JSONObject sandwichName = sandwichObj.getJSONObject("name");
            if (sandwichName != null) {
                if (sandwichName.getString(SANDWICH_MAIN_NAME) != null) {
                    sandwichMainName = sandwichName.getString(SANDWICH_MAIN_NAME);
                }
                JSONArray alsoKnownAsArray = sandwichName.getJSONArray(SANDWICH_ALSO_KNOWN_AS);
                if (alsoKnownAsArray != null) {
                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        String item = alsoKnownAsArray.get(i).toString();
                        alsoKnownAs.add(item);
                    }
                }
            }
            if (sandwichObj.getString(SANDWICH_PLACE_OF_ORIGIN) != null) {
                placeOfOrigin = sandwichObj.getString(SANDWICH_PLACE_OF_ORIGIN);
            }
            if (sandwichObj.getString(SANDWICH_DESCRIPTION) != null) {
                sandwichDesc = sandwichObj.getString(SANDWICH_DESCRIPTION);
            }
            if (sandwichObj.getString(SANDWICH_IMAGE) != null) {
                sandwichImage = sandwichObj.getString(SANDWICH_IMAGE);
            }

            JSONArray ingredientsArray = sandwichObj.getJSONArray(SANDWICH_INGREDIENTS);
            if (ingredientsArray != null) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String item = ingredientsArray.get(i).toString();
                    sandwichIngredients.add(item);
                }
            }
        } else
            return null;
        return new Sandwich(sandwichMainName, alsoKnownAs, placeOfOrigin, sandwichDesc, sandwichImage, sandwichIngredients);
    }
}
