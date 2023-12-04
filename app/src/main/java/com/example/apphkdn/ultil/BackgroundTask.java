package com.example.apphkdn.ultil;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    public BackgroundTask(Context context){
        this.context=context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String Type = strings[0];
        String RegUrl=Server.linkReg;
        String LogUrl=Server.linkLog;
        if (Type.equals("reg")){
            String Name =strings[1];
            String email =strings[2];
            String add =strings[3];
            String pass =strings[4];
            try{
                URL url= new URL(RegUrl);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream= httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+
                            "&"+URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(Name,"UTF-8")+
                            "&"+URLEncoder.encode("passwords","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+
                            "&"+URLEncoder.encode("Address","UTF-8")+"="+URLEncoder.encode(add,"UTF-8");
                    bufferedWriter.write(insert_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream =httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result="";
                    String line="";
                    StringBuilder stringBuilder= new StringBuilder();
                    while ((line= bufferedReader.readLine())!=null){
                        stringBuilder.append(line).append("\n");

                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                }catch (Exception e){

                }
            }catch (Exception e){

            }
        } else if (Type.equals("Login")){
            String email =strings[1];
            String Passwords =strings[2];
            try{
                URL url= new URL(LogUrl);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream= httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+
                            "&"+URLEncoder.encode("Passwords","UTF-8")+"="+URLEncoder.encode(Passwords,"UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream =httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result="";
                    String line="";
                    StringBuilder stringBuilder= new StringBuilder();
                    while ((line= bufferedReader.readLine())!=null){
                        stringBuilder.append(line).append("\n");

                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                }catch (Exception e){

                }
            }catch (Exception e){

            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {

        Toast.makeText(context, s, Toast.LENGTH_LONG).show();

//        // Check the result string and switch activities accordingly
//        if ("Login success".equals(s)) {
//            // Login was successful, switch to MainActivity
//            Intent intent = new Intent(context, MainActivity.class);
//            context.startActivity(intent);
//        } else if ("Register Succesfull".equals(s)) {
//            // Registration was successful, switch to LoginActivity (or any other activity)
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//        } else {
//            // Action failed, you may handle this case as needed
//            Toast.makeText(context, "Action Failed", Toast.LENGTH_LONG).show();
//        }
    }
}
