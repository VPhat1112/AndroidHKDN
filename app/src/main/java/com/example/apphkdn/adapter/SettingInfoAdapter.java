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

// YourAdapter.java
public class SettingInfoAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] data;
    private int[] imageResources; // Add an array to hold image resources

    public SettingInfoAdapter(Context context, String[] data, int[] imageResources) {
        super(context, R.layout.item_listview_information, data);
        this.context = context;
        this.data = data;
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemView = inflater.inflate(R.layout.item_listview_information, parent, false);

        TextView textViewItem = listItemView.findViewById(R.id.Txtinfo);
        ImageView imageViewItem = listItemView.findViewById(R.id.imageinfo);

        textViewItem.setText(data[position]);
        imageViewItem.setImageResource(imageResources[position]);

        return listItemView;
    }
}
