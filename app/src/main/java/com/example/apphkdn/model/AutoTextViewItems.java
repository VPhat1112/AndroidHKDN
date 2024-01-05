package com.example.apphkdn.model;

public class AutoTextViewItems {

    private String img, name;

    public AutoTextViewItems(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AutoTextViewItems{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
