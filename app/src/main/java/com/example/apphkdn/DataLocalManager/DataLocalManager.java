package com.example.apphkdn.DataLocalManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.apphkdn.MySharedPreferences.MySharedPreferences;
import com.example.apphkdn.model.Category;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {

    private static final String PREF_LIST_CATEGORY_SPINNER = "PREF_LIST_CATEGORY_SPINNER";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance(){
        if (instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setListCategorySpinner(List<Category> list){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_CATEGORY_SPINNER ,strJsonArray);
    }

    public static List<Category> getListCategorySpinner(){
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_CATEGORY_SPINNER);
        List<Category> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJsonArray);
            JSONObject jsonObject;
            Category category;
            Gson gson = new Gson();
            for (int i = 0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                category = gson.fromJson(jsonObject.toString(), Category.class);
                list.add(category);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
