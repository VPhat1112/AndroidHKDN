package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.WriteRating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.ShopOrderDetailActivity;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ItemHolder> {
    Context context;
    ArrayList<Order> orderArrayList=new ArrayList<>();

    RequestDB requestDB= new RequestDB();

    public MyOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_myorder,null);
        ItemHolder itemHolder=new MyOrderAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Order order= orderArrayList.get(position);
        holder.TxtProductNameMyOrder.setText(order.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Price_MyOrder.setText(decimalFormat.format(order.getFinalTotal())+"đ");
        new DownloadImageTask(holder.ImgVIewProductMyOrder).execute(order.getProduct_image());
        holder.SL_MyOrder.setText("SL: "+order.getNumber_pay());

        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopOrderDetailActivity.class);
                intent.putExtra("id",String.valueOf(order.getOrder_id()));
                intent.putExtra("Shop_id",String.valueOf(order.getShop_id()));
                intent.putExtra("CreatedAt",order.getCreateAt());
                intent.putExtra("status",String.valueOf(order.getOrder_status()));
                intent.putExtra("Name",order.getName());
                intent.putExtra("SDT",order.getPhone());
                intent.putExtra("Address_ship",order.getAddress_ship());
                intent.putExtra("shopName",order.getShop_name());
                intent.putExtra("Product_name",order.getProduct_name());
                intent.putExtra("ImageProduct",order.getProduct_image());
                intent.putExtra("Number_Pay",String.valueOf(order.getNumber_pay()));
                intent.putExtra("Product_price",String.valueOf(order.getFinalTotal()));
                context.startActivity(intent);
            }
        });

        if (order.getOrder_status()==0){
            holder.txtStatus.setText("Đang chờ duyệt");
            holder.btn_buy_back_item_myorder.setVisibility(View.GONE);
        } else if (order.getOrder_status()==1) {
            holder.txtStatus.setText("Đang Giao");
            holder.btn_buy_back_item_myorder.setVisibility(View.GONE);
        } else if (order.getOrder_status()==2) {
            holder.txtStatus.setText("Đã hoàn thành");
        } else if (order.getOrder_status()==3) {
            holder.txtStatus.setText("Đơn hàng đã hủy");
        }
        holder.write_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context,R.style.Theme_AppHKDN);

                final  View customRatingLayout= LayoutInflater.from(context).inflate(R.layout.dialog_rating_product,null);
                builder.setView(customRatingLayout);
                builder.setTitle("Đánh giá sản phẩm");

                ImageView imageView=customRatingLayout.findViewById(R.id.rating_image);
                TextView textView=customRatingLayout.findViewById(R.id.rating_product_name);

                textView.setText(order.getProduct_name());
                new DownloadImageTask(imageView).execute(order.getProduct_image());

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
                        String product_id= String.valueOf(order.getProduct_id());
                        String user_id= String.valueOf(DataLocalManager.getIdUser());
                        requestDB.WriteRating(context,product_id,user_id,Comment,rating,WriteRating);

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
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView ImgVIewProductMyOrder;
        public RelativeLayout layoutproductMyOrder;
        public AppCompatButton Detail,btn_buy_back_item_myorder;
        public TextView TxtProductNameMyOrder,Price_MyOrder,SL_MyOrder,txtStatus,write_rating;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductMyOrder=itemView.findViewById(R.id.layoutMyOrder);
            ImgVIewProductMyOrder=itemView.findViewById(R.id.ImgVIewProductMyOrder);
            TxtProductNameMyOrder=itemView.findViewById(R.id.TxtProductNameMyOrder);
            Price_MyOrder=itemView.findViewById(R.id.Price_MyOrder);
            SL_MyOrder=itemView.findViewById(R.id.SL_MyOrder);
            txtStatus=itemView.findViewById(R.id.tv_item_myorder_status);
            Detail=itemView.findViewById(R.id.btn_detail_item_myorder);
            btn_buy_back_item_myorder=itemView.findViewById(R.id.btn_buy_back_item_myorder);
            write_rating=itemView.findViewById(R.id.write_rating);
        }
    }
}
