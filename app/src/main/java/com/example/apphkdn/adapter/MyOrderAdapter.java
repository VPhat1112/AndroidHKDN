package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.GetInforProductFirst;
import static com.example.apphkdn.ultil.Server.serverAddress;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.ShopOrderDetailBuyerActivity;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ItemHolder> {
    Context context;
    ArrayList<Order> orderArrayList=new ArrayList<>();

    RequestDB requestDB= new RequestDB();

    String quanity,Product_TotalPay,product_name,product_image;

    public MyOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_myorder,null);
        ItemHolder itemHolder=new MyOrderAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Order order= orderArrayList.get(position);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GetInforProductFirst+String.valueOf(order.getIdOrder()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jsonObject.getString("quantity");
                        jsonObject.getString("Product_TotalPay");
                        jsonObject.getString("product_name");
                        jsonObject.getString("product_image");

                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        holder.TxtProductNameMyOrder.setText(jsonObject.getString("product_name"));
                        holder.SL_MyOrder.setText("SL: "+jsonObject.getString("quantity"));
                        holder.Price_MyOrder.setText(decimalFormat.format(Integer.parseInt(jsonObject.getString("Product_TotalPay")))+"đ");
                        new DownloadImageTask(holder.ImgVIewProductMyOrder).execute(serverAddress+jsonObject.getString("product_image"));
                        Log.d("kiem tra", jsonObject.toString());
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Parsing Json", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_total_item_myorder.setText(decimalFormat.format(order.getFinalTotal())+"đ");
        holder.tv_item_myorder_status.setText(order.getNameShop());
        new DownloadImageTask(holder.Image_shop).execute(order.getImgShop());

        holder.layoutproductMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopOrderDetailBuyerActivity.class);
                intent.putExtra("id",String.valueOf(order.getIdOrder()));
                intent.putExtra("Shop_id",String.valueOf(order.getIdShop()));
                intent.putExtra("CreatedAt",order.getCreateAt());
                intent.putExtra("status",String.valueOf(order.getStatusOrder()));
                intent.putExtra("SDT",order.getPhone());
                intent.putExtra("Address_ship",order.getAddress());
                intent.putExtra("shopName",order.getNameShop());
                intent.putExtra("shop_image",order.getImgShop());
                intent.putExtra("Product_price",String.valueOf(order.getFinalTotal()));
                intent.putExtra("user_name",order.getUsername());
                context.startActivity(intent);
            }
        });

        if (order.getStatusOrder()==0){
            holder.write_status.setText("Đang chờ duyệt");
            holder.btn_buy_back_item_myorder.setVisibility(View.GONE);
        } else if (order.getStatusOrder()==1) {
            holder.write_status.setText("Đang Giao");
            holder.btn_buy_back_item_myorder.setVisibility(View.GONE);
        } else if (order.getStatusOrder()==2) {
            holder.write_status.setText("Đã hoàn thành");
        } else if (order.getStatusOrder()==3) {
            holder.write_status.setText("Đơn hàng đã hủy");
        }
    }
    private void GetandSetDataProductFirstOrder(String url){

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView ImgVIewProductMyOrder,Image_shop;
        public RelativeLayout layoutproductMyOrder;
        public AppCompatButton btn_buy_back_item_myorder;
        public TextView TxtProductNameMyOrder,Price_MyOrder,SL_MyOrder,txtStatus,write_status,txt_total_item_myorder,tv_item_myorder_status;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductMyOrder=itemView.findViewById(R.id.layoutMyOrder);
            ImgVIewProductMyOrder=itemView.findViewById(R.id.ImgVIewProductMyOrder);
            TxtProductNameMyOrder=itemView.findViewById(R.id.TxtProductNameMyOrder);
            Price_MyOrder=itemView.findViewById(R.id.Price_MyOrder);
            SL_MyOrder=itemView.findViewById(R.id.SL_MyOrder);
            txtStatus=itemView.findViewById(R.id.tv_item_myorder_status);
            btn_buy_back_item_myorder=itemView.findViewById(R.id.btn_buy_back_item_myorder);
            write_status=itemView.findViewById(R.id.write_status);
            txt_total_item_myorder=itemView.findViewById(R.id.txt_total_item_myorder);
            Image_shop=itemView.findViewById(R.id.Image_shop);
            tv_item_myorder_status=itemView.findViewById(R.id.tv_item_myorder_status);
        }
    }
}
