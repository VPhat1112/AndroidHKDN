package com.example.apphkdn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product= productArrayList.get(position);
        holder.txtProduct_name.setText(product.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtProduct_price.setText(decimalFormat.format(product.getProduct_price())+"đ");

        Picasso.with(context)
                .load(product.getProduct_image())
                .placeholder(R.drawable.baseline_image_not_supported_24)
                .error(R.drawable.baseline_error_24)
                .into(holder.imageProduct);
        Log.d("url",product.getProduct_image());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageProduct;
        public TextView txtProduct_name,txtProduct_price;
        public ItemHolder(View itemview){
            super(itemview);
            imageProduct=itemview.findViewById(R.id.ImgVIewProduct);
            txtProduct_name=itemview.findViewById(R.id.TxtProductName);
            txtProduct_price=itemview.findViewById(R.id.txtProductPrice);

        }
    }
}
