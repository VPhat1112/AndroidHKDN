package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Category;

import java.util.ArrayList;

public class CategoryAdapterSpAdd  extends ArrayAdapter<Category> {
    public CategoryAdapterSpAdd(@NonNull Context context, int resource, @NonNull ArrayList<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_category_add, parent, false);
        TextView tvSelectedName = convertView.findViewById(R.id.tv_selected_spinner_category_name_add);
        TextView tvSelectedId = convertView.findViewById(R.id.tv_selected_spinner_category_id_add);

        Category category = this.getItem(position);
        if (category != null){
            tvSelectedName.setText(category.getCategory_name());
            //tvSelectedId.setText(category.getId());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_category_add, parent, false);
        TextView tvCategoryname = convertView.findViewById(R.id.tv_spinner_category_name_add);
        TextView tvCategoryID = convertView.findViewById(R.id.tv_spinner_category_id_add);

        Category category = this.getItem(position);
        if (category != null){
            tvCategoryname.setText(category.getCategory_name());
            Integer id = category.getId();
            tvCategoryID.setText(String.valueOf(id));
        }
        return convertView;
    }
}
