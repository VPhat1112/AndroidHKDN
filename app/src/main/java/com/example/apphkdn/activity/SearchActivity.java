package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearchBox;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);

        initUI();
        openSearchBox();
        Back();
        Search();
    }

    // Focus on search box when start activity
    private void openSearchBox(){
        edtSearchBox.requestFocus();
        showKeyboard();
    }
    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    // Return to homepage
    private void Back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Push search key to ProductSearchActivity
    private void Search() {

    }
    private void initUI(){
        edtSearchBox=findViewById(R.id.edt_search);
        btnBack=findViewById(R.id.btn_search_back);
    }
}
