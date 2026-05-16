package com.jewelryshop;

public class Bracelet extends Jewelry implements Discountable {
    private String type;
    private double discount = 0;

    public Bracelet(int id, String name, String category, double price, int quantity, String type) {
        super(id, name, category, price, quantity);
        this.type = type;
    }

    public String getType() { return type; }

    @Override
    public void displayDetails() {
        System.out.println("Bracelet ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Category: " + getCategory());
        System.out.println("Price: " + getPrice() + (discount > 0 ? " (Discounted)" : ""));
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Type: " + type);
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
