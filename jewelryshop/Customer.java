package com.jewelryshop;
import java.util.ArrayList;

public class Customer {
    private int id;
    private String name;
    private ArrayList<Jewelry> purchasedItems;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.purchasedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public void purchaseItem(Jewelry item) {
        purchasedItems.add(item);
        System.out.println(name + " purchased: " + item.getName());
    }

    public void showPurchases() {
        System.out.println("Customer " + name + " purchases:");
        for (Jewelry j : purchasedItems) {
            j.displayDetails();
        }
    }
}
