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
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductDetailApdapter extends RecyclerView.Adapter<ProductDetailApdapter.ItemHolder> {
    Context context;
    ArrayList<Product> productArrayList;

    public ProductDetailApdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail_product,null);
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
        EventButton();
    }

    private void EventButton() {

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageProduct;

        public RelativeLayout layoutproduct;
        public TextView txtProduct_name,txtProduct_price;
        public ItemHolder(View itemview){
            super(itemview);
            layoutproduct=itemview.findViewById(R.id.layoutProduct);
            imageProduct=itemview.findViewById(R.id.viewFlipperDT);
            txtProduct_name=itemview.findViewById(R.id.nameProduct);
            txtProduct_price=itemview.findViewById(R.id.Product_priceDT);

        }
    }
}
