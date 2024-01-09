package com.example.apphkdn.activity;

import static com.example.apphkdn.activity.CartActivity.cartLists;
import static com.example.apphkdn.ultil.Server.SaveOrder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductOrderAdapter;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.ultil.ChoiceWayPayDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements ChoiceWayPayDialog.ChoiceWayPayDialogListener {
    RecyclerView rcv_Order;
    TextView txtTen,txtSDT,txtMoneyFast,txtMoneyShip,txtMoneyTotal,CheckMoneyTotal,Update_Address_order,Comfirm_order,txtSeall,txtWayPay;
    Button btn_Order_complete;
    ProductOrderAdapter productOrderAdapter;
    List<Cart> filteredCartList = new ArrayList<>();
    int totalBill = 0,totalSP=0;
    RequestDB requestDB= new RequestDB();
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        UnitUI();
        EventAction();
    }
    private void EventAction(){
        Comfirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Update_Address_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(CheckOutActivity.this,R.style.Theme_AppHKDN);

                final  View customLayout= getLayoutInflater().inflate(R.layout.dialog_address_phone,null);
                builder.setView(customLayout);
                builder.setTitle("Thay đổi số điện thoại và địa chỉ");

                builder.setPositiveButton("xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText phone = customLayout.findViewById(R.id.edt_SDT_checkout);
                        EditText Address= customLayout.findViewById(R.id.edt_Address_checkout);
                        EditText mess=customLayout.findViewById(R.id.txtdialog_thongbao);
                        if (phone.getText().toString()==""&&Address.getText().toString()==""){
                            String messs= "Không thể bỏ trống cả 2 thông tin" ;
                            mess.setText(messs);
                        }else if(phone.getText().toString()==""){
                            Update_Address_order.setText(Address.getText().toString());
                        }else if(Address.getText().toString()==""){
                            txtSDT.setText(phone.getText().toString());
                        }else{
                            Update_Address_order.setText(Address.getText().toString());
                            txtSDT.setText(phone.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });
        btn_Order_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Integer id_shop=0,id_User=0,product_id=0,numberProduct=0,prot_price=0,totalofPrice=0;
                String Address_ship=Update_Address_order.getText().toString();
                String Phone=txtSDT.getText().toString();
                for (int i=0;i<filteredCartList.size();i++){
                     id_shop=filteredCartList.get(i).getShop_id();
                     id_User=DataLocalManager.getIdUser();
                     product_id=filteredCartList.get(i).getProduct_id();
                     numberProduct=filteredCartList.get(i).getProduct_pay();
                     prot_price=filteredCartList.get(i).getProduct_price();
                     totalofPrice=prot_price*numberProduct*5/100;

                    requestDB.InsertOrder(CheckOutActivity.this,totalofPrice,id_shop,id_User,product_id,numberProduct,prot_price,totalofPrice,Address_ship,Phone,SaveOrder);
                }

            }
        });
        setData();
        setWayPay();
    }
    private void setWayPay(){
        txtSeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment WaypayChoice=new ChoiceWayPayDialog();
                WaypayChoice.setCancelable(false);
                WaypayChoice.show(getSupportFragmentManager(),"WayPay Choice");
            }
        });
    }
    private void setData(){
        for (int j=0;j<cartLists.size();j++){
            if (cartLists.get(j).getCheck().equals("1")){
                filteredCartList.add(cartLists.get(j));
            }
        }

        productOrderAdapter = new ProductOrderAdapter(CheckOutActivity.this, (ArrayList<Cart>) filteredCartList);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(CheckOutActivity.this, 1, RecyclerView.VERTICAL, false);
        rcv_Order.setAdapter(productOrderAdapter);
        rcv_Order.setLayoutManager(linearLayoutManager);
        txtTen.setText(DataLocalManager.getNameUser()+" | ");
        txtSDT.setText(DataLocalManager.getPhoneUser());
        totalMoney();
        txtMoneyFast.setText(decimalFormat.format(totalBill)+"đ");
        txtMoneyShip.setText("42,000 đ");
        txtMoneyTotal.setText(decimalFormat.format(totalBill+(totalBill*5/100))+"đ");

        CheckMoneyTotal.setText(decimalFormat.format(totalBill+(totalBill*5/100))+"đ");
        Update_Address_order.setText(DataLocalManager.getAddressUser());
    }
    private void totalMoney(){
        for (int j=0;j<cartLists.size();j++){
            if (cartLists.get(j).getCheck().equals("1")){
                totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                totalSP++;
            }
        }
    }
    private void UnitUI(){
        rcv_Order=findViewById(R.id.rcv_Order);
        txtTen=findViewById(R.id.txtTen);
        txtMoneyFast=findViewById(R.id.txtMoneyFast);
        txtMoneyShip=findViewById(R.id.txtMoneyShip);
        txtMoneyTotal=findViewById(R.id.txtMoneyTotal);
        CheckMoneyTotal=findViewById(R.id.CheckMoneyTotal);
        Update_Address_order=findViewById(R.id.Update_Address_order);
        Comfirm_order=findViewById(R.id.Comfirm_order);
        btn_Order_complete=findViewById(R.id.btn_Order_complete);
        txtSeall=findViewById(R.id.txtSeall);
        txtWayPay=findViewById(R.id.txtWayPay);

        txtSDT=findViewById(R.id.txtSDT);
    }

    @Override
    public void onPotisitisionClicked(String[] list, int position) {
        txtWayPay.setText(list[position]);
    }
}