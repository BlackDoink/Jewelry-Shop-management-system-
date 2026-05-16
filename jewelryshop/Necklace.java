package com.jewelryshop;

public class Necklace extends Jewelry implements Discountable {
    private String material;
    private double discount = 0;

    public Necklace(int id, String name, String category, double price, int quantity, String material) {
        super(id, name, category, price, quantity);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public void displayDetails() {
        System.out.println("Necklace ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Category: " + getCategory());
        System.out.println("Price: " + getPrice() + (discount > 0 ? " (Discounted)" : ""));
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Material: " + getMaterial());
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
