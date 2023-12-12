package com.example.apphkdn.ultil;

import android.os.AsyncTask;
import android.util.Log;

import com.example.apphkdn.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetProductDetailsTask extends AsyncTask<String, Void, Product> {

    private static final String TAG = "GetProductDetailsTask";
    private ProductDetailListener listener;

    public GetProductDetailsTask(ProductDetailListener listener) {
        this.listener = listener;
    }

    @Override
    protected Product doInBackground(String... params) {
        String productId = params[0];
        Product product = null;

        try {
            URL url = new URL(Server.DetailProduct + productId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                product = parseProduct(jsonObject);
            } else {
                Log.e(TAG, "HTTP error code: " + connection.getResponseCode());
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error fetching product details", e);
        }

        return product;
    }

    @Override
    protected void onPostExecute(Product product) {
        super.onPostExecute(product);

        if (listener != null) {
            listener.onProductDetailReceived(product);
        }
    }

    private Product parseProduct(JSONObject jsonObject) throws JSONException {
        Integer id = jsonObject.getInt("id");
        String productName = jsonObject.getString("product_name");
        Integer productPrice = jsonObject.getInt("product_price");
        String productImage = jsonObject.getString("product_image");
        String productDesc = jsonObject.getString("product_decs");
        Integer categoryId = jsonObject.getInt("category_id");
        Integer shopId = jsonObject.getInt("id_shop");
        Integer product_review=jsonObject.getInt("product_review");
        Integer product_numbersell=jsonObject.getInt("product_numbersell");
        Integer product_selled=jsonObject.getInt("product_selled");

        return new Product(id, productName, productImage,productDesc, productPrice, categoryId, shopId,product_review,product_numbersell,product_selled);
    }

    public interface ProductDetailListener {
        void onProductDetailReceived(Product product);
    }
}
