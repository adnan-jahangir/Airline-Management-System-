package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, close;
    JTextField tfusername;
    JPasswordField tfpassword;
    JCheckBox showPassword;
    JLabel eyeIcon;
    boolean passwordVisible = false;
    float opacity = 0f;
    Timer fadeTimer;

    public Login() {
        setTitle("User Panel Login");

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(93, 12, 255);
                Color color2 = new Color(155, 0, 250);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                super.paintComponent(g2);
            }
        };
        card.setBackground(Color.WHITE);
        card.setBounds(100, 60, 400, 280);
        card.setLayout(null);

        JLabel titleLabel = new JLabel("User Panel Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(50, 0, 300, 30);
        card.add(titleLabel);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(30, 40, 100, 20);
        card.add(lblusername);

        tfusername = new JTextField("Enter your username");
        tfusername.setBounds(150, 40, 200, 25);
        tfusername.setForeground(Color.GRAY);
        tfusername.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tfusername.getText().equals("Enter your username")) {
                    tfusername.setText("");
                    tfusername.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (tfusername.getText().isEmpty()) {
                    tfusername.setForeground(Color.GRAY);
                    tfusername.setText("Enter your username");
                }
            }
        });
        card.add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(30, 80, 100, 20);
        card.add(lblpassword);

        tfpassword = new JPasswordField("Enter your password");
        tfpassword.setEchoChar((char) 0);
        tfpassword.setBounds(150, 80, 165, 25);
        tfpassword.setForeground(Color.GRAY);
        tfpassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String pwd = new String(tfpassword.getPassword());
                if (pwd.equals("Enter your password")) {
                    tfpassword.setText("");
                    tfpassword.setEchoChar('*');
                    tfpassword.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                String pwd = new String(tfpassword.getPassword());
                if (pwd.isEmpty()) {
                    tfpassword.setForeground(Color.GRAY);
                    tfpassword.setEchoChar((char) 0);
                    tfpassword.setText("Enter your password");
                }
            }
        });
        card.add(tfpassword);

        eyeIcon = new JLabel(new ImageIcon("eye.png")); // Make sure eye.png exists
        eyeIcon.setBounds(320, 80, 25, 25);
        eyeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eyeIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                passwordVisible = !passwordVisible;
                String pwd = new String(tfpassword.getPassword());
                if (passwordVisible) {
                    tfpassword.setEchoChar((char) 0);
                } else {
                    if (!pwd.equals("Enter your password")) {
                        tfpassword.setEchoChar('*');
                    }
                }
            }
        });
        card.add(eyeIcon);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(150, 110, 150, 20);
        showPassword.setBackground(Color.WHITE);
        showPassword.addActionListener(e -> {
            String pwd = new String(tfpassword.getPassword());
            if (showPassword.isSelected()) {
                tfpassword.setEchoChar((char) 0);
                passwordVisible = true;
            } else {
                if (!pwd.equals("Enter your password")) {
                    tfpassword.setEchoChar('*');
                    passwordVisible = false;
                }
            }
        });
        card.add(showPassword);

        // 🔄 Reset Button - Amber
        reset = new JButton("Reset");
        reset.setBounds(40, 170, 100, 30);
        reset.setBackground(new Color(255, 193, 7));
        reset.setForeground(Color.BLACK);
        reset.setFocusPainted(false);
        reset.setFont(new Font("Arial", Font.BOLD, 14));
        reset.addActionListener(this);
        card.add(reset);

        // ✅ Submit Button - Green
        submit = new JButton("Submit");
        submit.setBounds(160, 170, 100, 30);
        submit.setBackground(new Color(40, 167, 69));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.addActionListener(this);
        card.add(submit);

        // ❌ Close Button - Red
        close = new JButton("Close");
        close.setBounds(280, 170, 100, 30);
        close.setBackground(new Color(220, 53, 69));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setFont(new Font("Arial", Font.BOLD, 14));
        close.addActionListener(this);
        card.add(close);

        bgPanel.add(card);
        add(bgPanel);

        // Fade-in animation
        fadeTimer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opacity += 0.02f;
                if (opacity >= 1f) {
                    opacity = 1f;
                    fadeTimer.stop();
                }
                card.repaint();
            }
        });
        fadeTimer.start();

        setSize(600, 400);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            if (username.equals("Enter your username")) username = "";
            if (password.equals("Enter your password")) password = "";

            try {
                Conn c = new Conn();
                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == close) {
            setVisible(false);
        } else if (ae.getSource() == reset) {
            tfusername.setText("Enter your username");
            tfusername.setForeground(Color.GRAY);
            tfpassword.setText("Enter your password");
            tfpassword.setEchoChar((char) 0);
            tfpassword.setForeground(Color.GRAY);
            showPassword.setSelected(false);
            passwordVisible = false;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
