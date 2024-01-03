package com.example.apphkdn.DataLocalManager;

import android.content.Context;

import com.example.apphkdn.MySharedPreferences.MySharedPreferences;
import com.example.apphkdn.model.Category;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataLocalManager {

    private static final String PREF_ID_USER_LOGIN = "PREF_ID_USER_LOGIN";
    private static final String PREF_EMAIL_USER_LOGIN = "PREF_EMAIL_USER_LOGIN";
    private static final String PREF_LIST_CATEGORY_SPINNER = "PREF_LIST_CATEGORY_SPINNER";
    private static final String PREF_LIST_CATEGORY_SPINNER_ADD = "PREF_LIST_CATEGORY_SPINNER_ADD";
    private static final String PREF_NAME_USER_LOGIN = "PREF_NAME_USER_LOGIN";
    private static final String PREF_ADDRESS_USER_LOGIN = "PREF_ADDRESS_USER_LOGIN";
    private static final String PREF_ROLE_USER_LOGIN = "PREF_ROLE_USER_LOGIN";
    private static final String PREF_PHONE_USER_LOGIN = "PREF_PHONE_USER_LOGIN";
    private static final String PREF_INFOPAY_USER_LOGIN = "PREF_INFOPAY_USER_LOGIN";
    private static final String PREF_IMAGE_USER_LOGIN = "PREF_IMAGE_USER_LOGIN";
    private static final String PREF_IDSHOP_USER_LOGIN = "PREF_IDSHOP_USER_LOGIN";
    private static final String PREF_IDCATEGORY_USER_UPDATE = "PREF_IDCATEGORY_USER_UPDATE";
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

    public static void setIdUser(int value){
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(PREF_ID_USER_LOGIN ,value);
    }

    public static int getIdUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(PREF_ID_USER_LOGIN);
    }

    public static void setEmailUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_EMAIL_USER_LOGIN ,value);
    }

    public static String getEmailUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_EMAIL_USER_LOGIN);
    }

    public static void setNameUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_NAME_USER_LOGIN ,value);
    }

    public static String getNameUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_NAME_USER_LOGIN);
    }

    public static void setAddressUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_ADDRESS_USER_LOGIN ,value);
    }

    public static String getAddressUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_ADDRESS_USER_LOGIN);
    }

    public static void setRoleUser(int value){
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(PREF_ROLE_USER_LOGIN ,value);
    }

    public static int getRoleUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(PREF_ROLE_USER_LOGIN);
    }

    public static void setPhoneUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PHONE_USER_LOGIN ,value);
    }

    public static String getPhoneUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PHONE_USER_LOGIN);
    }

    public static void setInfoPayUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_INFOPAY_USER_LOGIN ,value);
    }

    public static String getInfoPayUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_INFOPAY_USER_LOGIN);
    }

    public static void setImageUser(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_IMAGE_USER_LOGIN ,value);
    }

    public static String getImageUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_IMAGE_USER_LOGIN);
    }

    public static void setIdShopUser(int value){
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(PREF_IDSHOP_USER_LOGIN ,value);
    }

    public static int getIdShopUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(PREF_IDSHOP_USER_LOGIN);
    }
    public static void setIdCategoryUser(int value){
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(PREF_IDCATEGORY_USER_UPDATE,value);
    }

    public static int getIdCategoryUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(PREF_IDCATEGORY_USER_UPDATE);
    }

    public static void setListCategorySpinner(ArrayList<Category> list){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_CATEGORY_SPINNER ,strJsonArray);
    }

    public static ArrayList<Category> getListCategorySpinner(){
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_CATEGORY_SPINNER);
        ArrayList<Category> list = new ArrayList<>();
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
    public static void ClearMySharedPreferences(){
        DataLocalManager.getInstance().mySharedPreferences.ClearMySharedPreferences();
    }
}
