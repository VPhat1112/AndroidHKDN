package com.example.apphkdn.model;

public class Product {
    private int id;
    private String product_name, product_image, product_decs;
    private double product_price;
    private int category_id;

    private int id_shop;
    private int product_review;
    private Integer product_numbersell;
    private Integer product_selled;

    public Product(int id, String product_name, String product_image, String product_decs, double product_price, int category_id, int id_shop, int product_review, Integer product_numbersell, Integer product_selled) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_decs = product_decs;
        this.product_price = product_price;
        this.category_id = category_id;
        this.id_shop = id_shop;
        this.product_review = product_review;
        this.product_numbersell = product_numbersell;
        this.product_selled = product_selled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProduct_decs() {
        return product_decs;
    }

    public void setProduct_decs(String product_decs) {
        this.product_decs = product_decs;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId_shop() {
        return id_shop;
    }

    public void setId_shop(int id_shop) {
        this.id_shop = id_shop;
    }

    public int getProduct_review() {
        return product_review;
    }

    public void setProduct_review(int product_review) {
        this.product_review = product_review;
    }

    public Integer getProduct_numbersell() {
        return product_numbersell;
    }

    public void setProduct_numbersell(Integer product_numbersell) {
        this.product_numbersell = product_numbersell;
    }

    public Integer getProduct_selled() {
        return product_selled;
    }

    public void setProduct_selled(Integer product_selled) {
        this.product_selled = product_selled;
    }
}
