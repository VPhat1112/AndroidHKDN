package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apphkdn.R;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.text.DecimalFormat;
import java.util.List;

public class Detail_product extends AppCompatActivity {
    ViewFlipper viewFlipper;
    int slcSP;
    private float initialX;
    ImageView imageView;
    ImageButton backBtn;
    TextView productName,ProductPrice,sells;
    RatingBar ratingBar;
    Integer id=0;
    Integer id_category=0;
    Integer id_shop=0;
    TextView editText;
    Integer Product_price=0;
     Integer product_review;
     Integer product_numbersell;
     int product_selled;
    CardView plus,remove;

    String product_name,Product_decs,Product_image;
    private List<String> imageUrls;

    Button BtnCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        viewFlipper =findViewById(R.id.viewFlipperDT);
        ratingBar =findViewById(R.id.productRatingBar);
        id=getIntent().getIntExtra("ID_Product",-1);
        product_name=getIntent().getStringExtra("product_name");
        Product_decs=getIntent().getStringExtra("Product_decs");
        Product_image=getIntent().getStringExtra("product_image");
        Product_price=getIntent().getIntExtra("product_price",-1);
        id_shop=getIntent().getIntExtra("ID_Shop",-1);
        id_category=getIntent().getIntExtra("ID_Category",-1);
        product_review=getIntent().getIntExtra("product_review",-1);
        product_numbersell=getIntent().getIntExtra("product_numbersell",-1);
        product_selled=getIntent().getIntExtra("product_selled",-1);
        UnitUI();
        slcSP=product_numbersell-product_selled;
        editText.setText("1/"+slcSP);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                if (slg<= Integer.parseInt(editText.getText().toString().split("/")[1]))
                    slg++;
                editText.setText(slg+"/"+slcSP);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                if (slg>1)
                    slg--;
                editText.setText(slg+"/"+slcSP);
            }
        });

        productName.setText(product_name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        ProductPrice.setText(decimalFormat.format(Product_price)+"đ");
//        Toast.makeText(Detail_product.this,String.valueOf(product_selled),Toast.LENGTH_LONG).show();
        sells.setText("Đã Bán "+product_selled);
        new DownloadImageTask(imageView).execute(Product_image);

        ActionViewFiller();

        BtnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartActivity.cartLists.size() >0){
                    int count=0;
                    int sl= Integer.parseInt(editText.getText().toString().split("/")[0]);
                    for (int i=0 ;i<CartActivity.cartLists.size();i++){
                        if (CartActivity.cartLists.get(i).getProduct_id()==id){
                            CartActivity.cartLists.get(i).setProduct_pay(CartActivity.cartLists.get(i).getProduct_pay()+sl);
                            if (CartActivity.cartLists.get(i).getProduct_pay()>=10){
                                CartActivity.cartLists.get(i).setProduct_pay(10);
                            }
                            CartActivity.cartLists.get(i).setProduct_price(Product_price*CartActivity.cartLists.get(i).getProduct_pay());
                            CartActivity.cartAdapter.notifyDataSetChanged();
                            count++;
                        }
                    }
                    if (count==0){
                        int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                        int new_price=slg*Product_price;
                        CartActivity.cartLists.add(new Cart(id,product_name,new_price,Product_image,"0",slg,product_numbersell));
                        Toast.makeText(Detail_product.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    int slg = Integer.parseInt(editText.getText().toString().split("/")[0]);
                    int new_price=slg*Product_price;
                    CartActivity.cartLists.add(new Cart(id,product_name,new_price,Product_image,"0",slg,product_numbersell));
                    Toast.makeText(Detail_product.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void ActionViewFiller() {

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
                        viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
                        viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);
                        viewFlipper.showPrevious();
                    } else if (initialX > finalX) {
                        viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
                        viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);
                        viewFlipper.showNext();
                    }
                    viewFlipper.startFlipping();
                    break;
            }
            return true;
        });
    }


        private void UnitUI(){
        viewFlipper=findViewById(R.id.viewFlipperDT);
        productName=findViewById(R.id.nameProduct);
        ProductPrice=findViewById(R.id.Product_priceDT);
        imageView=findViewById(R.id.imageFiller1);
        BtnCard=findViewById(R.id.btnadd_Cart);
        sells=findViewById(R.id.sells);
        backBtn=findViewById(R.id.imageButtonback);
        editText= findViewById(R.id.edtSlgCart);
        plus=findViewById(R.id.btn_plusedtsl);
        remove=findViewById(R.id.btn_removeedtsl);
    }
}