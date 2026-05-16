package com.jewelryshop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerGUI {
    private Shop shop;
    private JFrame frame;
    private JPanel productPanel;
    public JLabel statusLabel;
    private ShopGUI shopGUI;

    private final String PASSWORD = "admin123";

    public ManagerGUI(Shop shop, ShopGUI shopGUI) {
        this.shop = shop;
        this.shopGUI = shopGUI;
        SwingUtilities.invokeLater(this::showLoginDialog);
    }

    private void showLoginDialog() {
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Manager Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(okCxl == JOptionPane.OK_OPTION) {
            String pass = new String(pf.getPassword());
            if(PASSWORD.equals(pass)) createAndShowGUI();
            else JOptionPane.showMessageDialog(null,"Incorrect password. Access denied.");
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Manager Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10,10));
        frame.getRootPane().setBorder(new EmptyBorder(10,10,10,10));

        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0,1,10,10));

        JScrollPane scroll = new JScrollPane(productPanel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(new EmptyBorder(5,5,5,5));

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.addActionListener(e -> {
            statusLabel.setText("Changes saved!");
            if(shopGUI != null) shopGUI.buildLeftPanelItems();
        });

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.add(saveBtn, BorderLayout.NORTH);

        buildProductList();

        frame.setVisible(true);
    }

    private void buildProductList() {
        productPanel.removeAll();

        for(int i=0;i<shop.getInventoryCount();i++) {
            Jewelry item = shop.getInventoryItem(i);
            if(item == null) continue;

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.setBackground(Color.WHITE);

            // Image
            ImageIcon icon = shopGUI.getIconForItem(item);
            if(icon != null) {
                JLabel pic = new JLabel(icon);
                pic.setHorizontalAlignment(JLabel.CENTER);
                panel.add(pic, BorderLayout.EAST);
            }

            // Info + buttons
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            String discountText = (item instanceof Discountable && ((Discountable)item).getDiscount() > 0) ?
                    String.format("Discount: %.0f%%", ((Discountable)item).getDiscount()) : "No discount";

            JLabel infoLbl = new JLabel(
                    "<html>ID: " + item.getId() + "<br>" +
                            "Name: " + item.getName() + "<br>" +
                            "Category: " + item.getCategory() + "<br>" +
                            "Price: " + item.getPrice() + "<br>" +
                            "Quantity: " + item.getQuantity() + "<br>" +
                            discountText + "</html>"
            );
            infoPanel.add(infoLbl);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton addQtyBtn = new JButton("Add Qty");
            JButton removeQtyBtn = new JButton("Remove Qty");
            JButton changePriceBtn = new JButton("Change Price");
            JButton addDiscountBtn = new JButton("Add Discount");
            JButton removeDiscountBtn = new JButton("Remove Discount");

            btnPanel.add(addQtyBtn);
            btnPanel.add(removeQtyBtn);
            btnPanel.add(changePriceBtn);
            btnPanel.add(addDiscountBtn);
            btnPanel.add(removeDiscountBtn);

            infoPanel.add(btnPanel);
            panel.add(infoPanel, BorderLayout.CENTER);
            productPanel.add(panel);

            // Button actions
            addQtyBtn.addActionListener(e -> {
                String s = JOptionPane.showInputDialog(frame,"Enter quantity to add:");
                try {
                    int q = Integer.parseInt(s);
                    if(q<=0) throw new NumberFormatException();
                    item.setQuantity(item.getQuantity()+q);
                    statusLabel.setText("Added " + q + " to " + item.getName());
                    buildProductList();
                } catch(Exception ex) { JOptionPane.showMessageDialog(frame,"Invalid number"); }
            });

            removeQtyBtn.addActionListener(e -> {
                String s = JOptionPane.showInputDialog(frame,"Enter quantity to remove:");
                try {
                    int q = Integer.parseInt(s);
                    if(q<=0 || q>item.getQuantity()) throw new Exception();
                    item.setQuantity(item.getQuantity()-q);
                    statusLabel.setText("Removed " + q + " from " + item.getName());
                    buildProductList();
                } catch(Exception ex) { JOptionPane.showMessageDialog(frame,"Invalid number or exceeds stock"); }
            });

            changePriceBtn.addActionListener(e -> {
                String s = JOptionPane.showInputDialog(frame,"Enter new price:");
                try {
                    double p = Double.parseDouble(s);
                    if(p<0) throw new NumberFormatException();
                    item.setPrice(p);
                    statusLabel.setText("Price updated for " + item.getName());
                    buildProductList();
                } catch(Exception ex) { JOptionPane.showMessageDialog(frame,"Invalid price"); }
            });

            addDiscountBtn.addActionListener(e -> {
                if(!(item instanceof Discountable)) { JOptionPane.showMessageDialog(frame,"This item cannot have discount"); return; }
                String s = JOptionPane.showInputDialog(frame,"Enter discount % (0-100):");
                try {
                    double d = Double.parseDouble(s);
                    if(d<0 || d>100) throw new Exception();
                    ((Discountable)item).setDiscount(d);
                    statusLabel.setText("Discount added to " + item.getName());
                    buildProductList();
                } catch(Exception ex) { JOptionPane.showMessageDialog(frame,"Invalid discount"); }
            });

            removeDiscountBtn.addActionListener(e -> {
                if(!(item instanceof Discountable)) { JOptionPane.showMessageDialog(frame,"This item cannot have discount"); return; }
                ((Discountable)item).setDiscount(0);
                statusLabel.setText("Discount removed from " + item.getName());
                buildProductList();
            });
        }

        productPanel.revalidate();
        productPanel.repaint();
    }
}
