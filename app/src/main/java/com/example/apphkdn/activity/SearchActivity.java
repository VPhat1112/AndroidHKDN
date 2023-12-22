package com.example.apphkdn.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;

public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView atvSearchBox;

    ArrayAdapter<String> arrayAdapter;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);

        initUI();
        openSearchBox();
        Back();
        setAutoFillSearch();
        Search();
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

    // Set autotextview
    private void setAutoFillSearch(){
        String[] items = {"Asus 3","asus 1"};
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,items);
        atvSearchBox.setAdapter(arrayAdapter);

        atvSearchBox.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
            }
        });
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

    private void Search(){
        atvSearchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_M){
                    Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void initUI(){
        btnBack=findViewById(R.id.btn_search_back);
    }
}
