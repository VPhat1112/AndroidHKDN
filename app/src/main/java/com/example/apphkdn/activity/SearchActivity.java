package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.LinkDataAutotextViewSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String name;
    AutoCompleteTextView atvSearchBox;
    Button btnBack, btnFilter;
    AppCompatButton btnReset, btnApply;
    CheckBox ckb_price_300, ckb_price_300_1tr, ckb_price_1tr_1tr5, ckb_price_1tr5;
    EditText edtPriceFrom, edtPriceTo;
    NavigationView nav;
    DrawerLayout drawerLayout;
    RequestDB requestDB = new RequestDB();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);

        initUI();
        openSearchBox();
        setAutoFillSearch();
        Search();
        onClickitem();
        onClick();
        onCheckedChange();
    }

    private void onClick(){
        btnBack.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
    }

    private void onCheckedChange(){
    }

    // Focus on search box when start activity
    private void openSearchBox(){
        atvSearchBox.requestFocus();
        showKeyboard();
    }
    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    private void closeKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
    }

    // Set autotextview
    private void setAutoFillSearch(){
        requestDB.setDataAutotextViewSearch(SearchActivity.this, atvSearchBox, LinkDataAutotextViewSearch);
        atvSearchBox.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                closeKeyBoard();
            }
        });
    }

    private void onClickitem(){
        atvSearchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name = parent.getItemAtPosition(position).toString().trim();
                Intent intent = new Intent(SearchActivity.this, ShowProductBySearchActivity.class);
                intent.putExtra("keySearch",name);
                startActivity(intent);
            }
        });
    }

    private void Search(){
        atvSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    Intent intent = new Intent(SearchActivity.this, ShowProductBySearchActivity.class);
                    intent.putExtra("keySearch",atvSearchBox.getText().toString().trim());
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void initUI(){
        btnBack=findViewById(R.id.btn_search_back);
        atvSearchBox=findViewById(R.id.edt_search);
        btnFilter=findViewById(R.id.btn_search_filter);
        nav=findViewById(R.id.nav_filter);
        drawerLayout=findViewById(R.id.drawer_search);
        getHeaderNav();
    }

    private void getHeaderNav(){
        View mView = nav.inflateHeaderView(R.layout.header_nav);
        ckb_price_300=mView.findViewById(R.id.ckb_price_under_300k);
        ckb_price_300_1tr=mView.findViewById(R.id.ckb_price_1000k_to_300k);
        ckb_price_1tr_1tr5=mView.findViewById(R.id.ckb_price_1500k_to_1000k);
        ckb_price_1tr5=mView.findViewById(R.id.ckb_price_above_1500k);
        edtPriceFrom=mView.findViewById(R.id.edt_nav_price_from);
        edtPriceTo=mView.findViewById(R.id.edt_nav_price_to);
        btnReset=mView.findViewById(R.id.btn_nav_reset);
        btnApply=mView.findViewById(R.id.btn_nav_apply);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search_filter){
            closeKeyBoard();
            drawerLayout.openDrawer(GravityCompat.END);
        } else if (v.getId() == R.id.btn_search_back) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }
}
