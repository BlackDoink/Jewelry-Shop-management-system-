package com.jewelryshop;

import java.util.Scanner;
import javax.swing.*;

public class Main {
    static {
        System.out.println("Welcome to Jewelry Shop Management System!");
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        Customer currentCustomer = new Customer(1, "Ryan");
        shop.addCustomer(currentCustomer);

        SwingUtilities.invokeLater(() -> {
            String[] options = {"Manager Login", "Customer Shop", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to Jewelry Shop Management System",
                    "Select Mode",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                String pwd = JOptionPane.showInputDialog("Enter Manager Password:");
                if ("admin123".equals(pwd)) {
                    // Create ShopGUI first, then ManagerGUI
                    ShopGUI shopGUI = new ShopGUI(shop, currentCustomer);
                    new ManagerGUI(shop, shopGUI);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password!");
                }
            } else if (choice == 1) {
                new ShopGUI(shop, currentCustomer);
            }
        });

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1 Add Ring  2 Add Necklace  3 Add Bracelet  4 View Items  5 Sell Item  6 Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine();

            try {
                if (ch.equals("1")) {
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Category: "); String cat = sc.nextLine();
                    System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Quantity: "); int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("Size: "); int size = Integer.parseInt(sc.nextLine());
                    Ring r = new Ring(shop.nextId(), name, cat, price, qty, size);
                    shop.addItem(r);
                    System.out.println("Ring added!");
                } else if (ch.equals("2")) {
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Category: "); String cat = sc.nextLine();
                    System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Quantity: "); int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("Material: "); String material = sc.nextLine();
                    Necklace n = new Necklace(shop.nextId(), name, cat, price, qty, material);
                    shop.addItem(n);
                    System.out.println("Necklace added!");
                } else if (ch.equals("3")) {
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Category: "); String cat = sc.nextLine();
                    System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Quantity: "); int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("Type: "); String type = sc.nextLine();
                    Bracelet b = new Bracelet(shop.nextId(), name, cat, price, qty, type);
                    shop.addItem(b);
                    System.out.println("Bracelet added!");
                } else if (ch.equals("4")) {
                    shop.viewItems();
                } else if (ch.equals("5")) {
                    System.out.print("Item ID to sell: "); int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Quantity: "); int qty = Integer.parseInt(sc.nextLine());
                    shop.sellItem(id, qty, currentCustomer);
                } else if (ch.equals("6")) break;
                else System.out.println("Invalid choice");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        currentCustomer.showPurchases();
        sc.close();
    }
}
