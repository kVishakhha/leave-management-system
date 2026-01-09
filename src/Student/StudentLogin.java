package Student;

import javax.swing.*;
import java.sql.*;
import DB.DBConnection;
import Utils.Session;
import Admin.MainPage;

public class StudentLogin {

    private JFrame frame;
    private JTextField sapField;
    private JPasswordField passField;

    public StudentLogin() {

        frame = new JFrame("Student Login");
        frame.setSize(400, 320);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel sapLabel = new JLabel("SAP ID:");
        sapLabel.setBounds(80, 60, 100, 25);
        sapField = new JTextField();
        sapField.setBounds(160, 60, 140, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(80, 100, 100, 25);
        passField = new JPasswordField();
        passField.setBounds(160, 100, 140, 25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 140, 100, 30);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(140, 180, 100, 30);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(140, 220, 100, 30);

        frame.add(sapLabel);
        frame.add(sapField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(signupBtn);
        frame.add(backBtn);

        frame.setVisible(true);

        loginBtn.addActionListener(e -> login());

        signupBtn.addActionListener(e -> {
            frame.dispose();
            new StudentSignup();
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new MainPage();   // ✅ back to option screen
        });
    }

    private void login() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE id=? AND password=?"
            );

            ps.setInt(1, Integer.parseInt(sapField.getText()));
            ps.setString(2, new String(passField.getPassword()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Session.currentUserId = sapField.getText();
                Session.role = "STUDENT";

                frame.dispose();
                new StudentDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "SAP ID must be numeric");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
