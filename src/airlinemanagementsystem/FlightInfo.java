package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.Random;

public class FlightInfo extends JFrame {
    
    public FlightInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Table with DefaultTableModel so we can add a column
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from flight");

            // Get metadata for column names and count
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            // Add original columns
            for (int i = 1; i <= colCount; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }
            // Add new column for Flight Time
            model.addColumn("Flight Time");

            Random rand = new Random();

            // Add rows and generate random flight time
            while (rs.next()) {
                Object[] rowData = new Object[colCount + 1];
                for (int i = 1; i <= colCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                // Generate random time (hour 1-12, min 0-59, AM/PM)
                int hour = rand.nextInt(12) + 1;
                int min = rand.nextInt(60);
                String ampm = rand.nextBoolean() ? "AM" : "PM";
                String timeStr = String.format("%02d:%02d %s", hour, min, ampm);

                rowData[colCount] = timeStr;
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 500);
        add(jsp);

        setSize(800, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
