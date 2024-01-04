package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.AddProduct;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.CategoryAdapterSpAdd;
import com.example.apphkdn.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivitySellerAddProduct extends AppCompatActivity {
    TextView TitleBack;
    EditText _txtAddNameProduct,_txtAddPriceProduct,_txtAddDecsProduct,_txtNumberSeller;
    ImageView _AddImageProduct;
    Button _SubmitAdd;
    Spinner spCategory;
    CategoryAdapterSpAdd categoryAdapterSpAdd;
    Integer idCategory;
    Bitmap bitmap;
    RequestDB requestDB = new RequestDB();

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
        settingSpinner();
    }

    private void settingSpinner(){
        categoryAdapterSpAdd = new CategoryAdapterSpAdd(ActivitySellerAddProduct.this, R.layout.item_selected_spinner_category, getListCategoryAdd());
        spCategory.setAdapter(categoryAdapterSpAdd);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryAdapterSpAdd.getItem(position).getId();
                //Toast.makeText(RegistorSellerActivity.this, idCategory.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public ArrayList<Category> getListCategoryAdd() {
        ArrayList<Category> ProductCategory = new ArrayList<Category>();
        ProductCategory = DataLocalManager.getListCategorySpinner();
        return ProductCategory;
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
                String Category = String.valueOf(idCategory);
                String id_shop = String.valueOf(DataLocalManager.getIdShopUser());
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
                                        RequestDB.showInvalidOtpDialogAddProduct(ActivitySellerAddProduct.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "Thêm sản phẩm thất bại!";
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
                        Log.d("YourTag", "onActivityResult: ResultCode - " + result.getResultCode());
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri uri = data.getData();
                                Log.d("YourTag", "onActivityResult: Image URI - " + uri);
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    _AddImageProduct.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Log.e("YourTag", "onActivityResult: Error loading image", e);
                                }
                            }
                        }
                    }
                });
        _AddImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
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