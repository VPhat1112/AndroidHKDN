package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.AcceptOrder;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.model.Order;
import com.example.apphkdn.ultil.DownloadImageTask;

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
        holder.TxtProductNameShopOrder.setText(order.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Price_Order.setText(decimalFormat.format(order.getFinalTotal())+"đ");
        new DownloadImageTask(holder.ImgVIewProductShopOrder).execute(order.getProduct_image());
        holder.SL_order.setText("SL: "+order.getNumber_pay());
        int status=order.getOrder_status();
        if (status==0){
            holder.edit_accept_product.setText("Nhận đơn");
        } else if (status==1) {
            holder.edit_accept_product.setText("Hoàn thành đơn");
        }else if (status==2||status==3){
            holder.edit_accept_product.setVisibility(View.GONE);
            holder.edit_deni_product.setVisibility(View.GONE);
        }

        holder.edit_accept_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order_id = String.valueOf(order.getOrder_id());
                String mess="";
                int request=0;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (status==0){
                    mess = "Bạn có muốn nhận đơn hàng!!!";
                    builder.setTitle("Accept Order");
                    builder.setMessage(mess);
                    request=1;
                } else if (status==1) {
                    mess = "Bạn có muốn xác nhận đơn hàng đã hoàn thành!!!";
                    builder.setTitle("Complete Order");
                    builder.setMessage(mess);
                    request=2;
                }
                String CheckRQ=String.valueOf(request);


                // Adding a positive button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click if needed
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = AcceptOrder;

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Tasas",response);
                                        if (response.equals("success")){
                                            String remess= "Đã nhận sản phẩm thành công!";
                                            RequestDB.showInvalidOtpDialogAcceptOrder(context,remess);
                                        }else if (response.equals("failed")){
                                            String remess= "Nhận sản phẩm thất bại!";
                                            RequestDB.showInvalidOtpDialogERROR(context,remess);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                            protected Map<String, String> getParams(){
                                Map<String,String> params = new HashMap<>();
                                params.put("order_id",order_id);
                                params.put("request",CheckRQ);
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // Must call show() prior to fetching text view
                TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);

                TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
                if (titleView != null) {
                    titleView.setGravity(Gravity.CENTER);
                }
//                return false;

            }
        });
        holder.edit_deni_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order_id = String.valueOf(order.getOrder_id());
                String mess="";
                int request=3;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                mess = "Bạn có chắc chắn muốn hủy đơn hàng!!!";
                builder.setTitle("Deni Order");
                builder.setMessage(mess);

                String CheckRQ=String.valueOf(request);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click if needed
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = AcceptOrder;

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Tasas",response);
                                        if (response.equals("success")){
                                            String remess= "Đã nhận sản phẩm thành công!";
                                            RequestDB.showInvalidOtpDialogAcceptOrder(context,remess);
                                        }else if (response.equals("failed")){
                                            String remess= "Nhận sản phẩm thất bại!";
                                            RequestDB.showInvalidOtpDialogERROR(context,remess);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                            protected Map<String, String> getParams(){
                                Map<String,String> params = new HashMap<>();
                                params.put("order_id",order_id);
                                params.put("request",CheckRQ);
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // Must call show() prior to fetching text view
                TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);

                TextView titleView = (TextView)alertDialog.findViewById(context.getResources().getIdentifier("alertTitle", "id", "android"));
                if (titleView != null) {
                    titleView.setGravity(Gravity.CENTER);
                }
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
        public TextView TxtProductNameShopOrder,Price_Order,SL_order,edit_accept_product,edit_deni_product;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductShop=itemView.findViewById(R.id.layoutOrderShop);
            ImgVIewProductShopOrder=itemView.findViewById(R.id.ImgVIewProductShopOrder);
            TxtProductNameShopOrder=itemView.findViewById(R.id.TxtProductNameShopOrder);
            Price_Order=itemView.findViewById(R.id.Price_Order);SL_order=itemView.findViewById(R.id.SL_order);
            edit_accept_product=itemView.findViewById(R.id.edit_accept_product);
            edit_deni_product=itemView.findViewById(R.id.edit_deni_product);
        }
    }
}
