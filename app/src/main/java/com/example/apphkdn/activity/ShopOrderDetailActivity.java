package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.WriteRating;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.ultil.DownloadImageTask;
import com.example.apphkdn.ultil.ImageDownloadTask;

import java.text.DecimalFormat;

public class ShopOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton back_Btn_Detail, btn_danhgia;
    ImageView img_producr_buyer_order;
    TextView txtOrder_ID,txtCreatedAt,StatusDetail,tv_name_buyer_order,txtSDTDetail,txtAddress,tv_name_shop_seller_order,tv_product_name_order,tv_product_quantity_order,tv_product_price_order;
    Integer Order_Id,Status,Price,Quantity;
    String CreatedAt,NameBuyer,SDT,Address,Name_Shop,Product_Name,Image;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    RequestDB requestDB = new RequestDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order_detail);
        UnitUI();
        GetData();
        setData();
        EventAction();
    }
    private void EventAction(){

        back_Btn_Detail.setOnClickListener(this);
    }

    private void GetData(){
        //GetData
        Order_Id= Integer.valueOf(getIntent().getStringExtra("id"));
        CreatedAt=getIntent().getStringExtra("CreatedAt");
        Status= Integer.parseInt(getIntent().getStringExtra("status"));
        NameBuyer=getIntent().getStringExtra("Name");
        SDT=getIntent().getStringExtra("SDT");
        Address=getIntent().getStringExtra("Address_ship");
        Name_Shop=getIntent().getStringExtra("shopName");
        Product_Name=getIntent().getStringExtra("Product_name");
        Quantity= Integer.valueOf(getIntent().getStringExtra("Number_Pay"));
        Price= Integer.valueOf(getIntent().getStringExtra("Product_price"));
        Image=getIntent().getStringExtra("ImageProduct");

        Log.d("data tintten",String.valueOf(Order_Id)+" "+CreatedAt+" "+NameBuyer+" "+SDT+" "+Address+" "+Name_Shop+" "+Product_Name+" "+String.valueOf(Quantity)+" "+Image);
    }
    private void setData(){
        txtOrder_ID.setText(String.valueOf(Order_Id));
        txtCreatedAt.setText(CreatedAt);
        if (Status==0){
            StatusDetail.setText("Đang chờ duyệt");
        }else if(Status==1){
            StatusDetail.setText("Đang Giao");
        }else if(Status==2){
            StatusDetail.setText("Đã hoàn thành");
        }else if(Status==3){
            StatusDetail.setText("Đã Hủy");
        }
        tv_name_buyer_order.setText(NameBuyer);
        txtSDTDetail.setText(SDT);
        txtAddress.setText(Address);
        tv_name_shop_seller_order.setText(Name_Shop);
        tv_product_name_order.setText(Product_Name);
        tv_product_quantity_order.setText("SL: "+String.valueOf(Quantity)+" ");
        tv_product_price_order.setText(decimalFormat.format(Price)+"đ");
        new ImageDownloadTask(img_producr_buyer_order).execute(Image);
    }

    private void UnitUI(){
        back_Btn_Detail=findViewById(R.id.back_Btn_Detail);
        txtOrder_ID=findViewById(R.id.txtOrder_ID);
        txtCreatedAt=findViewById(R.id.txtCreatedAt);
        StatusDetail=findViewById(R.id.StatusDetail);
        tv_name_buyer_order=findViewById(R.id.tv_name_buyer_order);
        txtSDTDetail=findViewById(R.id.txtSDTDetail);
        txtAddress=findViewById(R.id.txtAddress);
        tv_name_shop_seller_order=findViewById(R.id.tv_name_shop_seller_order);
        tv_product_name_order=findViewById(R.id.tv_product_name_order);
        tv_product_quantity_order=findViewById(R.id.tv_product_quantity_order);
        tv_product_price_order=findViewById(R.id.tv_product_price_order);
        img_producr_buyer_order=findViewById(R.id.img_producr_buyer_order);
        btn_danhgia = findViewById(R.id.btn_danhgia);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_Btn_Detail){
            finish();
        }
        if (v.getId() == R.id.btn_danhgia){
            AlertDialog.Builder builder= new AlertDialog.Builder(ShopOrderDetailActivity.this,R.style.Theme_AppHKDN);

            final  View customRatingLayout= LayoutInflater.from(ShopOrderDetailActivity.this).inflate(R.layout.dialog_rating_product,null);
            builder.setView(customRatingLayout);
            builder.setTitle("Đánh giá sản phẩm");

            ImageView imageView=customRatingLayout.findViewById(R.id.rating_image);
            TextView textView=customRatingLayout.findViewById(R.id.rating_product_name);

            textView.setText(Product_Name);
            new DownloadImageTask(imageView).execute(Image);

            builder.setPositiveButton("xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText comment= customRatingLayout.findViewById(R.id.edt_rating_product);
                    RatingBar ratingBar=customRatingLayout.findViewById(R.id.productRatingBar);



                    String Comment="";
                    String rating="5";
                    if (comment.getText().toString()==""){
                        Comment="Sản phẩm rất tốt !!!";
                    }else {
                        Comment=comment.getText().toString();
                        rating= String.valueOf(ratingBar.getRating());
                    }
//                    String product_id= String.valueOf(order.getProduct_id());
                    String user_id= String.valueOf(DataLocalManager.getIdUser());
//                    requestDB.WriteRating(ShopOrderDetailActivity.this,product_id,user_id,Comment,rating,WriteRating);

                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog= builder.create();
            dialog.show();
        }
    }
}