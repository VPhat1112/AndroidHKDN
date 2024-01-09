package com.example.apphkdn.RequestDB;

import static com.example.apphkdn.ultil.Server.serverAddress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.activity.MainActivity;
import com.example.apphkdn.activity.MyOrderActivity;
import com.example.apphkdn.activity.SellerOrderActivity;
import com.example.apphkdn.activity.ShopSellerProductActivity;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.adapter.MyOrderAdapter;
import com.example.apphkdn.adapter.OrderAdapter;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.adapter.ProductShopAdapter;
import com.example.apphkdn.adapter.RatingAdapter;
import com.example.apphkdn.model.AutoTextViewItems;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.model.Rating;

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
                                serverAddress + object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled"),
                                object.getInt("status")
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
    public void GetRattingProduct(Context context, ArrayList<Rating> ratingArrayList, RatingAdapter ratingAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        ratingArrayList.add(new Rating(
                                object.getInt("Rating"),
                                object.getString("Comment"),
                                object.getString("id_product"),
                                object.getString("Name")
                        ));
                        DataLocalManager.setListRating(ratingArrayList);
                    }
                    ratingAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void GetProductBySearch(Context context, ArrayList<Product> productArrayList, ProductAdapter productAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Error")){
                    Toast.makeText(context, "Khong tim thay", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            productArrayList.add(new Product(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("product_name"),
                                    serverAddress + jsonObject.getString("product_image"),
                                    jsonObject.getString("product_decs"),
                                    jsonObject.getInt("product_price"),
                                    jsonObject.getInt("IDcategory"),
                                    jsonObject.getInt("id_shop"),
                                    jsonObject.getInt("product_review"),
                                    jsonObject.getInt("product_numbersell"),
                                    jsonObject.getInt("product_selled"),
                                    jsonObject.getInt("status")
                            ));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Parsing Json", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
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
                                serverAddress + object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled"),
                                object.getInt("status")
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
    public void GetProductShopDetail(Context context, ArrayList<Product> productArrayListSeller, ProductAdapter productAdapter, String url){
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
                                serverAddress + object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled"),
                                object.getInt("status")
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
                                serverAddress + object.getString("category_image")
                        ));
                        DataLocalManager.setListCategorySpinner(categoriesArrayList);
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
    public void InsertOrder(Context context,Integer BillTotal, Integer shopId, Integer buyerId, Integer productId, Integer numberOfProduct, Integer productPrice, Integer totalsOfOneProduct,String Address_ship,String Phone, String url){
        RequestQueue queue =Volley.newRequestQueue(context);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String title="Đặt hàng";
                    if (!jsonObject.getBoolean("successOrder")){
                        String remess= "Error insert order!";
                        RequestDB.showInvalidOtpDialogERRORSaveOrder(context,title,remess);
                    } else if (!jsonObject.getBoolean("successContact")){
                        String remess= "Error insert order_contact!";
                        RequestDB.showInvalidOtpDialogERRORSaveOrder(context,title,remess);
                    } else if (!jsonObject.getBoolean("successOrderDetail")){
                        String remess= "Error insert order_detail!";
                        RequestDB.showInvalidOtpDialogERRORSaveOrder(context,title,remess);
                    } else {
                        String remess= "Bạn đã đặt hàng thành công \n Xin Chân thành cảm ơn!";
                        RequestDB.showInvalidOtpDialogSaveOrder(context,title,remess);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("BillTotal", String.valueOf(BillTotal));
                params.put("Shop_id", String.valueOf(shopId));
                params.put("buyer_id", String.valueOf(buyerId));
                params.put("Product_id", String.valueOf(productId));
                params.put("Number_pay", String.valueOf(numberOfProduct));
                params.put("product_price", String.valueOf(productPrice));
                params.put("Product_TotalPay", String.valueOf(totalsOfOneProduct));
                params.put("Address_ship", Address_ship);
                params.put("Phone", Phone);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void getDataAutotextViewSearch(Context context, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<AutoTextViewItems> items = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        items.add(new AutoTextViewItems(
                                jsonObject.getString("Image_shop"),
                                jsonObject.getString("name"),
                                jsonObject.getString("id")
                        ));
                    }
                DataLocalManager.setListAutotextview(items);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Parsing Json", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void GetOrder(Context context, ArrayList<Order> orderArrayList, OrderAdapter orderAdapter, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        orderArrayList.add(new Order(
                                object.getInt("user_id"),
                                object.getInt("shop_id"),
                                object.getInt("order_id"),
                                object.getInt("contact_id"),
                                object.getInt("product_id"),
                                object.getInt("FinalTotal"),
                                object.getInt("Order_status"),
                                object.getInt("Number_pay"),
                                object.getString("product_name"),
                                serverAddress+object.getString("product_image"),
                                object.getString("CreatedAt"),
                                object.getString("Name"),
                                object.getString("Phone"),
                                object.getString("Address_ship"),
                                object.getString("Shop_name")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
                orderAdapter.notifyDataSetChanged();
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
    public void GetShop(Context context,String idShop, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jsonObject.getString("shop_name");
                    jsonObject.getString("Image_shop");
                    DataLocalManager.setNameShop(jsonObject.getString("shop_name"));
                    DataLocalManager.setImageShop(jsonObject.getString("Image_shop"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR PARSING JSON", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id_shop", idShop);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void GetMyOrder(Context context, ArrayList<Order> orderArrayList, MyOrderAdapter myOrderAdapter, String url){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        orderArrayList.add(new Order(
                                object.getInt("user_id"),
                                object.getInt("shop_id"),
                                object.getInt("order_id"),
                                object.getInt("contact_id"),
                                object.getInt("product_id"),
                                object.getInt("FinalTotal"),
                                object.getInt("Order_status"),
                                object.getInt("Number_pay"),
                                object.getString("product_name"),
                                serverAddress+object.getString("product_image"),
                                object.getString("CreatedAt"),
                                object.getString("Name"),
                                object.getString("Phone"),
                                object.getString("Address_ship"),
                                object.getString("Shop_name")



                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
                myOrderAdapter.notifyDataSetChanged();
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
    public void Filter(Context context, ArrayList<Product> productArrayList, ProductAdapter productAdapter, String price1, String price2, String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        productArrayList.add(new Product(
                                object.getInt("id"),
                                object.getString("product_name"),
                                serverAddress + object.getString("product_image"),
                                object.getString("product_decs"),
                                object.getInt("product_price"),
                                object.getInt("IDcategory"),
                                object.getInt("id_shop"),
                                object.getInt("product_review"),
                                object.getInt("product_numbersell"),
                                object.getInt("product_selled"),
                                object.getInt("status")
                        ));
                    }
                    productAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR PARSING JSON: ", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("filterPriceLow",price1);
                params.put("filterPriceHight",price2);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void WriteRating(Context context,String product_id,String user_id , String comment, String rating,String url) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String title="Đánh giá";
                    if (!jsonObject.getBoolean("success")) {
                        String remess = "Đánh giá thất bại!";
                        RequestDB.showInvalidOtpDialogERRORSaveOrder(context,title,remess);
                    }else {
                        String remess= "Đánh giá thành công \n Xin Chân thành cảm ơn!";
                        RequestDB.showInvalidOtpDialogSaveOrder(context,title,remess);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", String.valueOf(product_id));
                params.put("user_id", String.valueOf(user_id));
                params.put("comment", String.valueOf(comment));
                params.put("rating_star", String.valueOf(rating));
                return params;
            }
        };
        queue.add(stringRequest);
    }


    //DiaLog success and failed
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
                context.startActivity(new Intent(context,MainActivity.class));
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
                Intent intent = new Intent(context, ShopSellerProductActivity.class);
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
    public static void showInvalidOtpDialogUpdateProduct(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Update Product");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, ShopSellerProductActivity.class);
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
    public static void showInvalidOtpDialogLockProduct(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Lock Product");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, ShopSellerProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
    public static void showInvalidOtpDialogAcceptOrder(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Nhận đơn");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, SellerOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
    public static void showInvalidOtpDialogSaveOrder(Context context,String title, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle(title);
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(context, MyOrderActivity.class);
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
    public static void showInvalidOtpDialogERRORSaveOrder(Context context,String title, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle(title);
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
