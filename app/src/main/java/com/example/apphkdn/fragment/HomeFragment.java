package com.example.apphkdn.fragment;

import static com.example.apphkdn.ultil.Server.linkNewProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.CartActivity;
import com.example.apphkdn.activity.SearchActivity;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView searchBox;

    ImageButton CartBtn;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    RequestDB requestDB = new RequestDB();
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    private float initialX;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initUI(view);

        //Search
        Search();

        //ViewFlipper
        ActionViewFiller();

        RecyleviewSetting();
    }

    private void RecyleviewSetting(){
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(),productArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(productAdapter);

        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        GetnewProduct();

    }

    private void Search(){
        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }
    private void GetnewProduct() {
        requestDB.GetProduct(getContext(),productArrayList,productAdapter,linkNewProduct);
    }

    private void ActionViewFiller() {
        // Set auto-flipping interval (optional)
        viewFlipper.setFlipInterval(3000); // 3 seconds
        viewFlipper.startFlipping();

        // Pause flipping when touched (optional)
        viewFlipper.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = event.getX();
                    viewFlipper.stopFlipping();
                    break;

                case MotionEvent.ACTION_UP:
                    float finalX = event.getX();
                    if (initialX < finalX) {
                        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_left);
                        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_right);
                        viewFlipper.showPrevious();
                    } else if (initialX > finalX) {
                        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_right);
                        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);
                        viewFlipper.showNext();
                    }
                    viewFlipper.startFlipping();
                    break;
            }
            return true;
        });
    }

    private void initUI(View view){
        searchBox = view.findViewById(R.id.searchtxt);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        recyclerView = view.findViewById(R.id.RCV);
        CartBtn = view.findViewById(R.id.CartBtn);
    }
}