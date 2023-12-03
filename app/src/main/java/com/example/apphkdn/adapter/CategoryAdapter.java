package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> categoryArrayList;
    Context context;

    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context context) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView txtcategory;
        ImageView imgcategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_category, null);
            viewHolder.txtcategory = convertView.findViewById(R.id.TxtCategory);
            viewHolder.imgcategory = convertView.findViewById(R.id.ImgCategory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        Category category = (Category) getItem(position);

        // Check if the category is not null (added null check)
        if (category != null) {
            // Set data to the views in the ViewHolder
            viewHolder.txtcategory.setText(category.category_name);

            // Assuming you have an imageUrl property in your Category class
            String imageUrl = category.category_image; // replace with the actual property

            // Load the image using Picasso with placeholder and error images
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_image_not_supported_24)
                    .error(R.drawable.baseline_error_24)
                    .into(viewHolder.imgcategory);
        }
        return convertView;
    }
}