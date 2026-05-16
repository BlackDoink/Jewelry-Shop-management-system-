package com.jewelryshop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.*;

public class ShopGUI {
    private Shop shop;
    private Customer customer;
    public JFrame frame;
    private JPanel leftPanel;
    private JPanel middlePanel;
    public JTextArea billArea;
    public JLabel statusLabel;
    private Map<Integer, JTextField> qtyFields;
    private Map<Integer, Integer> cart;

    public ShopGUI(Shop shop, Customer customer) {
        this.shop = shop;
        this.customer = customer;
        this.qtyFields = new LinkedHashMap<>();
        this.cart = new LinkedHashMap<>();
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Jewelry Shop - Modern GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10,10));
        frame.getRootPane().setBorder(new EmptyBorder(10,10,10,10));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        leftPanel.setBackground(Color.WHITE);
        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        middlePanel.setBackground(Color.WHITE);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Bill"));
        rightPanel.setBackground(Color.WHITE);
        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane billScroll = new JScrollPane(billArea);
        rightPanel.add(billScroll, BorderLayout.CENTER);

        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel center = new JPanel(new BorderLayout());
        JPanel columns = new JPanel(new GridLayout(1,3,10,10));
        columns.add(leftScroll);
        columns.add(middlePanel);
        columns.add(rightPanel);
        center.add(columns, BorderLayout.CENTER);
        center.add(statusLabel, BorderLayout.SOUTH);

        frame.add(center, BorderLayout.CENTER);

        buildLeftPanelItems();
        buildMiddlePanelButtons();

        frame.setVisible(true);
    }

    public void buildLeftPanelItems() {
        leftPanel.removeAll();
        qtyFields.clear();

        for (int i = 0; i < shop.getInventoryCount(); i++) {
            Jewelry item = shop.getInventoryItem(i);
            if (item == null) continue;

            int itemId = item.getId();
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setPreferredSize(new Dimension(240, 160));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            itemPanel.setBackground(Color.WHITE);

            ImageIcon icon = getIconForItem(item);
            if (icon != null) {
                JLabel pic = new JLabel(icon);
                pic.setHorizontalAlignment(JLabel.CENTER);
                itemPanel.add(pic, BorderLayout.EAST);
            }

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            String discountText = (item instanceof Discountable && ((Discountable)item).getDiscount() > 0) ?
                    String.format("Discount: %.0f%%", ((Discountable)item).getDiscount()) : "No discount";

            JLabel nameLbl = new JLabel(item.getName() + " (ID:" + itemId + ")");
            JLabel priceLbl = new JLabel(String.format("Price: %.2f", item.getPrice()));
            JLabel discountLbl = new JLabel(discountText);
            JTextField qtyField = new JTextField("0", 4);
            qtyField.setMaximumSize(new Dimension(50,20));
            qtyField.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton addBtn = new JButton("Add to Cart");
            addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            addBtn.addActionListener(e -> {
                try {
                    int q = Integer.parseInt(qtyField.getText());
                    if (q <= 0) return;
                    cart.put(itemId, cart.getOrDefault(itemId, 0) + q);
                    statusLabel.setText(item.getName() + " x " + q + " added to cart");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity");
                }
            });

            infoPanel.add(nameLbl);
            infoPanel.add(priceLbl);
            infoPanel.add(discountLbl);
            infoPanel.add(new JLabel("Quantity:"));
            infoPanel.add(qtyField);
            infoPanel.add(addBtn);

            itemPanel.add(infoPanel, BorderLayout.CENTER);
            leftPanel.add(itemPanel);
            leftPanel.add(Box.createVerticalStrut(10));

            qtyFields.put(itemId, qtyField);
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }

    public ImageIcon getIconForItem(Jewelry item) {
        Map<String,String> map = new HashMap<>();
        map.put("Silver Ring","silverring.jpg");
        map.put("Gold Ring","goldring.jpg");
        map.put("Diamond Ring","diamond.jpg");
        map.put("Emerald Ring","emrald.jpg");
        map.put("Pearl Necklace","pearl.jpg");
        map.put("Platinum Necklace","platinum.jpg");
        map.put("Ruby Necklace","rubby.jpg");
        map.put("Silver Bracelet","silverbracelet.jpg");
        map.put("Gold Bracelet","goldbracelet.jpg");
        map.put("Leather Bracelet","leather.jpg");

        String filename = map.get(item.getName());
        if(filename == null) return null;
        File f = new File("images" + File.separator + filename);
        if(!f.exists()) return null;

        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void buildMiddlePanelButtons() {
        middlePanel.removeAll();

        JButton clearBtn = new JButton("Clear");
        JButton viewCartBtn = new JButton("View Cart");
        JButton calcTotalBtn = new JButton("Calculate Total");
        JButton confirmBtn = new JButton("Confirm Purchase");
        JButton manageBtn = new JButton("Manage Products");

        clearBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcTotalBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearBtn.setMaximumSize(new Dimension(160,40));
        viewCartBtn.setMaximumSize(new Dimension(160,40));
        calcTotalBtn.setMaximumSize(new Dimension(160,40));
        confirmBtn.setMaximumSize(new Dimension(160,40));
        manageBtn.setMaximumSize(new Dimension(160,40));

        middlePanel.add(Box.createVerticalStrut(20));
        middlePanel.add(clearBtn);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(viewCartBtn);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(calcTotalBtn);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(confirmBtn);
        middlePanel.add(Box.createVerticalStrut(20));
        middlePanel.add(manageBtn);

        clearBtn.addActionListener(e -> clearInputs());
        viewCartBtn.addActionListener(e -> showCartDialog()); // Does not update bill
        calcTotalBtn.addActionListener(e -> statusLabel.setText(String.format("Current cart total (est): %.2f", calculateTotal())));
        confirmBtn.addActionListener(e -> confirmPurchase());
        manageBtn.addActionListener(e -> new ManagerGUI(shop, this));

        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void clearInputs() {
        for(JTextField f : qtyFields.values()) f.setText("0");
        cart.clear();
        statusLabel.setText("Inputs cleared, cart emptied");
    }

    private void showCartDialog() {
        if(cart.isEmpty()) { JOptionPane.showMessageDialog(frame,"Cart is empty."); return; }
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        Map<Integer,JTextField> removeFields = new HashMap<>();

        for(Map.Entry<Integer,Integer> e : cart.entrySet()) {
            Jewelry item = shop.findById(e.getKey());
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            String discText = (item instanceof Discountable && ((Discountable)item).getDiscount() > 0) ?
                    String.format(" (Disc: %.0f%%)", ((Discountable)item).getDiscount()) : "";
            JLabel lbl = new JLabel(item.getName() + " x" + e.getValue() + discText + " => " + String.format("%.2f", item.getPrice() * e.getValue()));
            JTextField removeQty = new JTextField("0",3);
            p.add(lbl); p.add(new JLabel("Remove Quantity:")); p.add(removeQty);
            panel.add(p);
            removeFields.put(item.getId(), removeQty);
        }

        int option = JOptionPane.showConfirmDialog(frame,panel,"Cart",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(option == JOptionPane.OK_OPTION) {
            for(Map.Entry<Integer,JTextField> rf : removeFields.entrySet()) {
                try {
                    int rem = Integer.parseInt(rf.getValue().getText());
                    if(rem <= 0) continue;
                    int current = cart.get(rf.getKey());
                    if(rem >= current) cart.remove(rf.getKey());
                    else cart.put(rf.getKey(), current - rem);
                } catch(NumberFormatException ignored) {}
            }
            statusLabel.setText("Cart updated!");
        }
    }

    private double calculateTotal() {
        double sum = 0;
        for(Map.Entry<Integer,Integer> e : cart.entrySet()) {
            Jewelry item = shop.findById(e.getKey());
            if(item != null) sum += item.getPrice() * e.getValue();
        }
        return sum;
    }

    private void confirmPurchase() {
        if(cart.isEmpty()) { JOptionPane.showMessageDialog(frame,"Cart is empty."); return; }

        for(Map.Entry<Integer,Integer> e : cart.entrySet()) {
            Jewelry item = shop.findById(e.getKey());
            int q = e.getValue();
            if(item.getQuantity() < q) { JOptionPane.showMessageDialog(frame,"Not enough stock for " + item.getName()); return; }
        }

        double total = 0;
        billArea.setText("");

        for(Map.Entry<Integer,Integer> e : cart.entrySet()) {
            Jewelry item = shop.findById(e.getKey());
            int q = e.getValue();
            double price = item.getPrice() * q;
            String discText = "";
            if(item instanceof Discountable && ((Discountable)item).getDiscount() > 0) {
                double disc = ((Discountable)item).getDiscount();
                price = price * (1 - disc/100.0);
                discText = String.format(" (Disc: %.0f%%)", disc);
            }
            billArea.append(String.format("%s x %d%s => %.2f\n", item.getName(), q, discText, price));
            total += price;
            try { shop.sellItem(item.getId(), q, customer); }
            catch(Exception ex) { JOptionPane.showMessageDialog(frame,"Error: " + ex.getMessage()); }
        }

        billArea.append("\nTotal: " + String.format("%.2f", total));
        cart.clear();
        for(JTextField tf : qtyFields.values()) tf.setText("0");
        statusLabel.setText("Purchase confirmed!");
        buildLeftPanelItems();
    }
}
