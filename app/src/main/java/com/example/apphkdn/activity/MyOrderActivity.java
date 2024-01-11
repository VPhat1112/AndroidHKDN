package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetAllMyOrderApp;
import static com.example.apphkdn.ultil.Server.GetMyOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.MyOrderAdapter;
import com.example.apphkdn.model.Order;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {
    AppCompatButton WaitMyorder,ApproveMyorder,CancelMyorder,CompleteMyorder,AllMyorder;
    ImageButton Comfirm_myorder,Home_myorder;
    RecyclerView rcv_Myorder;
    ArrayList<Order> orderArrayList;
    MyOrderAdapter myOrderAdapter;
    int id_user = DataLocalManager.getIdUser();
    RequestDB requestDB = new RequestDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        UnitUI();
        setDataAllMyOrder();
        AllMyorder.setBackgroundColor(getResources().getColor(R.color.red));
        EventAction();
    }

    private void EventAction(){
        AllMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllMyorder.setBackgroundColor(getResources().getColor(R.color.red));
                WaitMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                ApproveMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CancelMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CompleteMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                setDataAllMyOrder();
            }
        });
        WaitMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitMyorder.setBackgroundColor(getResources().getColor(R.color.red));
                AllMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                ApproveMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CancelMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CompleteMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                setDataMyWaitOrder();
            }
        });
        ApproveMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveMyorder.setBackgroundColor(getResources().getColor(R.color.red));
                AllMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                WaitMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CancelMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CompleteMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                setDataMyAppOrder();
            }
        });
        CancelMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelMyorder.setBackgroundColor(getResources().getColor(R.color.red));
                AllMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                WaitMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                ApproveMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CompleteMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                setDataMyCancelOrder();
            }
        });
        CompleteMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteMyorder.setBackgroundColor(getResources().getColor(R.color.red));
                AllMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                WaitMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                ApproveMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                CancelMyorder.setBackgroundColor(getResources().getColor(R.color.whitee));
                setDataMyCompleteOrder();
            }
        });
        Comfirm_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Home_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyOrderActivity.this,MainActivity.class));
            }
        });
    }

    private void setDataMyWaitOrder(){
        int status=0;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }
    private void setDataMyAppOrder(){
        int status=1;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }
    private void setDataMyCancelOrder(){
        int status=3;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }

    private void setDataMyCompleteOrder(){
        int status=2;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }
    private void setDataAllMyOrder(){
        int status=3;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetAllMyOrderApp+id_user);
    }

    private void UnitUI(){
        AllMyorder=findViewById(R.id.AllMyorder);
        WaitMyorder=findViewById(R.id.WaitMyorder);
        ApproveMyorder=findViewById(R.id.ApproveMyorder);
        CancelMyorder=findViewById(R.id.CancelMyorder);
        CompleteMyorder=findViewById(R.id.CompleteMyorder);
        rcv_Myorder=findViewById(R.id.rcv_Myorder);
        Comfirm_myorder=findViewById(R.id.Comfirm_myorder);
        Home_myorder=findViewById(R.id.Home_myorder);
    }
}