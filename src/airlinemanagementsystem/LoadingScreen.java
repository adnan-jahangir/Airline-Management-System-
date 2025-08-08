package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;

public class LoadingScreen extends JFrame {
    private JLabel planeLabel;
    private JLabel tipLabel;
    private Timer moveTimer;
    private Timer tipTimer;
    private Timer fadeTimer;
    private Clip clip;
    private int x = -120, y = 220;
    private final int PLANE_WIDTH = 100, PLANE_HEIGHT = 100;
    private final String[] tips = {
        "Loading passenger data...",
        "Optimizing flight routes...",
        "Checking aircraft status...",
        "Powered by Java & IIUC"
    };
    private int tipIndex = 0;

    public LoadingScreen() {
        setTitle("Loading...");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

      
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 24, 82), 0, getHeight(), new Color(60, 70, 130));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

      
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/airlinemanagementsystem/icons/iiuc logo.jpeg"));
        Image logoScaled = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoScaled));
        logoLabel.setBounds(20, 20, 60, 60);
        panel.add(logoLabel);

    
        JLabel titleLabel = new JLabel("Airline Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bodoni MT", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 30, getWidth(), 30);
        panel.add(titleLabel);

    
        ImageIcon icon = new ImageIcon(getClass().getResource("/airlinemanagementsystem/icons/loading2.png"));
        Image scaled = icon.getImage().getScaledInstance(PLANE_WIDTH, PLANE_HEIGHT, Image.SCALE_SMOOTH);
        planeLabel = new JLabel(new ImageIcon(scaled));
        planeLabel.setBounds(x, y, PLANE_WIDTH, PLANE_HEIGHT);
        panel.add(planeLabel);

      
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(200, 330, 200, 15);
        panel.add(progressBar);

        
        tipLabel = new JLabel(tips[0], SwingConstants.CENTER);
        tipLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        tipLabel.setForeground(Color.LIGHT_GRAY);
        tipLabel.setBounds(0, 360, getWidth(), 20);
        panel.add(tipLabel);
        rotateTips();

        JButton muteButton = new JButton("🔇");
        muteButton.setBounds(550, 20, 30, 30);
        muteButton.setFocusPainted(false);
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setForeground(Color.WHITE);
        muteButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        muteButton.setToolTipText("Mute sound");
        muteButton.addActionListener(e -> {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        });
        panel.add(muteButton);

        add(panel);
        setVisible(true);

        // 🔊 Play airplane sound
        playAirplaneSound();

        // 🛫 Animate airplane
        moveTimer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                x += 4;
                y -= 1;
                planeLabel.setLocation(x, y);

                if (x > getWidth()) {
                    moveTimer.stop();
                    fadeOutAndLaunchLogin();
                }
            }
        });
        moveTimer.start();
    }

    // 🔉 Sound effect method
    private void playAirplaneSound() {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                getClass().getResource("/airlinemanagementsystem/icons/airplane.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.err.println("Sound error: " + e.getMessage());
        }
    }

    // 💬 Rotate loading tips every 2 seconds
    private void rotateTips() {
        tipTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipIndex = (tipIndex + 1) % tips.length;
                tipLabel.setText(tips[tipIndex]);
            }
        });
        tipTimer.start();
    }

    // 🌫️ Fade out and launch login
    private void fadeOutAndLaunchLogin() {
        fadeTimer = new Timer(50, new ActionListener() {
            float opacity = 1f;
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0f) {
                    fadeTimer.stop();
                    dispose();
                    new Login();
                } else {
                    setOpacity(opacity);
                }
            }
        });
        fadeTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoadingScreen screen = new LoadingScreen();
            screen.setOpacity(1f); // Required for fade effect
        });
    }
}
