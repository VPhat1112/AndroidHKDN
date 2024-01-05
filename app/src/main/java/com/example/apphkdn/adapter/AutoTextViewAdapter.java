package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apphkdn.R;
import com.example.apphkdn.model.AutoTextViewItems;

import java.util.List;

public class AutoTextViewAdapter extends ArrayAdapter<AutoTextViewItems> {
    public AutoTextViewAdapter(@NonNull Context context, int resource, @NonNull List<AutoTextViewItems> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.autotexview_row_items, parent, false);
        }

        ImageView imgShop = convertView.findViewById(R.id.autotv_items_imgShop);
        TextView tvShopName = convertView.findViewById(R.id.autotv_items_nameShop);

        AutoTextViewItems autoTextViewItems = getItem(position);

        return super.getView(position, convertView, parent);
    }


}
