package Admin;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import DB.DBConnection;

public class AdminLogin {

    private JFrame adminLogin;
    private JTextField nameField;
    private JPasswordField passField;

    public AdminLogin() {

        adminLogin = new JFrame("Admin Login Page");
        adminLogin.setSize(600, 600);
        adminLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminLogin.setLocationRelativeTo(null);
        adminLogin.getContentPane().setBackground(new Color(245, 245, 245));
        adminLogin.setLayout(new BorderLayout(0, 80));

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(128, 0, 32));

        ImageIcon imgIcon = new ImageIcon(
                AdminLogin.class.getResource("/images/nmimslogo.png")
        );
        Image img = imgIcon.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        topPanel.add(logoLabel);

        adminLogin.add(topPanel, BorderLayout.NORTH);

        // ================= CENTER PANEL =================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel adminHeading = new JLabel("Admin", SwingConstants.CENTER);
        adminHeading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        adminHeading.setForeground(new Color(128, 0, 32));
        adminHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(adminHeading);
        centerPanel.add(Box.createVerticalStrut(20));

        JPanel formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(300, 220));
        formPanel.setMaximumSize(new Dimension(300, 220));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel name = new JLabel("Name:");
        name.setBounds(30, 30, 80, 25);
        nameField = new JTextField();
        nameField.setBounds(110, 30, 150, 25);

        JLabel pass = new JLabel("Password:");
        pass.setBounds(30, 70, 80, 25);
        passField = new JPasswordField();
        passField.setBounds(110, 70, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 100, 30);
        loginButton.setBackground(new Color(128, 0, 32));
        loginButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 160, 100, 30);

        formPanel.add(name);
        formPanel.add(nameField);
        formPanel.add(pass);
        formPanel.add(passField);
        formPanel.add(loginButton);
        formPanel.add(backButton);

        centerPanel.add(formPanel);
        adminLogin.add(centerPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> login());

        backButton.addActionListener(e -> {
            adminLogin.dispose();
            new MainPage();   // ✅ back to option screen
        });

        adminLogin.setVisible(true);
    }

    private void login() {

        String username = nameField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(adminLogin,
                    "Please enter both Name and Password.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM admins WHERE name = ? AND password = ?"
            );

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                adminLogin.dispose();
                new TeacherDashboard();
            } else {
                JOptionPane.showMessageDialog(adminLogin,
                        "Invalid credentials. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
