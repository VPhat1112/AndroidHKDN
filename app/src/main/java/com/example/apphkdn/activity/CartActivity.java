package com.example.apphkdn.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.adapter.CartAdapter;
import com.example.apphkdn.model.Cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    static CartAdapter cartAdapter;
    static ArrayList<Cart> cartLists= new ArrayList<>();
    RecyclerView recyclerView;
    public static TextView dachon,totalMoney,txtThongBao;
    public static CheckBox checkBoxTotal;
    public static String mobile;
    Button btn_DatHang;
    String m_Text;
    String diaChi;
    Intent intentMain;
    String daban,soluongban;
    ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        UnitUI();
//        CheckData();
        cartAdapter = new CartAdapter(cartLists, CartActivity.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(CartActivity.this, 1, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        dachon.setText("Đã chọn: 0");
        totalMoney.setText("Tổng tiền: 0");
        EventAction();


    }
    private void EventAction(){
        checkBoxTotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
        checkBoxTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxTotal.isChecked()){
                    for (int i=0;i<cartLists.size();i++){
                        cartLists.get(i).setCheck("1");
                    }
                }else{
                    System.out.println("false");
                    for (int i=0;i<cartLists.size();i++){
                        cartLists.get(i).setCheck("0");
                    }
                    dachon.setText("Đã chọn: 0");
                    totalMoney.setText("Tổng tiền: 0");
                }
                cartAdapter.updateCart(cartLists);
            }
        });
        btn_DatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CountCheck=0;
                for (int i=0;i<cartLists.size();i++){
                    if (cartLists.get(i).getCheck().equals("1")){
                        CountCheck++;
                    }
                }
                if (CountCheck==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Đặt hàng");
                    builder.setMessage("Bạn vẫn chưa chọn sản phẩm để đặt hàng!!!");

                    // Adding a positive button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle the "OK" button click if needed
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    // Must call show() prior to fetching text view
                    TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
                    messageView.setGravity(Gravity.CENTER);

                    TextView titleView = (TextView)alertDialog.findViewById(getResources().getIdentifier("alertTitle", "id", "android"));
                    if (titleView != null) {
                        titleView.setGravity(Gravity.CENTER);
                    }
                }else startActivity(new Intent(CartActivity.this,CheckOutActivity.class));

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void UnitUI(){
        recyclerView = findViewById(R.id.rvCart);
        btn_DatHang=findViewById(R.id.btn_DatHang);
        dachon = findViewById(R.id.dachon);
        totalMoney=findViewById(R.id.totalMoney);
        checkBoxTotal=findViewById(R.id.checkboxTotal);
        btn_back=findViewById(R.id.btn_backMain);
//        txtThongBao=findViewById(R.id.Thongbaos);
        if (cartLists!=null){

        }else {
            cartLists= new ArrayList<>();
        }
    }

    private void CheckData(){
        if (cartLists.size()>0){
            cartAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.GONE);
        }
    }

}
