package com.example.apphkdn.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.activity.CartActivity;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Cart> cartLists;
    CartAdapter cartAdapter;
    public static TextView dachon,totalMoney;

    Context context;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public CartAdapter(List<Cart> cartLists, Context context) {
        this.cartLists = cartLists;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cartList = cartLists.get(position);
        int i=position;
        new DownloadImageTask(holder.imgCart).execute(cartList.getProduct_image());
//        Picasso.get().load(cartList.getProduct_image()).into(holder.imgCart);
        holder.tvGia.setText(String.valueOf(decimalFormat.format(cartList.getProduct_price()))+"đ");
        holder.tvName.setText(cartList.getProduct_name());
        holder.solg.setText(String.valueOf(cartList.getProduct_pay()));
        if (cartList.getCheck().equals("1"))
            holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);
        int totalBill = 0,totalSP=0;
        for (int j=0;j<cartLists.size();j++){
            if (cartLists.get(j).getCheck().equals("1")){
                totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                totalSP++;
            }
        }

        CartActivity.totalMoney.setText("Tổng tiền: "+decimalFormat.format(totalBill)+"đ");
        CartActivity.dachon.setText("Đã chọn: "+totalSP);
        if (totalSP==cartLists.size())
            CartActivity.checkBoxTotal.setChecked(true);
        else CartActivity.checkBoxTotal.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    System.out.println(i+" "+" acb");
                    cartLists.get(i).setCheck("1");
                }else cartLists.get(i).setCheck("0");
                long totalBill = 0,totalSP=0;
                for (int j=0;j<cartLists.size();j++){
                    if (cartLists.get(j).getCheck().equals("1")){
                        totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                        totalSP++;
                    }
                }
                if (totalSP==cartLists.size())
                    CartActivity.checkBoxTotal.setChecked(true);
                else CartActivity.checkBoxTotal.setChecked(false);
                CartActivity.totalMoney.setText("Tổng tiền: "+decimalFormat.format(totalBill)+"đ");
                CartActivity.dachon.setText("Đã chọn: "+totalSP);
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cartLists.remove(i);
                                updateCart(cartLists);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartLists.get(i).setProduct_pay(cartLists.get(i).getProduct_pay()+1);
                holder.solg.setText(String.valueOf(cartList.getProduct_pay()));
                long totalBill = 0,totalSP=0;
                for (int j=0;j<cartLists.size();j++){
                    if (cartLists.get(j).getCheck().equals("1")){
                        totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                        totalSP++;
                    }
                }
                CartActivity.totalMoney.setText("Tổng tiền: "+decimalFormat.format(totalBill)+"đ");
                CartActivity.dachon.setText("Đã chọn: "+totalSP);
            }
        });
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartLists.get(i).getProduct_pay()>0){
                    cartLists.get(i).setProduct_pay(cartLists.get(i).getProduct_pay()-1);
                    holder.solg.setText(String.valueOf(cartList.getProduct_pay()));
                    int totalBill = 0,totalSP=0;
                    for (int j=0;j<cartLists.size();j++){
                        if (cartLists.get(j).getCheck().equals("1")){
                            totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                            totalSP++;
                        }
                    }
                    if (cartLists.get(i).getProduct_pay()==0){
                        cartLists.remove(i);
                    }
                    CartActivity.totalMoney.setText("Tổng tiền: "+decimalFormat.format(totalBill)+"đ");
                    CartActivity.dachon.setText("Đã chọn: "+totalSP);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return cartLists.size();
    }
    public void updateCart(List<Cart> cartLists){
        this.cartLists=cartLists;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCart,btnDel;
        TextView tvName,tvGia,solg;
        CheckBox checkBox,checkBoxTotal;
        CardView btn_add,btn_remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkboxCartt);
            imgCart=itemView.findViewById(R.id.imgCartt);
            tvGia=itemView.findViewById(R.id.giaCartt);
            tvName=itemView.findViewById(R.id.nameCartt);
            solg = itemView.findViewById(R.id.edt_slCartt);
            btn_add=itemView.findViewById(R.id.btn_plusCartt);
            btn_remove=itemView.findViewById(R.id.btn_removeCartt);
            btnDel=itemView.findViewById(R.id.btn_delCartt);
        }
    }
}
