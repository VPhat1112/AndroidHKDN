package com.example.apphkdn.ultil;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginTask extends AsyncTask<String, Void, String> {

    private LoginCallback callback;

    public LoginTask(LoginCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        try {
            URL url = new URL("http://192.168.1.25/server/login.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method and set output to true
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Create the query parameters
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
                    "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            // Write the parameters to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }
        } catch (IOException e) {
            Log.e("LoginTask", "Error during login", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Handle the login result
            if (result.equals("Login successful")) {
                callback.onLoginSuccess();
            } else {
                callback.onLoginFailure(result);
            }
        } else {
            callback.onLoginFailure("An error occurred");
        }
    }

    public interface LoginCallback {
        void onLoginSuccess();

        void onLoginFailure(String error);
    }
}
