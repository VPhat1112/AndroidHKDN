package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
        cartAdapter = new CartAdapter(cartLists, CartActivity.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(CartActivity.this, 1, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        dachon.setText("Đã chọn: 0");
        totalMoney.setText("Tổng tiền: 0");
        back();
        checkBoxTotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
        checkBoxTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxTotal.isChecked()){
                    System.out.println("truee");
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

    }
    private void CheckkOut(){
        btn_DatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void back(){
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

        if (cartLists!=null){

        }else {
            cartLists= new ArrayList<>();
        }
    }

    private void CheckData(){
        if (cartLists.size()<=0){
            cartAdapter.notifyDataSetChanged();
            txtThongBao.setEnabled(true);
        }else{
            cartAdapter.notifyDataSetChanged();
            txtThongBao.setEnabled(false);
        }
    }

}
