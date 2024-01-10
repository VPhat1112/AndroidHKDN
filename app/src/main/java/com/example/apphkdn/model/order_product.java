package com.example.apphkdn.model;

public class order_product {
    int quantity,product_price,Product_TotalPay,product_id;
    String product_name,product_image;

    public order_product(int quantity, int product_price, int product_TotalPay, int product_id, String product_name, String product_image) {
        this.quantity = quantity;
        this.product_price = product_price;
        this.Product_TotalPay = product_TotalPay;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_TotalPay() {
        return Product_TotalPay;
    }

    public void setProduct_TotalPay(int product_TotalPay) {
        Product_TotalPay = product_TotalPay;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
