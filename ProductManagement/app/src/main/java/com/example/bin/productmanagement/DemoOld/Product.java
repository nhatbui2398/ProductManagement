package com.example.bin.productmanagement.DemoOld;

public class Product {
    int id;
    String name;
    String desc;//describe = mô tả
    String thumb;
    double price;

    public Product(int id, String name, String desc, String thumb, double price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.thumb = thumb;
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }



    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
