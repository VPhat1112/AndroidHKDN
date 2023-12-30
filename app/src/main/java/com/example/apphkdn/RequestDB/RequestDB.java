package com.example.apphkdn.RequestDB;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.activity.MainActivity;
import com.example.apphkdn.activity.ShopSellerActivity;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.adapter.ProductShopAdapter;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestDB {
    public void GetProduct(Context context, ArrayList<Product> productArrayList, ProductAdapter productAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        productArrayList.add(new Product(
                                object.getInt("id"),
                                object.getString("product_name"),
                                object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void GetProductShop(Context context, ArrayList<Product> productArrayListSeller, ProductShopAdapter productShopAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        productArrayListSeller.add(new Product(
                                object.getInt("id"),
                                object.getString("product_name"),
                                object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
                productShopAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
    public void GetCategory(Context context, ArrayList<Category> categoriesArrayList, CategoryAdapter categoryAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        categoriesArrayList.add(new Category(
                                object.getInt("id"),
                                object.getString("category_name"),
                                object.getString("category_image")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }




    public void Login(Context context, String url, String email, String pass){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //boolean success = jsonObject.getBoolean("success");
                if (response != null){
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            if (object.getInt("is_verified") == 0){
                                showInvalidOtpDialog(context);
                            } else{
                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = context.getSharedPreferences("MyProfile", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("id", object.getInt("id"));
                                editor.putString("email", object.getString("email"));
                                editor.putString("Name", object.getString("Name"));
                                editor.putString("Address", object.getString("Address"));
                                editor.putInt("role", object.getInt("role"));
                                editor.putString("phone", object.getString("Phone"));
                                editor.putString("Info_pay", object.getString("Info_Pay"));
                                editor.putString("imgUS", object.getString("imgUS"));
                                editor.apply();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else{
                    // Login failed
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("Passwords", pass);
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

//    public void RegistorSeller(Context context, String url, String name, String img, String address, String kind, String id_User){
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.toString().equals("success")){
//                    String remess= "Đăng ký thành công! Xin đợi phản hồi từ admin";
//                    showInvalidOtpDialog(context,remess);
//                } else{
//                    String remess= "Đăng ký không thành công!";
//                    showInvalidOtpDialog(context,remess);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("shop_name",name);
//                params.put("ImageShop",img);
//                params.put("Address",address);
//                params.put("shop_kind",kind);
//                params.put("idUser",id_User);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
    public void showInvalidOtpDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView myMsg = new TextView(context);
        builder.setTitle("Login with Password");
        builder.setMessage("Your email not verified !!! \n Please login with OTP to active your email !!!");

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Must call show() prior to fetching text view
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
        if (titleView != null) {
            titleView.setGravity(Gravity.CENTER);
        }
    }

    public static void showInvalidOtpDialog(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Register Seller");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Must call show() prior to fetching text view
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
        if (titleView != null) {
            titleView.setGravity(Gravity.CENTER);
        }
    }
    public static void showInvalidOtpDialogAddProduct(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Add Product");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, ShopSellerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Must call show() prior to fetching text view
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
        if (titleView != null) {
            titleView.setGravity(Gravity.CENTER);
        }
    }
    public static void showInvalidOtpDialogERROR(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Register Seller");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Must call show() prior to fetching text view
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
        if (titleView != null) {
            titleView.setGravity(Gravity.CENTER);
        }
    }


}
