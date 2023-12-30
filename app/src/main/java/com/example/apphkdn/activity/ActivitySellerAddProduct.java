package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.AddProduct;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActivitySellerAddProduct extends AppCompatActivity {
    TextView TitleBack;
    EditText _txtAddNameProduct,_txtAddPriceProduct,_txtAddDecsProduct,_txtNumberSeller,spCategory;
    ImageView _AddImageProduct;
    Button _SubmitAdd;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_product);
        UnitUI();
        EventAction();
    }
    private void EventAction(){
        TitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Add_Product();
    }

    private void Add_Product() {
        ChooseImage();
        _SubmitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _txtAddNameProduct.getText().toString().trim();
                String price = _txtAddPriceProduct.getText().toString().trim();
                String decs = _txtAddDecsProduct.getText().toString().trim();
                String NumberSell= String.valueOf(_txtNumberSeller.getText());
                String Category = spCategory.getText().toString().trim();
                SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                String id_shop = String.valueOf(preferences.getInt("id_shop",0));
                String base64Image;

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = AddProduct;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")){
                                        String remess= "Đã thêm sản phẩm thành công!";
                                        RequestDB.showInvalidOtpDialog(ActivitySellerAddProduct.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "Thêm sản phẩm thành công thất bại!";
                                        RequestDB.showInvalidOtpDialogERROR(ActivitySellerAddProduct.this,remess);
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
                            params.put("product_name",name);
                            params.put("product_image",base64Image);
                            params.put("product_price",price);
                            params.put("category_id",Category);
                            params.put("product_decs",decs);
                            params.put("id_shop",id_shop);
                            params.put("product_numbersell",NumberSell);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(),"Choose image first",Toast.LENGTH_SHORT).show();
                }
            }
        });

//                new RegisterTask().execute(name,img,address,kind,id_user);
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
    }

    private void ChooseImage() {
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK){
                            Intent data= result.getData();
                            Uri uri= data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                _AddImageProduct.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
        _AddImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
    }


    private void UnitUI(){
        TitleBack=findViewById(R.id.TilrleBack);
        _txtAddNameProduct=findViewById(R.id.AddNameProduct_edt);
        _txtAddPriceProduct=findViewById(R.id.AddPriceProduct_edt);
        _txtAddDecsProduct=findViewById(R.id.AddDecsProduct_edt);
        _SubmitAdd=findViewById(R.id.SubmitAdd);
        _AddImageProduct=findViewById(R.id.AddImageProduct_edt);
        _txtNumberSeller=findViewById(R.id.NumberSell);
        spCategory=findViewById(R.id.spCategory);
    }
}