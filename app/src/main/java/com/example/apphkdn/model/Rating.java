package com.example.apphkdn.model;

public class Rating {
    float rating;
    String Comment,id_product,user_name;

    public Rating(float rating, String comment, String id_product, String user_name) {
        this.rating = rating;
        this.Comment = comment;
        this.id_product = id_product;
        this.user_name = user_name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rating=" + rating +
                ", Comment='" + Comment + '\'' +
                ", id_product='" + id_product + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
