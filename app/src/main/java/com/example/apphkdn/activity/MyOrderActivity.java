package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetMyOrder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.MyOrderAdapter;
import com.example.apphkdn.model.Order;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {
    Button WaitMyorder,ApproveMyorder,CancelMyorder,CompleteMyorder;
    TextView Comfirm_myorder;
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
        setDataMyWaitOrder();
        EventAction();
    }

    private void EventAction(){
        WaitMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataMyWaitOrder();
            }
        });
        ApproveMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataMyAppOrder();
            }
        });
        Comfirm_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CancelMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataMyCancelOrder();
            }
        });
        CompleteMyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataMyCompleteOrder();
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
        int status=2;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }

    private void setDataMyCompleteOrder(){
        int status=3;
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,orderArrayList);
        rcv_Myorder.setHasFixedSize(true);
        rcv_Myorder.setLayoutManager(new GridLayoutManager(MyOrderActivity.this,1));
        rcv_Myorder.setAdapter(myOrderAdapter);

        requestDB.GetMyOrder(MyOrderActivity.this,orderArrayList,myOrderAdapter,GetMyOrder+id_user+"&status="+status);
    }

    private void UnitUI(){

        WaitMyorder=findViewById(R.id.WaitMyorder);
        ApproveMyorder=findViewById(R.id.ApproveMyorder);
        CancelMyorder=findViewById(R.id.CancelMyorder);
        CompleteMyorder=findViewById(R.id.CompleteMyorder);
        rcv_Myorder=findViewById(R.id.rcv_Myorder);
        Comfirm_myorder=findViewById(R.id.Comfirm_myorder);
    }
}