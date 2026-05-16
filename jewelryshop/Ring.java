package com.jewelryshop;

public class Ring extends Jewelry implements Discountable {
    private int size;
    private double discount = 0;

    public Ring(int id, String name, String category, double price, int quantity, int size) {
        super(id, name, category, price, quantity);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void displayDetails() {
        System.out.println("Ring ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Category: " + getCategory());
        System.out.println("Price: " + getPrice() + (discount > 0 ? " (Discounted)" : ""));
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Size: " + getSize());
        if(discount > 0) System.out.println("Discount: " + discount + "%");
    }

    @Override
    public double applyDiscount(double percentage) {
        discount = percentage;
        double newPrice = getPrice() * (1 - discount / 100);
        setPrice(newPrice);
        return newPrice;
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double percentage) {
        discount = percentage;
        double newPrice = getPrice() * (1 - discount / 100);
        setPrice(newPrice);
    }
}
