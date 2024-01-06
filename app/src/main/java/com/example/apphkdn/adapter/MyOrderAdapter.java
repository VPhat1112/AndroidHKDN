package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ItemHolder> {
    Context context;
    ArrayList<Order> orderArrayList=new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView ImgVIewProductMyOrder;

        public RelativeLayout layoutproductMyOrder;
        public TextView TxtProductNameMyOrder,Price_MyOrder,SL_MyOrder;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductMyOrder=itemView.findViewById(R.id.layoutMyOrder);
            ImgVIewProductMyOrder=itemView.findViewById(R.id.ImgVIewProductMyOrder);
            TxtProductNameMyOrder=itemView.findViewById(R.id.TxtProductNameMyOrder);
            Price_MyOrder=itemView.findViewById(R.id.Price_MyOrder);
            SL_MyOrder=itemView.findViewById(R.id.SL_MyOrder);
        }
    }
}
