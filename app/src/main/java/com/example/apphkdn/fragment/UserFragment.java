package com.example.apphkdn.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.apphkdn.ultil.Server.LinkGetUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.ProfileActivity;
import com.example.apphkdn.activity.ShopActivity;
import com.example.apphkdn.ultil.Checkconnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtProfile,txtStoreInfor;
    ViewPager viewPager;
    TextView txtGotoErrorSeller;
    ScrollView scrollView;

    RequestDB requestDB = new RequestDB();

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        viewPager=view.findViewById(R.id.view_page);
        if (Checkconnection.haveNetworkConnection(getContext())){
            goToProfileActivity();
            goToStoreInfor();
        }else {
            Toast.makeText(getContext(),"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
        }
    }

    private void goToProfileActivity(){
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
    }

    private void goToStoreInfor(){
        txtStoreInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("MyProfile", MODE_PRIVATE);
                String email = preferences.getString("email","null");
                new GetUSer().execute(email);


//                int role_seller = preferences.getInt("role",0);
//                if (role_seller == 3){
//                    GotoErrorSellerFraggment();
//                }else{
//                    Intent intent = new Intent(getContext(), ShopActivity.class);
//                    startActivity(intent);
//                }
            }
        });
    }

    private class GetUSer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            try {
                URL url = new URL(LinkGetUser);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + email;
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
                    int role=jsonObject.getInt("role");
                    String phone = jsonObject.getString("phone");
                    String Info_pay = jsonObject.getString("Info_pay");
                    String imgUS = jsonObject.getString("imgUS");

                    // You can save the user details in SharedPreferences or other storage
                    // and navigate to the next activity
                    if (role==3) {
                        GotoErrorSellerFraggment();
                    }else {
                        SharedPreferences preferences = getContext().getSharedPreferences("MyProfile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("id", userId);
                        editor.putString("email", email);
                        editor.putString("Name", Name);
                        editor.putString("Address", Address);
                        editor.putInt("role", role);
                        editor.putString("phone", phone);
                        editor.putString("Info_pay", Info_pay);
                        editor.putString("imgUS", imgUS);
                        editor.apply();
                        Intent intent = new Intent(getActivity(), ShopActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // Login failed
                    Toast.makeText(getContext(), "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GotoErrorSellerFraggment(){
        txtGotoErrorSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frgm_user, new ErrorSellerFragment()).commit();
            }

        });
    }


    public void initUI(View view){
        txtProfile=view.findViewById(R.id.frpr_Profile);
        txtStoreInfor=view.findViewById(R.id.frpr_storeinfor);
        txtGotoErrorSeller=view.findViewById(R.id.frpr_storeinfor);
        scrollView=view.findViewById(R.id.scrollViewProfile_fm_user);
    }
}