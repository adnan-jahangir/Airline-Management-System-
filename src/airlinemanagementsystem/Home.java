package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    public Home() {
        setLayout(null);

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/new home4.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1300, 625);
        add(image);

        
JLabel heading = new JLabel("AIR IIUC WELCOMES YOU", SwingConstants.CENTER);
heading.setFont(new Font("Bodoni MT", Font.BOLD, 40));
heading.setForeground(Color.white); 

int labelWidth = 800;
int labelHeight = 60;
int xPos = (1300 - labelWidth) / 2;
heading.setBounds(xPos, 40, labelWidth, labelHeight);

image.add(heading);


image.add(heading);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBounds(0, 0, 1300, 40);
        menuPanel.setBackground(Color.decode("0x927ada"));
        image.add(menuPanel);

        // Menu Bar
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.decode("0x927ada"));
        menubar.setBorder(BorderFactory.createEmptyBorder());
        menuPanel.add(menubar);

        Font menuFont = new Font("Bodoni MT", Font.BOLD, 18);

        // 1. DETAILS
        JMenu details = createStyledMenu("Details", Color.BLACK, menuFont);
        menubar.add(details);
        addMenuItem(details, "Add Customer Details");
        addMenuItem(details, "Book Flight");

        // 2. UPDATE INFO
        JMenu updateDetails = createStyledMenu("Update Info", Color.BLACK, menuFont);
        menubar.add(updateDetails);
        addMenuItem(updateDetails, "Update Details");

        // 3. TICKET
        JMenu ticket = createStyledMenu("Ticket", Color.BLACK, menuFont);
        menubar.add(ticket);
        addMenuItem(ticket, "Payment");
        addMenuItem(ticket, "Boarding Pass");

        // 4. CANCEL (red)
        JMenu cancelMenu = createStyledMenu("Cancel", Color.RED, menuFont);
        menubar.add(cancelMenu);
        addMenuItem(cancelMenu, "Cancel Ticket");

        // 5. FLIGHT DETAILS
        JMenu flightDetailsMenu = createStyledMenu("Flight Details", Color.BLACK, menuFont);
        menubar.add(flightDetailsMenu);
        addMenuItem(flightDetailsMenu, "Flight Details");

        // 6. JOURNEY DETAILS
        JMenu journeyMenu = createStyledMenu("Journey Details", Color.BLACK, menuFont);
        menubar.add(journeyMenu);
        addMenuItem(journeyMenu, "Journey Details");

        // 7. ANALYSIS
        JMenu analytics = createStyledMenu("Analysis", Color.BLACK, menuFont);
        menubar.add(analytics);
        addMenuItem(analytics, "Dashboard");

        // Frame settings
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        switch (text) {
            case "Add Customer Details":
                new AddCustomer();
                break;
            case "Flight Details":
                new FlightInfo();
                break;
            case "Book Flight":
                new BookFlight();
                break;
            case "Journey Details":
                new JourneyDetails();
                break;
            case "Cancel Ticket":
                new Cancel();
                break;
            case "Payment":
                new Payment(1500);
                break;
            case "Boarding Pass":
                new BoardingPass();
                break;
            case "Update Details":
                new Update();
                break;
            case "Dashboard":
                new AnalyticsDashboard();
                break;
        }
    }


    private JMenu createStyledMenu(String title, Color fgColor, Font font) {
        JMenu menu = new JMenu(title);
        menu.setOpaque(true);
        menu.setBackground(Color.decode("0x927ada"));
        menu.setForeground(fgColor);
        menu.setFont(font);
        menu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        menu.setMargin(new Insets(5, 15, 5, 15));
        return menu;
    }

 
    private void addMenuItem(JMenu menu, String title) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(this);
        item.setOpaque(true);
        item.setBackground(Color.decode("#22e5df")); 
        item.setForeground(Color.BLACK);            
        item.setFont(new Font("Arial", Font.PLAIN, 14));
        item.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        menu.add(item);
    }

    public static void main(String[] args) {
        new Home();
    }
}
