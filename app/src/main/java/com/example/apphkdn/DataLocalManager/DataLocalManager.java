package com.example.apphkdn.DataLocalManager;

import android.content.Context;
import android.util.Log;

import com.example.apphkdn.MySharedPreferences.MySharedPreferences;
import com.example.apphkdn.model.AutoTextViewItems;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Rating;
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
    private static final String PREF_LIST_AUTOTEXTVIEW = "PREF_LIST_AUTOTEXTVIEW";
    private static final String PREF_NAME_SHOP = "PREF_NAME_SHOP";
    private static final String PREF_IMAGE_SHOP = "PREF_IMAGE_SHOP";
    private static final String PREF_LIST_RATING = "PREF_LIST_RATING";
    private static final String PREF_PRODUCT_QUANTITY = "PREF_PRODUCT_QUANTITY";
    private static final String PREF_PRODUCT_NAME = "PREF_PRODUCT_NAME";
    private static final String PREF_PRODUCT_IMAGE = "PREF_PRODUCT_IMAGE";
    private static final String PREF_PRODUCT_TOTALPAY = "PREF_PRODUCT_TOTALPAY";
    private static final String PREF_PRODUCT_QUANTITY_SHOP = "PREF_PRODUCT_QUANTITY_SHOP";
    private static final String PREF_PRODUCT_NAME_SHOP = "PREF_PRODUCT_NAME_SHOP";
    private static final String PREF_PRODUCT_IMAGE_SHOP = "PREF_PRODUCT_IMAGE_SHOP";
    private static final String PREF_PRODUCT_TOTALPAY_SHOP = "PREF_PRODUCT_TOTALPAY_SHOP";
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

    public static void setNameShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_NAME_SHOP, value);
    }

    public static String getNameShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_NAME_SHOP);
    }

    public static void setImageShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_IMAGE_SHOP, value);
    }

    public static String getImageShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_IMAGE_SHOP);
    }
    public static void setquantity(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_QUANTITY, value);
    }

    public static String getquantity(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_QUANTITY);
    }
    public static void setproduct_name(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_NAME, value);
    }

    public static String getproduct_name(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_NAME);
    }
    public static void setproduct_image(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_IMAGE, value);
    }

    public static String getproduct_image(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_IMAGE);
    }
    public static void setproduct_totalPay(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_TOTALPAY, value);
    }

    public static String getproduct_totalPay(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_TOTALPAY);
    }

    public static void setQuantityShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_QUANTITY_SHOP, value);
    }

    public static String getQuantityShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_QUANTITY_SHOP);
    }
    public static void setProductNameShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_NAME_SHOP, value);
    }

    public static String getProductNameShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_NAME_SHOP);
    }
    public static void setProductImgShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_IMAGE_SHOP, value);
    }

    public static String getProductImgShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_IMAGE_SHOP);
    }
    public static void setProductTotalShop(String value){
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_PRODUCT_TOTALPAY_SHOP, value);
    }

    public static String getProductTotalShop(){
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_PRODUCT_TOTALPAY_SHOP);
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
    public static void setListRating(ArrayList<Rating> list){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_RATING ,strJsonArray);
    }

    public static ArrayList<Rating> getListRating(){
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_RATING);
        ArrayList<Rating> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJsonArray);
            JSONObject jsonObject;
            Rating rating;
            Gson gson = new Gson();
            for (int i = 0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                rating = gson.fromJson(jsonObject.toString(), Rating.class);
                list.add(rating);
            }
            Log.d("JsonA",list.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static void setListAutotextview(ArrayList<AutoTextViewItems> list){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_AUTOTEXTVIEW ,strJsonArray);
    }

    public static ArrayList<AutoTextViewItems> getListAutotextview(){
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_AUTOTEXTVIEW);
        ArrayList<AutoTextViewItems> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJsonArray);
            JSONObject jsonObject;
            AutoTextViewItems autoTextViewItems;
            Gson gson = new Gson();
            for (int i = 0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                autoTextViewItems = gson.fromJson(jsonObject.toString(), AutoTextViewItems.class);
                list.add(autoTextViewItems);
            }
            Log.d("Etsa",list.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void ClearMySharedPreferences(){
        DataLocalManager.getInstance().mySharedPreferences.ClearMySharedPreferences();
    }
}
