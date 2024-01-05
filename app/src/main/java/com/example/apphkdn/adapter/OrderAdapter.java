package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ItemHolder> {
    Context context;
    ArrayList<Order> orderArrayList;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_rcv,null);
        ItemHolder itemHolder=new OrderAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ItemHolder holder, int position) {
        Order order= orderArrayList.get(position);
        holder.TxtProductNameShopOrder.setText(order.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Price_Order.setText(decimalFormat.format(order.getFinalTotal())+"đ");
        new DownloadImageTask(holder.ImgVIewProductShopOrder).execute(order.getProduct_image());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView ImgVIewProductShopOrder;
        ImageButton edit_accept_product,edit_deni_product;

        public LinearLayout layoutproductShop;
        public TextView TxtProductNameShopOrder,Price_Order,SL_order;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductShop=itemView.findViewById(R.id.layoutProductShop);
            ImgVIewProductShopOrder=itemView.findViewById(R.id.ImgVIewProductShopOrder);
            TxtProductNameShopOrder=itemView.findViewById(R.id.TxtProductNameShopOrder);
            Price_Order=itemView.findViewById(R.id.Price_Order);SL_order=itemView.findViewById(R.id.SL_order);
            edit_accept_product=itemView.findViewById(R.id.edit_accept_product);
            edit_deni_product=itemView.findViewById(R.id.edit_deni_product);
        }
    }
}
