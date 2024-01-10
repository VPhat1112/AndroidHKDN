package com.example.apphkdn.model;

public class Order {
    int idOrder, idUser, idShop, finalTotal, statusOrder;
    String address, phone, createAt, nameShop, imgShop;

    public Order(int idOrder, int idUser, int idShop, int finalTotal, int statusOrder, String address, String phone, String createAt, String nameShop, String imgShop) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idShop = idShop;
        this.finalTotal = finalTotal;
        this.statusOrder = statusOrder;
        this.address = address;
        this.phone = phone;
        this.createAt = createAt;
        this.nameShop = nameShop;
        this.imgShop = imgShop;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(int finalTotal) {
        this.finalTotal = finalTotal;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getImgShop() {
        return imgShop;
    }

    public void setImgShop(String imgShop) {
        this.imgShop = imgShop;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", idUser=" + idUser +
                ", idShop=" + idShop +
                ", finalTotal=" + finalTotal +
                ", statusOrder=" + statusOrder +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", createAt='" + createAt + '\'' +
                ", nameShop='" + nameShop + '\'' +
                ", imgShop='" + imgShop + '\'' +
                '}';
    }
}
