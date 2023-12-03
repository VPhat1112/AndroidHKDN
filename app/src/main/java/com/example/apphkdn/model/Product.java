package com.example.apphkdn.model;

public class Product {
    private int id;
    private String product_name,product_image,product_decs;
    private Integer product_price;
    private int category_id;

    public Product(int id, String product_name, String product_image, String product_decs, Integer product_price, int category_id) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_decs = product_decs;
        this.product_price = product_price;
        this.category_id = category_id;
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

    public Integer getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Integer product_price) {
        this.product_price = product_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }


}