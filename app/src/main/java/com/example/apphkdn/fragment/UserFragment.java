package com.example.apphkdn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.apphkdn.R;
import com.example.apphkdn.adapter.SettingInfoAdapter;
import com.example.apphkdn.ultil.Checkconnection;
import com.google.android.material.navigation.NavigationView;

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

    Toolbar setting;
    ListView listView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

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
        if (Checkconnection.haveNetworkConnection(getContext())){
            ActionBar();
        }else {
            Toast.makeText(getContext(),"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
        }

        String[] data = {"Đến trang của tôi", "Chỉnh sửa thông tin"};
        int[] imageResources = {
                R.drawable.baseline_person_pin_24,
                R.drawable.outline_edit_24,
        };

        SettingInfoAdapter adapter = new SettingInfoAdapter(getContext(), data, imageResources);
        listView.setAdapter(adapter);
    }

    private void ActionBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(setting);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setting.setNavigationIcon(R.drawable.outline_settings_24);
        setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void initUI(View view){
        setting = view.findViewById(R.id.ToolBarSetting);
        drawerLayout = view.findViewById(R.id.drawableInf);
        listView = view.findViewById(R.id.LVSetting);
        navigationView = view.findViewById(R.id.NavigationSetting);
    }
}