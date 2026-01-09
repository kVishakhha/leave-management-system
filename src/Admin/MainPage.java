package Admin;

import javax.swing.*;
import java.awt.*;

public class MainPage {

    private JFrame frame;

    public MainPage() {

        frame = new JFrame("Request Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(128, 0, 32));

        ImageIcon logoIcon = new ImageIcon(
                ClassLoader.getSystemResource("images/nmimslogo.png")
        );
        Image logo = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logo));

        JLabel titleLabel = new JLabel("Request Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        topPanel.add(logoLabel);
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        centerPanel.setBackground(new Color(245, 245, 245));

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        studentPanel.setBackground(Color.WHITE);
        studentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        ImageIcon studentIcon = new ImageIcon(
                ClassLoader.getSystemResource("images/student.png")
        );
        Image studentImg = studentIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel studentLabel = new JLabel(new ImageIcon(studentImg));
        studentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel studentText = new JLabel("Student");
        studentText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton studentBtn = new JButton("Login as Student");
        styleButton(studentBtn);

        studentBtn.addActionListener(e -> {
            frame.dispose();
            new Student.StudentLogin();
        });

        studentPanel.add(Box.createVerticalGlue());
        studentPanel.add(studentLabel);
        studentPanel.add(Box.createVerticalStrut(10));
        studentPanel.add(studentText);
        studentPanel.add(Box.createVerticalStrut(10));
        studentPanel.add(studentBtn);
        studentPanel.add(Box.createVerticalGlue());

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setBackground(Color.WHITE);
        adminPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        ImageIcon adminIcon = new ImageIcon(
                ClassLoader.getSystemResource("images/teacher1.png")
        );
        Image adminImg = adminIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel adminLabel = new JLabel(new ImageIcon(adminImg));
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel adminText = new JLabel("Admin");
        adminText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        adminText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton adminBtn = new JButton("Login as Admin");
        styleButton(adminBtn);

        adminBtn.addActionListener(e -> {
            frame.dispose();
            new AdminLogin();
        });

        adminPanel.add(Box.createVerticalGlue());
        adminPanel.add(adminLabel);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(adminText);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(adminBtn);
        adminPanel.add(Box.createVerticalGlue());

        centerPanel.add(studentPanel);
        centerPanel.add(adminPanel);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	DB.DatabaseInitializer.init();
        new MainPage();
    }

    private static void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(128, 0, 32));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(180, 35));
    }
}
