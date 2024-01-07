package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apphkdn.R;
import com.example.apphkdn.model.AutoTextViewItems;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AutoTextViewAdapter extends ArrayAdapter<AutoTextViewItems> {

    public AutoTextViewAdapter(@NonNull Context context, int resource, @NonNull List<AutoTextViewItems> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_autotv_row, parent, false);
        }

        ImageView imgShop = convertView.findViewById(R.id.autotv_items_imgShop);
        TextView tvShopName = convertView.findViewById(R.id.autotv_items_nameShop);
        TextView tvIdName = convertView.findViewById(R.id.autotv_items_idShop);

        AutoTextViewItems autoTextViewItems = getItem(position);

        new DownloadImageTask(imgShop).execute(autoTextViewItems.getImg());
        tvShopName.setText(autoTextViewItems.getName());
        tvIdName.setText(autoTextViewItems.getId());

        return convertView;
    }
}
