package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate, labelftime;
    JButton fetchButton;

    public BoardingPass() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("AIR IIUC");
        heading.setBounds(380, 10, 450, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);

        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 30);
        subheading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        subheading.setForeground(Color.BLUE);
        add(subheading);

        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        add(tfpnr);

        fetchButton = new JButton("Enter");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblname = new JLabel("NAME");
        lblname.setBounds(60, 140, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 140, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("NATIONALITY");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lblsrcLabel = new JLabel("SRC");
        lblsrcLabel.setBounds(60, 220, 150, 25);
        lblsrcLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsrcLabel);

        lblsrc = new JLabel();
        lblsrc.setBounds(220, 220, 150, 25);
        add(lblsrc);

        JLabel lbldestLabel = new JLabel("DEST");
        lbldestLabel.setBounds(380, 220, 150, 25);
        lbldestLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldestLabel);

        lbldest = new JLabel();
        lbldest.setBounds(540, 220, 150, 25);
        add(lbldest);

        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 260, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 260, 150, 25);
        add(labelfname);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(380, 260, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);

        labelfcode = new JLabel();
        labelfcode.setBounds(540, 260, 150, 25);
        add(labelfcode);

        JLabel lbldateLabel = new JLabel("Date");
        lbldateLabel.setBounds(60, 300, 150, 25);
        lbldateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldateLabel);

        labeldate = new JLabel();
        labeldate.setBounds(220, 300, 200, 25);
        add(labeldate);

        // NEW: Flight Time Label
        JLabel lblftime = new JLabel("Flight Time");
        lblftime.setBounds(380, 300, 150, 25);
        lblftime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblftime);

        labelftime = new JLabel();
        labelftime.setBounds(540, 300, 150, 25);
        add(labelftime);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/airIIUC.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 230, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(600, 0, 300, 300);
        add(lblimage);

        setSize(1000, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    public BoardingPass(String pnr) {
        this();
        tfpnr.setText(pnr);
        fetchButton.doClick();
    }

    public void actionPerformed(ActionEvent ae) {
        String pnr = tfpnr.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter PNR");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM reservation WHERE PNR = ?";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("NAME"));
                tfnationality.setText(rs.getString("NATIONALITY"));
                lblsrc.setText(rs.getString("src"));
                lbldest.setText(rs.getString("des"));
                labelfname.setText(rs.getString("flightname"));
                labelfcode.setText(rs.getString("flightcode"));
                labeldate.setText(rs.getString("date"));

                // NEW: Set flight_time
                labelftime.setText(rs.getString("flight_time") != null ? rs.getString("flight_time") : "No Time Found");

            } else {
                JOptionPane.showMessageDialog(null, "Please enter a correct PNR");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error");
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
