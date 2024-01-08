package com.example.apphkdn.model;

public class Order {
    int user_id,shop_id,order_id,contact_id,product_id,FinalTotal,Order_status,Number_pay;
    String product_name,product_image,CreateAt,Name,Phone,Address_ship,Shop_name;


    public Order(int user_id, int shop_id, int order_id, int contact_id, int product_id, int finalTotal, int order_status, int number_pay, String product_name, String product_image, String createAt, String name, String phone, String address_ship, String shop_name) {
        this.user_id = user_id;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.contact_id = contact_id;
        this.product_id = product_id;
        this.FinalTotal = finalTotal;
        this.Order_status = order_status;
        this.Number_pay = number_pay;
        this.product_name = product_name;
        this.product_image = product_image;
        this.CreateAt = createAt;
        this.Name = name;
        this.Phone = phone;
        this.Address_ship = address_ship;
        this.Shop_name = shop_name;
    }

    public String getShop_name() {
        return Shop_name;
    }

    public void setShop_name(String shop_name) {
        Shop_name = shop_name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress_ship() {
        return Address_ship;
    }

    public void setAddress_ship(String address_ship) {
        Address_ship = address_ship;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getFinalTotal() {
        return FinalTotal;
    }

    public void setFinalTotal(int finalTotal) {
        FinalTotal = finalTotal;
    }

    public int getOrder_status() {
        return Order_status;
    }

    public void setOrder_status(int order_status) {
        Order_status = order_status;
    }

    public int getNumber_pay() {
        return Number_pay;
    }

    public void setNumber_pay(int number_pay) {
        Number_pay = number_pay;
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
