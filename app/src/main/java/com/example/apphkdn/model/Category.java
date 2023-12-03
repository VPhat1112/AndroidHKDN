package com.example.apphkdn.model;

public class Category {
    public int id;
    public String category_name;
    public String category_image;

    public Category(int id, String category_name, String category_image) {
        this.id = id;
        this.category_name = category_name;
        this.category_image = category_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }
}
