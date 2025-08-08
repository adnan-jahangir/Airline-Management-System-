package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class AnalyticsDashboard extends JFrame {

    public AnalyticsDashboard() {
        setTitle("Airline Revenue Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createRevenueChartPanel(), BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("← Back to Home");
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Home(); 
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createRevenueChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String url = "jdbc:mysql:///airlinemanagementsystem"; 
        String user = "root";
        String password = "23154";

        String query = """
            SELECT r.des AS destination, SUM(dp.price) AS total_revenue
            FROM reservation r
            JOIN destination_price dp ON r.des = dp.destination
            GROUP BY r.des
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String dest = rs.getString("destination");
                double revenue = rs.getDouble("total_revenue");
                dataset.addValue(revenue, "Revenue", dest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Revenue by Destination",
                "Destination",
                "Revenue (BDT)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(750, 400));
        return chartPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnalyticsDashboard());
    }
}
