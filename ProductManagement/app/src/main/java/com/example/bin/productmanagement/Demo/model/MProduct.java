package com.example.bin.productmanagement.Demo.model;

public class MProduct {
    int id;

    public MProduct(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    String name;
    double point;
    double price;
    double discount1;
    double discount2;
    double discount3;
    double discount4;
    int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount1() {
        return discount1;
    }

    public void setDiscount1(double discount1) {
        this.discount1 = discount1;
    }

    public double getDiscount2() {
        return discount2;
    }

    public void setDiscount2(double discount2) {
        this.discount2 = discount2;
    }

    public double getDiscount3() {
        return discount3;
    }

    public void setDiscount3(double discount3) {
        this.discount3 = discount3;
    }

    public double getDiscount4() {
        return discount4;
    }

    public void setDiscount4(double discount4) {
        this.discount4 = discount4;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MProduct(int id, String name, double price, double point, double discount1, double discount2, double discount3, double discount4, int amount) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.price = price;
        this.discount1 = discount1;
        this.discount2 = discount2;
        this.discount3 = discount3;
        this.discount4 = discount4;
        this.amount = amount;
    }



}
