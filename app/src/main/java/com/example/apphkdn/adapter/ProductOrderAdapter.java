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
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ItemHolder>{
    List<Cart> cartLists;
    Context context;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public ProductOrderAdapter(Context context, ArrayList<Cart> cartLists) {
        this.context = context;
        this.cartLists = cartLists;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product_checkout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.ItemHolder holder, int position) {
        Cart cartList = cartLists.get(position);
        int i=position;
        new DownloadImageTask(holder.imageProduct).execute(cartList.getProduct_image());
//        Picasso.get().load(cartList.getProduct_image()).into(holder.imgCart);
        holder.txtProduct_price.setText(String.valueOf(decimalFormat.format(cartList.getProduct_price()))+"đ");
        holder.txtProduct_name.setText(cartList.getProduct_name());
        holder.txtProduct_Pay.setText("SL: "+String.valueOf(cartList.getProduct_pay()));
    }

    @Override
    public int getItemCount() {
        return cartLists.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageProduct;

        public RelativeLayout layoutproduct;
        public TextView txtProduct_name,txtProduct_price,txtProduct_Pay;
        public ItemHolder(View itemview){
            super(itemview);
            layoutproduct=itemview.findViewById(R.id.Product_checkout);
            imageProduct=itemview.findViewById(R.id.imgCartt_order);
            txtProduct_name=itemview.findViewById(R.id.nameCartt_order);
            txtProduct_price=itemview.findViewById(R.id.Price_Product);
            txtProduct_Pay=itemview.findViewById(R.id.SLCartt_order);

        }
    }
}
