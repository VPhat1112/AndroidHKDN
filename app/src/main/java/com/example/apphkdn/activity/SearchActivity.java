package com.example.apphkdn.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;

public class SearchActivity extends AppCompatActivity {

    EditText searchBox;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);

        initUI();
        openSearchBox();
    }

    private void openSearchBox(){
        searchBox.requestFocus();
        showKeyboard();
    }
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    private void initUI(){
        searchBox=findViewById(R.id.edt_search);
    }
}
