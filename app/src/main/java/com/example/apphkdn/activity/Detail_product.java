package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetProductByShop;
import static com.example.apphkdn.ultil.Server.GetRatingPR;
import static com.example.apphkdn.ultil.Server.LinkGetShop;
import static com.example.apphkdn.ultil.Server.serverAddress;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.adapter.RatingAdapter;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.model.Rating;
import com.example.apphkdn.ultil.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Detail_product extends AppCompatActivity {
    ViewFlipper viewFlipper;
    int slcSP;
    private float initialX;
    ImageView imageView,ShopLogo;
    ImageButton backBtn,CartBtndtail,HomeBtnDt;
    TextView productName,ProductPrice,sells,editText,Name_Shop,TxtCheckkStatus;
    RatingBar ratingBar;
    Integer id=0,id_category=0,id_shop=0,product_review,product_numbersell;
    RecyclerView ShopProduct;
    RecyclerView Rating;
    int Product_price,status,product_selled,shop_rate;
    CardView plus,remove,colorStatus;

    String shop_name,shop_phone,Address,Image_shop,product_name,Product_decs,Product_image;

    RatingBar ratingshop;
    private List<String> imageUrls;

    RequestDB requestDB= new RequestDB();

    Button BtnCard;
    ArrayList<Product> productArrayListShop;


    ArrayList<Rating> ratingArrayList;

    RatingAdapter ratingAdapter;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        GetDataExtraShare();
        UnitUI();
        SetShopProduct();
        SetRatingProduct();
        slcSP=product_numbersell-product_selled;
        editText.setText("1/"+slcSP);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                if (slg<= Integer.parseInt(editText.getText().toString().split("/")[1]))
                    slg++;
                editText.setText(slg+"/"+slcSP);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                if (slg>1)
                    slg--;
                editText.setText(slg+"/"+slcSP);
            }
        });

        new GetShop().execute(id_shop.toString());

        productName.setText(product_name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        ProductPrice.setText(decimalFormat.format(Product_price)+"đ");
        sells.setText("Đã Bán "+product_selled);
        new DownloadImageTask(imageView).execute(Product_image);


        ActionViewFiller();

        BtnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartActivity.cartLists.size() >0){
                    int count=0;
                    int sl= Integer.parseInt(editText.getText().toString().split("/")[0]);
                    for (int i=0 ;i<CartActivity.cartLists.size();i++){
                        if (CartActivity.cartLists.get(i).getProduct_id()==id){
                            CartActivity.cartLists.get(i).setProduct_pay(CartActivity.cartLists.get(i).getProduct_pay()+sl);
                            if (CartActivity.cartLists.get(i).getProduct_pay()>=10){
                                CartActivity.cartLists.get(i).setProduct_pay(10);
                            }
                            CartActivity.cartLists.get(i).setProduct_price(Product_price*CartActivity.cartLists.get(i).getProduct_pay());
                            CartActivity.cartAdapter.notifyDataSetChanged();
                            count++;
                        }
                    }
                    if (count==0){
                        int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                        int new_price=slg*Product_price;
                        CartActivity.cartLists.add(new Cart(id,product_name,new_price,Product_image,"0",slg,product_numbersell,id_shop));
                        Toast.makeText(Detail_product.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                    int new_price=slg*Product_price;
                    CartActivity.cartLists.add(new Cart(id,product_name,new_price,Product_image,"0",slg,product_numbersell,id_shop));
                    Toast.makeText(Detail_product.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CartBtndtail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        HomeBtnDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ActionViewFiller() {

        // Pause flipping when touched (optional)
        viewFlipper.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = event.getX();
                    viewFlipper.stopFlipping();
                    break;

                case MotionEvent.ACTION_UP:
                    float finalX = event.getX();
                    if (initialX < finalX) {
                        viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
                        viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
                        viewFlipper.showPrevious();
                    } else if (initialX > finalX) {
                        viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        viewFlipper.showNext();
                    }
                    viewFlipper.startFlipping();
                    break;
            }
            return true;
        });
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
        product_selled=getIntent().getIntExtra("product_selled",-1);
    }

    private void SetShopProduct(){

        productArrayListShop = new ArrayList<>();
        productAdapter = new ProductAdapter(Detail_product.this,productArrayListShop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Detail_product.this, LinearLayoutManager.HORIZONTAL, false);
        ShopProduct.setHasFixedSize(true);
        ShopProduct.setLayoutManager(layoutManager);
        ShopProduct.setAdapter(productAdapter);
        requestDB.GetProductShopDetail(Detail_product.this,productArrayListShop,productAdapter,GetProductByShop+id_shop);
    }
    private void SetRatingProduct(){
        ratingArrayList= new ArrayList<>();
        ratingAdapter = new RatingAdapter(Detail_product.this,ratingArrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Detail_product.this, LinearLayoutManager.VERTICAL, false);
        Rating.setHasFixedSize(true);
        Rating.setLayoutManager(layoutManager1);
        Rating.setAdapter(ratingAdapter);
        requestDB.GetRattingProduct(Detail_product.this,ratingArrayList,ratingAdapter,GetRatingPR+id);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingArrayList= DataLocalManager.getListRating();
                int ratingcount=0;
                if (ratingArrayList.size()==0){
                    ratingshop.setRating(5);
                }else{
                    for (int i =0;i<ratingArrayList.size();i++){
                        ratingcount+=ratingArrayList.get(i).getRating();
                    }
                    int ratingsum=ratingcount/ratingArrayList.size();
                    Log.d("ratingsum", String.valueOf(ratingsum));
                    ratingshop.setRating(ratingsum);
                }
            }
        }, 200);
    }


    private class GetShop extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id_shop = params[0];
            try {
                URL url = new URL(LinkGetShop);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "id_shop=" + id_shop;
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Get the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }
                br.close();

                return response.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    id_shop = jsonObject.getInt("id");
                    shop_name = jsonObject.getString("shop_name");
                    shop_phone = jsonObject.getString("shop_phone");
                    shop_rate = jsonObject.getInt("shop_rate");
                    status=jsonObject.getInt("status");
                    Address = jsonObject.getString("Address");
                    setStatus(status);
                    Image_shop = serverAddress+jsonObject.getString("Image_shop");
                    new DownloadImageTask(ShopLogo).execute(Image_shop);
                    Name_Shop.setText(shop_name);
                    ratingshop.setRating(shop_rate);
                } else {
                    Toast.makeText(Detail_product.this, "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Detail_product.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setStatus(int checkStatus){
        if (checkStatus==0){
            TxtCheckkStatus.setText("Offline");
            colorStatus.setCardBackgroundColor(Color.parseColor("#B50000"));
        } else if (checkStatus==1) {
            TxtCheckkStatus.setText("Online");
            colorStatus.setCardBackgroundColor(Color.parseColor("#00B527"));
        }
    }
    private void UnitUI(){
        viewFlipper=findViewById(R.id.viewFlipperDT);
        productName=findViewById(R.id.nameProduct);
        ProductPrice=findViewById(R.id.Product_priceDT);
        imageView=findViewById(R.id.imageFiller1);
        BtnCard=findViewById(R.id.btnadd_Cart);
        sells=findViewById(R.id.sells);
        backBtn=findViewById(R.id.imageButtonback);
        editText= findViewById(R.id.edtSlgCart);
        plus=findViewById(R.id.btn_plusedtsl);
        remove=findViewById(R.id.btn_removeedtsl);
        Name_Shop=findViewById(R.id.namePRShop);
        ratingshop=findViewById(R.id.productRatingBar);
        ShopProduct=findViewById(R.id.recyclerSPDetail);
        ShopLogo=findViewById(R.id.imgShopDetai);
        TxtCheckkStatus=findViewById(R.id.CheckStatus);
        colorStatus=findViewById(R.id.colorStatus);
        CartBtndtail=findViewById(R.id.CartBtndtail);
        HomeBtnDt=findViewById(R.id.HomeBtnDt);
        Rating=findViewById(R.id.rcv_rating);
    }
}