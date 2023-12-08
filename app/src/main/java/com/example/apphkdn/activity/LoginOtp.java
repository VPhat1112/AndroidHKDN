package com.example.apphkdn.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class LoginOtp extends AppCompatActivity {
    EditText emails;
    TextView Mess;
    Button _BtnLoginOTP,_BtnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        anhxa();
        _BtnLoginOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emails.getText().toString();
                if (email.isEmpty()){
                    showInvalidOtpDialog();
                }else {
                    new LoginOTPTask().execute(email);
                }
            }
        });
        _BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOtp.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void showInvalidOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login OTP");
        builder.setMessage("Email Required!!!");

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void anhxa(){
        emails=findViewById(R.id.EmailEdtLogOTP);
        _BtnLoginOTP=findViewById(R.id.loginButtonOTP);
        _BtnReg=findViewById(R.id.registerButtonOTP);
        Mess=findViewById(R.id.message);
    }
    private class LoginOTPTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];


            try {
                URL url = new URL(Server.linkSendOTP);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + email ;
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

                    Intent intent = new Intent(LoginOtp.this, InputOTP.class);
                    intent.putExtra("emailotp",emails.getText().toString());
                    intent.putExtra("status","Đã gửi mail");
                    startActivity(intent);
                } else {
                    //Send failed
                    Toast.makeText(LoginOtp.this, "Send failed", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginOtp.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}