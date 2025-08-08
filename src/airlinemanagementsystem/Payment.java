package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Payment extends JFrame implements ActionListener {

    JTextField tfNID, tfAccountNumber, tfPNR;
    JComboBox<String> paymentMethod;
    JButton fetchBtn, payBtn;
    JLabel background;

    JLabel basePriceLabel, discountLabel, finalPriceLabel;

    double basePrice = 0, finalPrice = 0;
    String passengerAddress = "";
    Conn c;

    public Payment(double basePrice) {
        this.basePrice = basePrice;

        setTitle("Payment Portal");
        setSize(600, 500);
        setLayout(null);

        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/PAY.png"));
        Image bgImg = bgIcon.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
        background = new JLabel(new ImageIcon(bgImg));
        background.setBounds(0, 0, 600, 500);
        add(background);

        background.setLayout(null);

        JLabel lblNID = new JLabel("Enter NID:");
        lblNID.setForeground(Color.WHITE);
        lblNID.setBounds(30, 30, 100, 25);
        background.add(lblNID);

        tfNID = new JTextField();
        tfNID.setBounds(150, 30, 200, 25);
        background.add(tfNID);

        fetchBtn = new JButton("Fetch Info");
        fetchBtn.setBounds(370, 30, 120, 25);
        fetchBtn.addActionListener(this);
        background.add(fetchBtn);

        basePriceLabel = new JLabel("Base Price: -");
        basePriceLabel.setBounds(30, 70, 400, 25);
        basePriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        basePriceLabel.setForeground(Color.WHITE);
        background.add(basePriceLabel);

        discountLabel = new JLabel("Discount: -");
        discountLabel.setBounds(30, 100, 400, 25);
        discountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountLabel.setForeground(Color.WHITE);
        background.add(discountLabel);

        finalPriceLabel = new JLabel("Total Payable: -");
        finalPriceLabel.setBounds(30, 130, 400, 25);
        finalPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        finalPriceLabel.setForeground(Color.WHITE);
        background.add(finalPriceLabel);

        JLabel lblMethod = new JLabel("Payment Method:");
        lblMethod.setBounds(30, 170, 120, 25);
        lblMethod.setForeground(Color.WHITE);
        background.add(lblMethod);

        String[] methods = {"Card", "Banking", "Bkash", "Nagad"};
        paymentMethod = new JComboBox<>(methods);
        paymentMethod.setBounds(150, 170, 200, 25);

        paymentMethod.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Arial", Font.BOLD, 14));
                String method = value.toString();
                switch (method) {
                    case "Card": label.setForeground(Color.BLUE); label.setBackground(Color.WHITE); break;
                    case "Banking": label.setForeground(Color.WHITE); label.setBackground(Color.decode("#439DF7")); break;
                    case "Bkash": label.setForeground(Color.BLACK); label.setBackground(Color.decode("#E15D5D")); break;
                    case "Nagad": label.setForeground(Color.RED); break;
                    default: label.setForeground(list.getForeground()); label.setBackground(list.getBackground());
                }
                if (isSelected) label.setBackground(label.getBackground().darker());
                return label;
            }
        });

        background.add(paymentMethod);

        JLabel lblAccNum = new JLabel("Account / Mobile No:");
        lblAccNum.setBounds(30, 210, 150, 25);
        lblAccNum.setForeground(Color.WHITE);
        background.add(lblAccNum);

        tfAccountNumber = new JTextField();
        tfAccountNumber.setBounds(180, 210, 170, 25);
        background.add(tfAccountNumber);

        JLabel lblPNR = new JLabel("Enter PNR:");
        lblPNR.setBounds(30, 250, 100, 25);
        lblPNR.setForeground(Color.WHITE);
        background.add(lblPNR);

        tfPNR = new JTextField();
        tfPNR.setBounds(150, 250, 200, 25);
        background.add(tfPNR);

        payBtn = new JButton("Pay Now");
        payBtn.setBounds(150, 300, 120, 30);
        payBtn.setEnabled(false);
        payBtn.addActionListener(this);
        background.add(payBtn);

        setLocationRelativeTo(null);
        setVisible(true);

        c = new Conn();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchBtn) {
            String nid = tfNID.getText().trim();

            if (nid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter NID number.");
                return;
            }

            try {
                
                String passengerQuery = "SELECT address FROM passenger WHERE NID = ?";
                PreparedStatement pst = c.c.prepareStatement(passengerQuery);
                pst.setString(1, nid);
                ResultSet rs = pst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "NID not found in the system.");
                    resetPriceLabels();
                    payBtn.setEnabled(false);
                    rs.close();
                    pst.close();
                    return;
                }
                passengerAddress = rs.getString("address");
                rs.close();
                pst.close();


                int bookingCount = 0;
                String countBookingQuery = "SELECT COUNT(*) AS booking_count FROM reservation WHERE NID = ?";
                PreparedStatement pstBooking = c.c.prepareStatement(countBookingQuery);
                pstBooking.setString(1, nid);
                ResultSet rsBooking = pstBooking.executeQuery();
                if (rsBooking.next()) {
                    bookingCount = rsBooking.getInt("booking_count");
                }
                rsBooking.close();
                pstBooking.close();

             
                String pnr = tfPNR.getText().trim();

                String destination = null;

                if (pnr.isEmpty()) {
                    
                    String destQuery = "SELECT des FROM reservation WHERE NID = ? LIMIT 1";
                    pst = c.c.prepareStatement(destQuery);
                    pst.setString(1, nid);
                    rs = pst.executeQuery();

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "No reservation found for this NID.");
                        resetPriceLabels();
                        payBtn.setEnabled(false);
                        rs.close();
                        pst.close();
                        return;
                    }
                    destination = rs.getString("des");
                    rs.close();
                    pst.close();

                } else {
              
                    String destQuery = "SELECT des FROM reservation WHERE PNR = ?";
                    pst = c.c.prepareStatement(destQuery);
                    pst.setString(1, pnr);
                    rs = pst.executeQuery();

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "PNR not found.");
                        resetPriceLabels();
                        payBtn.setEnabled(false);
                        rs.close();
                        pst.close();
                        return;
                    }
                    destination = rs.getString("des");
                    rs.close();
                    pst.close();
                }

               
                String priceQuery = "SELECT price FROM destination_price WHERE destination = ?";
                pst = c.c.prepareStatement(priceQuery);
                pst.setString(1, destination);
                rs = pst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Price not found for destination: " + destination);
                    resetPriceLabels();
                    payBtn.setEnabled(false);
                    rs.close();
                    pst.close();
                    return;
                }
                basePrice = rs.getDouble("price");
                rs.close();
                pst.close();

               
                boolean hasAddressDiscount = false;
                if (passengerAddress != null) {
                    String addrLower = passengerAddress.toLowerCase();
                    if (addrLower.contains("iiuc") || addrLower.contains("faz")) {
                        hasAddressDiscount = true;
                    }
                }

                double totalDiscountPercent = 0.0;

                if (hasAddressDiscount) totalDiscountPercent += 30.0;
                if (bookingCount > 1) totalDiscountPercent += 5.0; 

                double discountAmount = (basePrice * totalDiscountPercent) / 100.0;
                finalPrice = basePrice - discountAmount;

                basePriceLabel.setText(String.format("Base Price: %.2f BDT", basePrice));
                discountLabel.setText(String.format("Discount: %.2f BDT (%.0f%%)", discountAmount, totalDiscountPercent));
                finalPriceLabel.setText(String.format("Total Payable: %.2f BDT", finalPrice));

        
                payBtn.setEnabled(!pnr.isEmpty());

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error occurred.");
                resetPriceLabels();
                payBtn.setEnabled(false);
            }
        } else if (ae.getSource() == payBtn) {
            String method = (String) paymentMethod.getSelectedItem();
            String account = tfAccountNumber.getText().trim();
            String nid = tfNID.getText().trim();
            String pnr = tfPNR.getText().trim();

            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter PNR to make payment.");
                return;
            }

           
            if (method.equals("Card") || method.equals("Banking")) {
                if (!account.matches("\\d{5}")) {
                    JOptionPane.showMessageDialog(null, "Account number must be 5 digits.");
                    return;
                }
            } else {
                if (!account.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(null, "Mobile number must be 11 digits.");
                    return;
                }
            }

            try {
                String insertPayment = "INSERT INTO payment (NID, PNR, amount, method, account_number) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(insertPayment);
                pst.setString(1, nid);
                pst.setString(2, pnr);
                pst.setDouble(3, finalPrice);
                pst.setString(4, method);
                pst.setString(5, account);

                int updated = pst.executeUpdate();
                pst.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Payment successful via " + method + "\nAmount Paid: " + finalPrice + " BDT");
                    this.setVisible(false);
                    this.dispose();

                    new BoardingPass(pnr).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed. Please try again.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error during payment.");
            }
        }
    }

    private void resetPriceLabels() {
        basePriceLabel.setText("Base Price: -");
        discountLabel.setText("Discount: -");
        finalPriceLabel.setText("Total Payable: -");
    }

    public static void main(String[] args) {
        new Payment(0);
    }
}
