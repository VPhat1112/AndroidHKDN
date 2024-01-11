package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.AcceptOrder;
import static com.example.apphkdn.ultil.Server.GetInforProductFirst;
import static com.example.apphkdn.ultil.Server.serverAddress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.example.apphkdn.activity.SearchActivity;
import com.example.apphkdn.activity.ShopOrderDetailBuyerActivity;
import com.example.apphkdn.activity.ShopOrderDetailSellerActivity;
import com.example.apphkdn.model.AutoTextViewItems;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ItemHolder> {
    Context context;
    ArrayList<Order> orderArrayList;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_rcv,null);
        ItemHolder itemHolder=new OrderAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ItemHolder holder, int position) {
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
                        holder.TxtProductNameShopOrder.setText(jsonObject.getString("product_name"));
                        holder.SL_order.setText("SL: "+jsonObject.getString("quantity"));
                        holder.Price_Order.setText(decimalFormat.format(Integer.parseInt(jsonObject.getString("Product_TotalPay")))+"đ");
                        new DownloadImageTask(holder.ImgVIewProductShopOrder).execute(serverAddress+jsonObject.getString("product_image"));
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

        holder.layoutproductShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopOrderDetailSellerActivity.class);
                intent.putExtra("ids",String.valueOf(order.getIdOrder()));
                intent.putExtra("Shop_ids",String.valueOf(order.getIdShop()));
                intent.putExtra("CreatedAts",order.getCreateAt());
                intent.putExtra("statuss",String.valueOf(order.getStatusOrder()));
                intent.putExtra("SDTs",order.getPhone());
                intent.putExtra("Address_ships",order.getAddress());
                intent.putExtra("shopNames",order.getNameShop());
                intent.putExtra("shop_images",order.getImgShop());
                intent.putExtra("Product_prices",String.valueOf(order.getFinalTotal()));
                intent.putExtra("user_names",order.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView ImgVIewProductShopOrder;
        public RelativeLayout layoutproductShop;
        public TextView TxtProductNameShopOrder,Price_Order,SL_order;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductShop=itemView.findViewById(R.id.layoutOrderShop);
            ImgVIewProductShopOrder=itemView.findViewById(R.id.ImgVIewProductShopOrder);
            TxtProductNameShopOrder=itemView.findViewById(R.id.TxtProductNameShopOrder);
            Price_Order=itemView.findViewById(R.id.Price_Order);SL_order=itemView.findViewById(R.id.SL_order);
        }
    }
}
