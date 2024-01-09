package com.example.apphkdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Rating;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ItemHolder> {
    Context context;
    ArrayList<Rating> ratingArrayList;

    public RatingAdapter(Context context, ArrayList<Rating> ratingArrayList) {
        this.context = context;
        this.ratingArrayList = ratingArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating_product,null);
        ItemHolder itemHolder=new RatingAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Rating rating= ratingArrayList.get(position);
        holder.productRatingBar_rcv.setRating(rating.getRating());
        holder.Text_Rating_pr.setText(rating.getComment());
        holder.text_user_ratiing.setText(rating.getUser_name());



    }

    @Override
    public int getItemCount() {
        return ratingArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        RatingBar productRatingBar_rcv;
        TextView txtRating_rcv,Text_Rating_pr,text_user_ratiing;
        public ItemHolder(View itemView){
            super(itemView);
            productRatingBar_rcv=itemView.findViewById(R.id.productRatingBar_rcv);
            txtRating_rcv=itemView.findViewById(R.id.txtRating_rcv);
            Text_Rating_pr=itemView.findViewById(R.id.Text_Rating_pr);
            text_user_ratiing=itemView.findViewById(R.id.text_user_ratiing);
        }
    }
}
