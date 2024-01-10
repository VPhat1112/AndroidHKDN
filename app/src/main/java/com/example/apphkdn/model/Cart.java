package com.example.apphkdn.model;

import java.util.Comparator;

public class Cart {
    int product_id;
    String product_name;
    int product_price;
    String product_image;
    String check;
    int product_pay;
    int product_numbersell;
    int shop_id;

    public Cart(int product_id, String product_name, int product_price, String product_image, String check, int product_pay, int product_numbersell, int shop_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.check = check;
        this.product_pay = product_pay;
        this.product_numbersell = product_numbersell;
        this.shop_id = shop_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_pay() {
        return product_pay;
    }

    public void setProduct_pay(int product_pay) {
        this.product_pay = product_pay;
    }

    public int getProduct_numbersell() {
        return product_numbersell;
    }

    public void setProduct_numbersell(int product_numbersell) {
        this.product_numbersell = product_numbersell;
    }

    public static class SortByShopId implements Comparator<Cart>{
        @Override
        public int compare(Cart o1, Cart o2) {
            if (o1.getShop_id() > o2.getShop_id())
                return 1;
            if (o1.getShop_id() > o2.getShop_id())
                return -1;
            return 0;
        }
    }
}
