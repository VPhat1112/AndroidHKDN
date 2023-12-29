package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.LinkGetIDCategoryByName;
import static com.example.apphkdn.ultil.Server.LinkRegistorSeller;
import static com.example.apphkdn.ultil.Server.linkCategory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistorSellerActivity extends AppCompatActivity {

    EditText edtShopName, edtShopAddress, edtShopKind, edtReason;
    Button btnSignUp;
    ImageButton BtnBack;
    ImageView ImgvShopImg;
    Spinner categoryspiner;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> arrSpinner;
    Bitmap bitmap;
    RequestDB requestDB = new RequestDB();
    ArrayList<Category> categoriesArrayList;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor_seller);
        initUI();
        Register();
        settingSpinner();
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void settingSpinner(){
        arrSpinner = new ArrayList<>();
        adapterSpinner = new ArrayAdapter<>(RegistorSellerActivity.this, android.R.layout.simple_spinner_dropdown_item, arrSpinner);
        requestDB.GetCategorySpinner(RegistorSellerActivity.this,arrSpinner, adapterSpinner, categoryspiner, linkCategory);
        SharedPreferences sharedPreferences= getSharedPreferences("MyProfile", MODE_PRIVATE);
        categoryspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString().trim();
                requestDB.GetIDCategorySpinner(RegistorSellerActivity.this, name, LinkGetIDCategoryByName);
                Integer idCategory = sharedPreferences.getInt("id_category_spinner",-1);
                Toast.makeText(RegistorSellerActivity.this, idCategory.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                                        String remess= "Đăng ký thành công! Xin đợi phản hồi từ admin";
                                        RequestDB.showInvalidOtpDialog(RegistorSellerActivity.this,remess);
                                    }else if (response.equals("Faild to upload images to database")){
                                        String remess= "Đăng ký không thành công!";
                                        RequestDB.showInvalidOtpDialogERROR(RegistorSellerActivity.this,remess);
                                    }else if (response.equals("Faild to upload images")) {
                                        String remess= "Failed Image Uploaded!";
                                        RequestDB.showInvalidOtpDialogERROR(RegistorSellerActivity.this,remess);
                                    }else if (response.equals("NO IMAGE FOUND")){
                                        String remess= "NO IMAGE FOUND!";
                                        RequestDB.showInvalidOtpDialogERROR(RegistorSellerActivity.this,remess);
                                    }
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

    private void initUI(){
        BtnBack=findViewById(R.id.Btn_Back);
        edtShopName = findViewById(R.id.edt_shop_name);
        ImgvShopImg = findViewById(R.id.imgv_img_seller);
        edtShopAddress = findViewById(R.id.edt_address_seller);
        categoryspiner = findViewById(R.id.spn_shop_kind);
        edtReason = findViewById(R.id.edt_reason_seller);
        btnSignUp = findViewById(R.id.btn_signup_seller);

    }
}
