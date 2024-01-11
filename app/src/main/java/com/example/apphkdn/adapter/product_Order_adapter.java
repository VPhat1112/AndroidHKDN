package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.WriteRating;
import static com.example.apphkdn.ultil.Server.serverAddress;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.model.order_product;
import com.example.apphkdn.ultil.DownloadImageTask;
import com.example.apphkdn.ultil.ImageDownloadTask;

import java.util.ArrayList;

public class product_Order_adapter extends RecyclerView.Adapter<product_Order_adapter.ItemHolder> {

    Context context;
    ArrayList<order_product> orderProducts=new ArrayList<>();

    RequestDB requestDB= new RequestDB();

    public product_Order_adapter(Context context, ArrayList<order_product> orderProducts) {
        this.context = context;
        this.orderProducts = orderProducts;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_rcv_order_detail_for_buyer,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        order_product orderProduct= orderProducts.get(position);
        new ImageDownloadTask(holder.img_producr_buyer_order).execute(serverAddress+orderProduct.getProduct_image());
        holder.tv_product_name_order.setText(orderProduct.getProduct_name());
        holder.tv_product_price_order.setText(String.valueOf(orderProduct.getProduct_price()));
        holder.tv_product_quantity_order.setText(String.valueOf(orderProduct.getProduct_TotalPay()));
        holder.btn_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context,R.style.Theme_AppHKDN);

                final  View customRatingLayout= LayoutInflater.from(context).inflate(R.layout.dialog_rating_product,null);
                builder.setView(customRatingLayout);
                builder.setTitle("Đánh giá sản phẩm");

                ImageView imageView=customRatingLayout.findViewById(R.id.rating_image);
                TextView textView=customRatingLayout.findViewById(R.id.rating_product_name);

                textView.setText(orderProduct.getProduct_name());
                new DownloadImageTask(imageView).execute(serverAddress+orderProduct.getProduct_image());

                builder.setPositiveButton("xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText comment= customRatingLayout.findViewById(R.id.edt_rating_product);
                        RatingBar ratingBar=customRatingLayout.findViewById(R.id.productRatingBars);



                        String Comment="";
                        String rating="5";
                        if (comment.getText().toString()==""){
                            Comment="Sản phẩm rất tốt !!!";
                        }else {
                            Comment=comment.getText().toString();
                            rating= String.valueOf(ratingBar.getRating());
                        }
                        String product_id= String.valueOf(orderProduct.getProduct_id());
                        String user_id= String.valueOf(DataLocalManager.getIdUser());
                        requestDB.WriteRating(context,product_id,user_id,Comment,rating,WriteRating);
                        Log.d("writerangting",product_id+"-"+user_id+"-"+Comment+"-"+rating);

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
        return orderProducts.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView img_producr_buyer_order;
        TextView tv_product_name_order,tv_product_quantity_order,tv_product_price_order;
        AppCompatButton btn_danhgia;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            img_producr_buyer_order=itemView.findViewById(R.id.img_producr_buyer_order);
            tv_product_name_order=itemView.findViewById(R.id.tv_product_name_order);
            tv_product_quantity_order=itemView.findViewById(R.id.tv_product_quantity_order);
            tv_product_price_order=itemView.findViewById(R.id.tv_product_price_order);
            btn_danhgia=itemView.findViewById(R.id.btn_danhgia);
        }
    }
}
