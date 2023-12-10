package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemHolder> {
    Context context;
    ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_category,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Category category= categoryArrayList.get(position);
        holder.txtCategory_name.setText(category.getCategory_name());
        new DownloadImageTask(holder.imageCategory).execute(category.getCategory_image());
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageCategory;
        public TextView txtCategory_name;
        public ItemHolder(View itemview){
            super(itemview);
            imageCategory=itemview.findViewById(R.id.imageViewcategory);
            txtCategory_name=itemview.findViewById(R.id.TxtCategory);
        }
    }
}
