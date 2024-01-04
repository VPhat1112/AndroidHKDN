package com.example.apphkdn.activity;

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
import com.example.apphkdn.adapter.CategoryAdapterSpiner;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.ultil.DownloadImageTask;
import com.example.apphkdn.ultil.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Seller_Update_Product extends AppCompatActivity {
    int id,Product_price,id_shop,id_category,product_review,product_numbersell;
    Integer idCategory;
    String product_name,Product_decs,Product_image,CategoryID;

    EditText updateName,updatePrice,updateNumber,updateDecs;
    TextView Btn_Back_Update;
    Spinner updateCategory;
    ImageView updateImage;
    Bitmap bitmap;
    Button Btn_Update;
    RequestDB requestDB = new RequestDB();
    CategoryAdapterSpiner categoryAdapterSpiner;
    String base64Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_update_product);
        GetDataExtraShare();
        UnitUI();
        SetDataExtraUpdate();
        EventAction();
    }
    private void UnitUI(){
        updateName= findViewById(R.id.Edt_Update_name);
        updatePrice=findViewById(R.id.Edt_Update_price);
        updateNumber=findViewById(R.id.Edt_Update_Number);
        updateDecs=findViewById(R.id.Edt_Update_Decs);
        updateCategory=findViewById(R.id.Edt_Update_Category);
        updateImage=findViewById(R.id.Edt_Update_image);
        Btn_Update=findViewById(R.id.Btn_Update);
        Btn_Back_Update=findViewById(R.id.Btn_Back_Update);
    }
    public ArrayList<Category> getListCategoryUpdate() {
        ArrayList<Category> ProductCategory = new ArrayList<Category>();
        ProductCategory = DataLocalManager.getListCategorySpinner();
        return ProductCategory;
    }
    private void settingSpinner(){
        categoryAdapterSpiner = new CategoryAdapterSpiner(Activity_Seller_Update_Product.this, R.layout.item_selected_spinner_category, getListCategoryUpdate());
        updateCategory.setAdapter(categoryAdapterSpiner);
        updateCategory.setSelection(index(getListCategoryUpdate(),id_category));
        updateCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryAdapterSpiner.getItem(position).getId();
                CategoryID=String.valueOf(idCategory);
//                String testID= String.valueOf(DataLocalManager.getIdCategoryUser());
//                Toast.makeText(Activity_Seller_Update_Product.this, idCategory.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private int index(ArrayList<Category> inArray,int categorychoose){
        for (int i=0;i<=inArray.size();i++){
            if (inArray.get(i).getId()==categorychoose){
                return i;
            }
        }
        return 0;
    }
    private void EventAction(){
        settingSpinner();

        ChooseImage();
        Update_Product();
//        Btn_Update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id_product = String.valueOf(id);
//                String Name = updateName.getText().toString();
//                String Price=updatePrice.getText().toString();
//                String Decs=updateDecs.getText().toString();
//                String NumberSell=updateNumber.getText().toString();
////                Toast.makeText(Activity_Seller_Update_Product.this, Name, Toast.LENGTH_SHORT).show();
////                Toast.makeText(Activity_Seller_Update_Product.this,CategoryID,Toast.LENGTH_SHORT).show();
////                UpdateTask updateTask = new UpdateTask(Activity_Seller_Update_Product.this,bitmap);
////                updateTask.execute(id_product,Name,Price,CategoryID,Decs,NumberSell);
//
//            }
//        });
    }

    private void GetDataExtraShare(){
        id=getIntent().getIntExtra("ID_Product",-1);
        product_name=getIntent().getStringExtra("product_name");
        Product_decs=getIntent().getStringExtra("Product_decs");
        Product_image=getIntent().getStringExtra("product_image");
        Product_price=getIntent().getIntExtra("product_price",-1);
        id_shop=getIntent().getIntExtra("ID_Shop",-1);
        id_category=getIntent().getIntExtra("ID_Category",-1);
        product_review=getIntent().getIntExtra("product_review",-1);
        product_numbersell=getIntent().getIntExtra("product_numbersell",-1);
    }
    private void SetDataExtraUpdate(){
        updateName.setText(product_name);
        updateDecs.setText(Product_decs);
        updatePrice.setText(String.valueOf(Product_price));
        updateNumber.setText(String.valueOf(product_numbersell));
        new DownloadImageTask(updateImage).execute(Product_image);
        Btn_Back_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    private static class UpdateTask extends AsyncTask<String, Void, String> {
//        private Bitmap bitmap;
//        private Context context;
//
//        public UpdateTask(Context context,Bitmap bitmap) {
//            this.bitmap = bitmap;
//            this.context=context;
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            String id_products = params[0];
//            String product_name = params[1];
//            String product_price = params[2];
//            String category_id = params[3];
//            String product_decs = params[4];
//            String product_numbersell = params[5];
//
//            String base64Image;
//
//            try {
//                ByteArrayOutputStream byteArrayOutputStream;
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                if (bitmap!=null){
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//                    byte[] bytes=byteArrayOutputStream.toByteArray();
//                    base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
//                    URL url = new URL(Server.UpdateProduct);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    // Prepare the data to be sent to the server
//                    String data = "id_product=" + id_products
//                            + "&product_name=" + product_name
//                            + "&product_price=" + product_price
//                            + "&product_image=" + base64Image
//                            + "&category_id=" + category_id
//                            + "&product_decs=" + product_decs
//                            + "&product_numbersell=" + product_numbersell;
//                    OutputStream os = httpURLConnection.getOutputStream();
//                    os.write(data.getBytes());
//                    os.flush();
//                    os.close();
//
//                    // Get the response from the server
//                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        response.append(line).append("\n");
//                    }
//                    br.close();
//
//                    return response.toString().trim();
//                }else{
//                    URL url = new URL(Server.UpdateProduct);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    String none="none";
//                    // Prepare the data to be sent to the server
//                    String data = "id_product=" + id_products
//                            + "&product_name=" + product_name
//                            + "&product_price=" + product_price
//                            + "&product_image=" + none
//                            + "&category_id=" + category_id
//                            + "&product_decs=" + product_decs
//                            + "&product_numbersell=" + product_numbersell;
//                    OutputStream os = httpURLConnection.getOutputStream();
//                    os.write(data.getBytes());
//                    os.flush();
//                    os.close();
//
//                    // Get the response from the server
//                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        response.append(line).append("\n");
//                    }
//                    br.close();
//
//                    return response.toString().trim();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Error: " + e.getMessage();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                Log.d("TestResult",result);
//                JSONObject jsonObject = new JSONObject(result);
//                boolean success = jsonObject.getBoolean("success");
//
//                if (success) {
//                    String remess= "Cập nhật sản phẩm thành công!";
//                    RequestDB.showInvalidOtpDialogAddProduct(context,remess);
//                } else {
//                    String remess= "Cập nhật sản phẩm thất bại!";
//                    RequestDB.showInvalidOtpDialogERROR(context,remess);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void Update_Product() {
        ChooseImage();
        Btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_product = String.valueOf(id);
                String Name = updateName.getText().toString();
                String Price=updatePrice.getText().toString();
                String Decs=updateDecs.getText().toString();
                String NumberSell=updateNumber.getText().toString();
                String Category = CategoryID;
                String base64Image;

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = Server.UpdateProduct;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("ADd product",response);
                                    if (response.equals("success")){
                                        String remess= "Cập nhật sản phẩm thành công!";
                                        RequestDB.showInvalidOtpDialogAddProduct(Activity_Seller_Update_Product.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "Cập nhật sản phẩm thất bại!";
                                        RequestDB.showInvalidOtpDialogERROR(Activity_Seller_Update_Product.this,remess);
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
                            params.put("id_product",id_product);
                            params.put("product_name",Name);
                            params.put("product_image",base64Image);
                            params.put("product_price",Price);
                            params.put("category_id",Category);
                            params.put("product_decs",Decs);
                            params.put("product_numbersell",NumberSell);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }else{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = Server.UpdateProduct;
                    String none="none";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String remess1= "Đã thêm sản phẩm thành công!";
                                    RequestDB.showInvalidOtpDialogUpdateProduct(Activity_Seller_Update_Product.this,remess1);
                                    if (response.equals("success")){
                                        String remess= "Đã thêm sản phẩm thành công!";
                                        RequestDB.showInvalidOtpDialogUpdateProduct(Activity_Seller_Update_Product.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "Thêm sản phẩm thất bại!";
                                        RequestDB.showInvalidOtpDialogERROR(Activity_Seller_Update_Product.this,remess);
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
                            params.put("id_product",id_product);
                            params.put("product_name",Name);
                            params.put("product_image",none);
                            params.put("product_price",Price);
                            params.put("category_id",Category);
                            params.put("product_decs",Decs);
                            params.put("product_numbersell",NumberSell);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
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
                                    updateImage.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Log.e("YourTag", "onActivityResult: Error loading image", e);
                                }
                            }
                        }
                    }
                });
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
    }



}