package com.example.apphkdn.model;

public class AutoTextViewItems {

    private String img, name, id;

    public AutoTextViewItems(String img, String name, String id) {
        this.img = img;
        this.name = name;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AutoTextViewItems{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
