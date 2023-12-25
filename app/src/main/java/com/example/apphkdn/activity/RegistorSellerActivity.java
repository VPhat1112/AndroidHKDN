package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.LinkRegistorSeller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegistorSellerActivity extends AppCompatActivity {

    EditText edtShopName, edtShopAddress, edtShopKind, edtReason;
    Button btnSignUp;
    ImageView ImgvShopImg;
    Bitmap bitmap;
    RequestDB requestDB = new RequestDB();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor_seller);
        initUI();
        Register();
    }

    private void Register(){
        uploadImg();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtShopName.getText().toString().trim();
                String address = edtShopAddress.getText().toString().trim();
                String kind = edtShopKind.getText().toString().trim();
                String Reason= edtReason.getText().toString().toString().trim();
                SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                String id_user = String.valueOf(preferences.getInt("id",0));
                String base64Image;

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = LinkRegistorSeller;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")){
                                        Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                                    }else if (response.equals("Faild to upload images to database")){
                                        Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                                    }else if (response.equals("Faild to upload images")) {
                                        Toast.makeText(getApplicationContext(),"Failed Image Uploaded",Toast.LENGTH_SHORT).show();
                                    }else if (response.equals("NO IMAGE FOUND"))
                                        Toast.makeText(getApplicationContext(),"NO IMAGE FOUND",Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<>();
                            params.put("shop_name",name);
                            params.put("ImageShop",base64Image);
                            params.put("Address",address);
                            params.put("shop_kind",kind);
                            params.put("idUser",id_user);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(),"Choose mage first",Toast.LENGTH_SHORT).show();
                }
//                new RegisterTask().execute(name,img,address,kind,id_user);
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImg(){
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK){
                            Intent data= result.getData();
                            Uri uri= data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                ImgvShopImg.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
        ImgvShopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
    }


//    private class RegisterTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String ... params) {
//            String name = params[0];
//            String img = params[1];
//            String address = params[2];
//            String kind = params[3];
////            String reason=params[4];
//            String id_User=params[4];
//
//            try {
//                URL url = new URL(Server.LinkRegistorSeller);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//
//                // Prepare the data to be sent to the server
//                String data = "NameShop=" + name + "&ImageShop=" + img + "&Address=" + address + "&KindShop=" + kind /*+ "&reason=" + reason*/+"&id_user=" + id_User;
//                OutputStream os = httpURLConnection.getOutputStream();
//                os.write(data.getBytes());
//                os.flush();
//                os.close();
//
//                // Get the response from the server
//                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null) {
//                    response.append(line).append("\n");
//                }
//                br.close();
//
//                return response.toString().trim();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Error: " + e.getMessage();
//            }
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                boolean success = jsonObject.getBoolean("success");
//
//                if (success) {
//                    // Login successful
//                    String remess= "Đăng ký thành công! Xin đợi phản hồi từ admin";
//                    showInvalidOtpDialog(remess);
//
//                    // You can save the user details in SharedPreferences or other storage
//                    // and navigate to the next activity
//                } else {
//                    // Login failed
////                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                    String remess= "Đăng ký không thành công!";
//                    showInvalidOtpDialog(remess);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
////                Toast.makeText(LoginActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void initUI(){
        edtShopName = findViewById(R.id.edt_shop_name);
        ImgvShopImg = findViewById(R.id.imgv_img_seller);
        edtShopAddress = findViewById(R.id.edt_address_seller);
        edtShopKind = findViewById(R.id.edt_shop_kind);
        edtReason = findViewById(R.id.edt_reason_seller);
        btnSignUp = findViewById(R.id.btn_signup_seller);
    }
}
