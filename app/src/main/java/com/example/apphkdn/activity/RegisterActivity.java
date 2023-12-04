package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;
import com.example.apphkdn.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    Button _BtnReg,_BtnLogin;
    EditText _txtName,_txtemail,_txtadd,_txtpass,_txtConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhxa();
        _BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=_txtName.getText().toString();
                String email=_txtemail.getText().toString();
                String add=_txtadd.getText().toString();
                String pass=_txtpass.getText().toString();
                new RegisterTask().execute(email, name, add, pass);
            }
        });
        _BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void anhxa(){
        _BtnLogin=findViewById(R.id.loginButton);
        _BtnReg=findViewById(R.id.registerButton);
        _txtemail=findViewById(R.id.EmailEdt);
        _txtName=findViewById(R.id.usernameEditText);
        _txtadd=findViewById(R.id.AdressEdt);
        _txtpass=findViewById(R.id.passwordEditText);
        _txtConfirm=findViewById(R.id.confirmPasswordEditText);
    }
    private class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String name = params[1];
            String address = params[2];
            String password = params[3];
            String Confirm=params[4];

            try {
                URL url = new URL(Server.linkReg);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + email + "&Name=" + name + "&Address=" + address + "&Passwords=" + password + "&Confirm=" + Confirm;
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Get the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }
                br.close();

                return response.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    // Registration successful
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    // Optionally, you can navigate to the login activity or perform other actions
                } else {
                    // Registration failed
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
