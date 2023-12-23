package com.example.apphkdn.fragment;

import static com.example.apphkdn.ultil.Server.linkCategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.ShowProductSearchActivity;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.my_interface.RecycleViewItemClickListener;
import com.example.apphkdn.ultil.Checkconnection;
import com.example.apphkdn.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRecyclerView;
    EditText searchBox;
    ImageButton cartButton;
    ArrayList<Category> categoriesArrayList;
    CategoryAdapter categoryAdapter;
    RequestDB requestDB = new RequestDB();
    int id =0;
    Integer idCategory = 0;
    String Category_name="";
    String Category_image="";

    public CategoryFragment() {
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
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        SettingRecyleview();
    }

    private void SettingRecyleview(){
        categoriesArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoriesArrayList, new RecycleViewItemClickListener() {
            @Override
            public void onClickItemCategory(Category category) {
                Integer idCategory = category.getId();
                Intent intent = new Intent(getContext(), ShowProductSearchActivity.class);
                intent.putExtra("id_category",idCategory);
                startActivity(intent);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(categoryAdapter);
        GetDataCategory();
    }

    // Get data from table category
    private void GetDataCategory() {
        requestDB.GetCategory(getContext(),categoriesArrayList,categoryAdapter,linkCategory);
    }

    private void initUI(View view){
        mRecyclerView = view.findViewById(R.id.rey_view_category);
        searchBox = view.findViewById(R.id.searchtxt_category_fm);
        cartButton = view.findViewById(R.id.CartBtn_search_category_fm);
    }
}