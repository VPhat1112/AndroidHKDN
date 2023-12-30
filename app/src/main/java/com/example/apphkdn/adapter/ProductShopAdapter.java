package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.ItemHolder>{
    Context context;
    ArrayList<Product> productArrayListSelle;

    public ProductShopAdapter(Context context, ArrayList<Product> productArrayListSelle) {
        this.context = context;
        this.productArrayListSelle = productArrayListSelle;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product_shop,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product= productArrayListSelle.get(position);
        holder.txtProduct_nameShop.setText(product.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtProduct_priceShop.setText(decimalFormat.format(product.getProduct_price())+"đ");

        new DownloadImageTask(holder.imageProductShop).execute(product.getProduct_image());

        holder.layoutproductShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayListSelle.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageProductShop;

        public LinearLayout layoutproductShop;
        public TextView txtProduct_nameShop,txtProduct_priceShop;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductShop=itemView.findViewById(R.id.layoutProductShop);
            imageProductShop=itemView.findViewById(R.id.ImgVIewProductShop);
            txtProduct_nameShop=itemView.findViewById(R.id.TxtProductNameShop);
            txtProduct_priceShop=itemView.findViewById(R.id.txtProductPriceShop);
        }
    }
}
