package com.jewelryshop;
import java.util.ArrayList;

public class Shop {
    private Jewelry[] inventory;
    private int count;
    private ArrayList<Customer> customers;

    public Shop() {
        inventory = new Jewelry[20];
        customers = new ArrayList<>();
        count = 0;

        inventory[count++] = new Ring(1, "Silver Ring", "Ring", 120.0, 10, 7);
        inventory[count++] = new Ring(2, "Gold Ring", "Ring", 250.0, 5, 8);
        inventory[count++] = new Necklace(3, "Pearl Necklace", "Necklace", 300.0, 8, "Pearl");
        inventory[count++] = new Necklace(4, "Platinum Necklace", "Necklace", 500.0, 3, "Platinum");
        inventory[count++] = new Bracelet(5, "Silver Bracelet", "Bracelet", 150.0, 7, "Chain");
        inventory[count++] = new Bracelet(6, "Gold Bracelet", "Bracelet", 270.0, 4, "Cuff");
        inventory[count++] = new Ring(7, "Diamond Ring", "Ring", 800.0, 2, 6);
        inventory[count++] = new Necklace(8, "Ruby Necklace", "Necklace", 450.0, 6, "Ruby");
        inventory[count++] = new Bracelet(9, "Leather Bracelet", "Bracelet", 90.0, 12, "Leather");
        inventory[count++] = new Ring(10, "Emerald Ring", "Ring", 700.0, 1, 9);
    }

    public int getInventoryCount() { return count; }
    public Jewelry getInventoryItem(int index) { return inventory[index]; }

    public void addCustomer(Customer c) { customers.add(c); }

    public Jewelry findById(int id) {
        for (int i = 0; i < count; i++)
            if (inventory[i].getId() == id) return inventory[i];
        return null;
    }

    public void sellItem(int itemId, int qty, Customer customer) throws Exception {
        Jewelry item = findById(itemId);
        if (item == null) throw new Exception("Item not found");
        if (qty <= 0) throw new Exception("Invalid quantity");
        if (item.getQuantity() < qty) throw new Exception("Not enough stock");

        item.setQuantity(item.getQuantity() - qty);
        customer.purchaseItem(item);
    }

    public void updateQuantity(int id, int delta) throws Exception {
        Jewelry item = findById(id);
        if (item == null) throw new Exception("Item not found");
        int newQty = item.getQuantity() + delta;
        if (newQty < 0) throw new Exception("Quantity cannot be negative");
        item.setQuantity(newQty);
    }

    public void setDiscount(int id, double percent) throws Exception {
        Jewelry item = findById(id);
        if (item == null) throw new Exception("Item not found");
        if (!(item instanceof Discountable)) throw new Exception("Item is not discountable");
        if (percent < 0 || percent > 100) throw new Exception("Discount must be 0-100%");
        ((Discountable) item).applyDiscount(percent);
    }

    public void setPrice(int id, double price) throws Exception {
        Jewelry item = findById(id);
        if (item == null) throw new Exception("Item not found");
        if (price < 0) throw new Exception("Price cannot be negative");
        item.setPrice(price);
    }

    public int nextId() {
        return count + 1;
    }

    public void addItem(Jewelry item) {
        if (count >= inventory.length) {
            Jewelry[] newInv = new Jewelry[inventory.length + 10];
            System.arraycopy(inventory, 0, newInv, 0, inventory.length);
            inventory = newInv;
        }
        inventory[count++] = item;
    }

    public void viewItems() {
        for (int i = 0; i < count; i++) {
            Jewelry j = inventory[i];
            System.out.println(j.getId() + " - " + j.getName() + " | " + j.getCategory() + " | Price: " + j.getPrice() + " | Qty: " + j.getQuantity());
        }
    }
}
