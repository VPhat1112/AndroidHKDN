package com.example.apphkdn.adapter;

import static com.example.apphkdn.ultil.Server.LockProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.apphkdn.activity.Activity_Seller_Update_Product;
import com.example.apphkdn.activity.Detail_product;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.ItemHolder>{
    Context context;
    ArrayList<Product> productArrayListSelle;

    public ProductShopAdapter(Context context, ArrayList<Product> productArrayListSelle) {
        this.context = context;
        this.productArrayListSelle = productArrayListSelle;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product_shop,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product= productArrayListSelle.get(position);
        if (product.getStatus()==0){
            holder.lockview.setVisibility(View.VISIBLE);
        }else if (product.getStatus()==1){
            holder.lockview.setVisibility(View.INVISIBLE);
        }
        holder.txtProduct_nameShop.setText(product.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtProduct_priceShop.setText(decimalFormat.format(product.getProduct_price())+"đ");

        new DownloadImageTask(holder.imageProductShop).execute(product.getProduct_image());

        holder.layoutproductShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Detail_product.class);
                intent.putExtra("ID_Product",product.getId());
                intent.putExtra("product_name",product.getProduct_name());
                intent.putExtra("product_image",product.getProduct_image());
                intent.putExtra("product_price",product.getProduct_price());
                intent.putExtra("ID_Category",product.getCategory_id());
                intent.putExtra("Product_decs",product.getProduct_decs());
                intent.putExtra("ID_Shop",product.getId_shop());
                intent.putExtra("product_review",product.getProduct_review());
                intent.putExtra("product_numbersell",product.getProduct_numbersell());
                intent.putExtra("product_selled",product.getProduct_selled());
                intent.putExtra("status",product.getStatus());
                context.startActivity(intent);
            }
        });
        holder.layoutproductShop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String id_product = String.valueOf(product.getId());
                String mess="";
                if (product.getStatus()==0) {
                     mess = "Bạn có chắc muốn mở khóa sản phẩm!!!";
                } else if (product.getStatus()==1) {
                     mess = "Bạn có chắc muốn khóa sản phẩm!!!";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Lock Product");
                builder.setMessage(mess);

                // Adding a positive button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click if needed
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = LockProduct;

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("success")){
                                            String remess= "Đã Khóa sản phẩm thành công!";
                                            RequestDB.showInvalidOtpDialogLockProduct(context,remess);
                                        }else if (response.equals("failed")){
                                            String remess= "Khóa sản phẩm thất bại!";
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
                                Map<String, String> params = new HashMap<>();
                                params.put("id",id_product);
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
                return false;

            }
        });
        holder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Activity_Seller_Update_Product.class);
                intent.putExtra("ID_Product",product.getId());
                intent.putExtra("product_name",product.getProduct_name());
                intent.putExtra("product_image",product.getProduct_image());
                intent.putExtra("product_price",product.getProduct_price());
                intent.putExtra("ID_Category",product.getCategory_id());
                intent.putExtra("Product_decs",product.getProduct_decs());
                intent.putExtra("ID_Shop",product.getId_shop());
                intent.putExtra("product_review",product.getProduct_review());
                intent.putExtra("product_numbersell",product.getProduct_numbersell());
                intent.putExtra("product_selled",product.getProduct_selled());
                intent.putExtra("status",product.getStatus());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productArrayListSelle.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imageProductShop;
        ImageButton EditBtn;
        ImageView lockview;

        public LinearLayout layoutproductShop;
        public TextView txtProduct_nameShop,txtProduct_priceShop;
        public ItemHolder(View itemView) {
            super(itemView);
            layoutproductShop=itemView.findViewById(R.id.layoutProductShop);
            imageProductShop=itemView.findViewById(R.id.ImgVIewProductShop);
            txtProduct_nameShop=itemView.findViewById(R.id.TxtProductNameShop);
            txtProduct_priceShop=itemView.findViewById(R.id.txtProductPriceShop);
            EditBtn=itemView.findViewById(R.id.edit_Btn_product);
            lockview=itemView.findViewById(R.id.lockview);
        }
    }
}
