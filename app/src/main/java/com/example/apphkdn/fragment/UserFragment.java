package com.example.apphkdn.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.apphkdn.R;
import com.example.apphkdn.activity.ProfileActivity;
import com.example.apphkdn.ultil.Checkconnection;

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
            GotoErrorSellerFraggment();
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
                int role_seller = preferences.getInt("role_seller",0);
                if (role_seller == 0){

                }
            }
        });
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
        scrollView=view.findViewById(R.id.scrollViewProfile);
    }
}