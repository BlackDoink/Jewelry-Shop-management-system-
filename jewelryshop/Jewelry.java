package com.jewelryshop;

public abstract class Jewelry {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private double discount;
    private String imageFilename;

    public Jewelry(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.discount = 0;
        this.imageFilename = "";
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getDiscount() {
        return discount;
    }
    public String getImageFilename() {
        return imageFilename;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setImageFilename(String filename) {
        this.imageFilename = filename;
    }

    public double getFinalPrice() {
        return price * (1 - discount/100);
    }

    public abstract void displayDetails();
}
