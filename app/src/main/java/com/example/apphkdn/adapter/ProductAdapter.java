package com.example.apphkdn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.activity.Detail_product;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

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

        new DownloadImageTask(holder.imageProduct).execute(product.getProduct_image());

        holder.layoutproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Detail_product.class);
                intent.putExtra("ID_Product",product.getId());
                intent.putExtra("product_name",product.getProduct_name());
                intent.putExtra("product_image",product.getProduct_image());
                intent.putExtra("product_price",product.getProduct_price());
                intent.putExtra("ID_Category",product.getCategory_id());
                intent.putExtra("Product_decs",product.getProduct_decs());
                intent.putExtra("ID_Shop",product.getId_shop());
                intent.putExtra("product_review",product.getProduct_review());
                intent.putExtra("product_numbersell",product.getProduct_numbersell());
                intent.putExtra("product_selled",product.getProduct_selled());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }




    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageProduct;

        public LinearLayout layoutproduct;
        public TextView txtProduct_name,txtProduct_price;
        public ItemHolder(View itemview){
            super(itemview);
            layoutproduct=itemview.findViewById(R.id.layoutProduct);
            imageProduct=itemview.findViewById(R.id.ImgVIewProduct);
            txtProduct_name=itemview.findViewById(R.id.TxtProductName);
            txtProduct_price=itemview.findViewById(R.id.txtProductPrice);

        }
    }
}
