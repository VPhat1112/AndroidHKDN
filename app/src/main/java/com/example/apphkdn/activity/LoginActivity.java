package com.example.apphkdn.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {
    Button _BtnLog,_BtnReg;
    EditText emailEdt,PassEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();

        _BtnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEdt.getText().toString();
                String pass=PassEdt.getText().toString();
                String Type="Login";
                new LoginTask().execute(email, pass);
            }
        });
        _BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void anhxa(){
        _BtnLog=findViewById(R.id.loginButton);
        _BtnReg=findViewById(R.id.registerButton);
        emailEdt=findViewById(R.id.EmailEdt);
        PassEdt=findViewById(R.id.passwordLoginET);
    }
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String Passwords = params[1];

            try {
                URL url = new URL(Server.linkLog);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + email + "&Passwords=" + Passwords;
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
                    // Login successful
                    int userId = jsonObject.getInt("id");
                    String Name = jsonObject.getString("Name");
                    String email = jsonObject.getString("email");
                    String Address = jsonObject.getString("Address");
                    // You can save the user details in SharedPreferences or other storage
                    // and navigate to the next activity
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("id", userId);
                    editor.putString("email", email);
                    editor.putString("Name", Name);
                    editor.putString("Address", Address);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Login failed
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
