package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button _BtnLog,_BtnReg,_BtnLogOTP;
    EditText emailEdt,PassEdt;
    RequestDB requestDB = new RequestDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        onClick();
    }
    private void onClick(){
        _BtnLog.setOnClickListener(this);
        _BtnReg.setOnClickListener(this);
        _BtnLogOTP.setOnClickListener(this);
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
                    int is_verified=jsonObject.getInt("is_verified");
                    int role=jsonObject.getInt("role");
                    String phone = jsonObject.getString("phone");
                    String Info_pay = jsonObject.getString("Info_pay");
                    String imgUS = jsonObject.getString("imgUS");

                    // You can save the user details in SharedPreferences or other storage
                    // and navigate to the next activity
                    if (role==1) {
                        requestDB.showInvalidOtpDialogERROR(LoginActivity.this,"Ứng dụng không có quyền đăng nhập admin!!!");
                    }else {
                        if (is_verified==0){
                            requestDB.showInvalidOtpDialog(LoginActivity.this);
                        }else {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            DataLocalManager.setIdUser(userId);
                            DataLocalManager.setEmailUser(email);
                            DataLocalManager.setNameUser(Name);
                            DataLocalManager.setAddressUser(Address);
                            DataLocalManager.setRoleUser(role);
                            DataLocalManager.setPhoneUser(phone);
                            DataLocalManager.setInfoPayUser(Info_pay);
                            DataLocalManager.setImageUser(imgUS);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
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

    public void initUI(){
        _BtnLog=findViewById(R.id.loginButtonLog);
        _BtnReg=findViewById(R.id.registerButtonLog);
        emailEdt=findViewById(R.id.EmailEdtLog);
        PassEdt=findViewById(R.id.passwordLoginET);
        _BtnLogOTP=findViewById(R.id.LoginWithOTP);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButtonLog){
            String email=emailEdt.getText().toString().trim();
            String pass=PassEdt.getText().toString().trim();
            //login(email,pass);
            new LoginTask().execute(email, pass);
        } else if (v.getId() == R.id.registerButtonLog) {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.LoginWithOTP) {
            Intent intent = new Intent(LoginActivity.this, LoginOtp.class);
            startActivity(intent);
        }
    }
}
