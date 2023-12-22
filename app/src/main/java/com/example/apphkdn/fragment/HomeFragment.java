package com.example.apphkdn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.activity.CartActivity;
import com.example.apphkdn.activity.SearchActivity;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    ViewFlipper viewFlipper;
    public RelativeLayout layoutproduct;
    RecyclerView recyclerView;
    private float initialX;
    public static ArrayList<Cart> cartArrayList;

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

        GetnewProduct();
    }

    private void RecyleviewSetting(){
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(),productArrayList);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Server.linkNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    Integer id = 0;
                    String product_name="";
                    Integer product_price=0;
                    String product_image="";
                    String product_decs="";
                    Integer product_view=0;
                    Integer IDCategory=0;
                    int IDShop=0;
                    int product_review=0;
                    Integer product_numbersell=0;
                    Integer product_selled=0;
                    Integer i=0;
                    for (i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            product_name=jsonObject.getString("product_name");
                            product_price=jsonObject.getInt("product_price");
                            product_image=jsonObject.getString("product_image");
                            product_decs=jsonObject.getString("product_decs");
                            IDCategory=jsonObject.getInt("IDcategory");
                            IDShop=jsonObject.getInt("id_shop");
                            product_review=jsonObject.getInt("product_review");
                            product_numbersell=jsonObject.getInt("product_numbersell");
                            product_selled=jsonObject.getInt("product_selled");
                            productArrayList.add(new Product(id,product_name,product_image,product_decs,product_price,IDCategory,IDShop,product_review,product_numbersell,product_selled));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
//                    Toast.makeText(getContext(),i.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
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