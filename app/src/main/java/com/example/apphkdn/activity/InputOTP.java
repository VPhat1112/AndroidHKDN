package com.example.apphkdn.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Arrays;
import java.util.List;

public class InputOTP extends AppCompatActivity {
    EditText otp1,otp2,otp3,otp4,otp5,otp6;
    TextView messer;
    Button _BtnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_otp);
        initUI();
        setupOtpFieldListeners();
        Intent intent = getIntent();
        String emails = intent.getStringExtra("emailotp");
        String statisafter =intent.getStringExtra("status");
        messer.setText("Status before: " + statisafter);
        _BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = otp1.getText().toString() +
                        otp2.getText().toString() +
                        otp3.getText().toString() +
                        otp4.getText().toString() +
                        otp5.getText().toString() +
                        otp6.getText().toString();


                new LoginOTPTask().execute(enteredOtp,emails);
            }
        });

    }

    private void setupOtpFieldListeners() {
        // Set up a TextWatcher for each OTP field to automatically move to the next field
        List<EditText> otpFields = Arrays.asList(otp1, otp2, otp3, otp4, otp5, otp6);

        for (int i = 0; i < otpFields.size(); i++) {
            final int index = i;
            otpFields.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not needed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Not needed
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        int length = s.length();

                        // Move focus to the next OTP field if a character is entered
                        // Move focus to the previous OTP field if a character is deleted
                        if (length == 1 && index < otpFields.size() - 1) {
                            otpFields.get(index + 1).requestFocus();
                        } else if (length == 0 && index > 0) {
                            otpFields.get(index - 1).requestFocus();
                        }
                    }
                }
            });
        }
    }


    private class LoginOTPTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String otp = params[0];
            String takeemail=params[1];


            try {
                URL url = new URL(Server.linkLogOTP);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + takeemail + "&otp=" + otp;
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

                    Toast.makeText(InputOTP.this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("id", userId);
                    editor.putString("email", email);
                    editor.putString("Name", Name);
                    editor.putString("Address", Address);
                    editor.apply();
                    Intent intent = new Intent(InputOTP.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    // Login failed
                    showInvalidOtpDialog();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(InputOTP.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showInvalidOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid OTP");
        builder.setMessage("The entered OTP is incorrect. Please try again.");

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

    public void initUI(){
        otp1=findViewById(R.id.editTextOtp1);
        otp2=findViewById(R.id.editTextOtp2);
        otp3=findViewById(R.id.editTextOtp3);
        otp4=findViewById(R.id.editTextOtp4);
        otp5=findViewById(R.id.editTextOtp5);
        otp6=findViewById(R.id.editTextOtp6);
        messer=findViewById(R.id.Messafter);
        _BtnSubmit=findViewById(R.id.btnSubmitOTP);
    }
}