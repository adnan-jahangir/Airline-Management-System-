package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Update extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;
    Choice cname;
    JButton update, back;

    public Update() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("UPDATE CUSTOMER DETAILS");
        heading.setBounds(200, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 30));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblselect = new JLabel("Select Name");
        lblselect.setBounds(60, 80, 150, 25);
        lblselect.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblselect);

        cname = new Choice();
        cname.setBounds(220, 80, 150, 25);
        add(cname);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select name from passenger");
            while (rs.next()) {
                cname.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton fetch = new JButton("FETCH");
        fetch.setBounds(400, 80, 100, 25);
        fetch.addActionListener(this);
        add(fetch);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lblaadhar = new JLabel("NID");
        lblaadhar.setBounds(60, 230, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 230, 150, 25);
        add(tfaadhar);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 280, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 280, 150, 25);
        add(tfaddress);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 330, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(220, 330, 70, 25);
        rbmale.setBackground(Color.WHITE);
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(300, 330, 70, 25);
        rbfemale.setBackground(Color.WHITE);
        add(rbfemale);

        ButtonGroup gendergroup = new ButtonGroup();
        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 380, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 380, 150, 25);
        add(tfphone);

        update = new JButton("UPDATE");
        update.setBackground(Color.BLUE);
        update.setForeground(Color.WHITE);
        update.setBounds(100, 440, 120, 30);
        update.addActionListener(this);
        add(update);

        back = new JButton("BACK");
        back.setBackground(Color.RED);
        back.setForeground(Color.WHITE);
        back.setBounds(250, 440, 120, 30);
        back.addActionListener(this);
        add(back);

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/update2.png"));
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(400, 130, 280, 400);
        add(lblimage);

        setSize(900, 600);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("FETCH")) {
            String selectedName = cname.getSelectedItem();
            try {
                Conn conn = new Conn();
                ResultSet rs = conn.s.executeQuery("select * from passenger where name = '" + selectedName + "'");
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    tfphone.setText(rs.getString("phone"));
                    tfaddress.setText(rs.getString("address"));
                    tfaadhar.setText(rs.getString("NID"));

                    String gender = rs.getString("gender");

                    if (gender.equals("Male")) {
                        rbmale.setSelected(true);
                    } else {
                        rbfemale.setSelected(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Customer not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("UPDATE")) {
            String originalName = cname.getSelectedItem();
            String name = tfname.getText();
            String nationality = tfnationality.getText();
            String phone = tfphone.getText();
            String address = tfaddress.getText();
            String aadhar = tfaadhar.getText();
            String gender = rbmale.isSelected() ? "Male" : "Female";

            try {
                Conn conn = new Conn();
               String query = "update passenger set name='" + name + "', nationality='" + nationality + "', phone='" + phone + "', address='" + address + "', NID='" + aadhar + "', gender='" + gender + "' where name='" + originalName + "'";

                JOptionPane.showMessageDialog(null, "Customer Details Updated Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("BACK")) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Update();
    }
}
