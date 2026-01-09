package Student;

import javax.swing.*;
import java.sql.*;
import DB.DBConnection;

public class StudentSignup {

    public StudentSignup() {

        JFrame frame = new JFrame("Student Signup");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JTextField sapField = new JTextField();
        JTextField nameField = new JTextField();
        JPasswordField passField = new JPasswordField();

        frame.add(new JLabel("SAP ID:")).setBounds(60, 40, 100, 25);
        sapField.setBounds(160, 40, 150, 25);

        frame.add(new JLabel("Name:")).setBounds(60, 80, 100, 25);
        nameField.setBounds(160, 80, 150, 25);

        frame.add(new JLabel("Password:")).setBounds(60, 120, 100, 25);
        passField.setBounds(160, 120, 150, 25);

        JButton signupBtn = new JButton("Register");
        signupBtn.setBounds(140, 180, 100, 30);

        frame.add(sapField);
        frame.add(nameField);
        frame.add(passField);
        frame.add(signupBtn);
        frame.setVisible(true);

        signupBtn.addActionListener(e -> {

            if (sapField.getText().isEmpty() ||
                nameField.getText().isEmpty() ||
                passField.getPassword().length == 0) {

                JOptionPane.showMessageDialog(frame, "All fields are required");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users (id, name, password) VALUES (?, ?, ?)"
                );

                ps.setInt(1, Integer.parseInt(sapField.getText())); // SAP ID → id
                ps.setString(2, nameField.getText());
                ps.setString(3, new String(passField.getPassword()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Signup Successful");

                frame.dispose();
                new StudentLogin();

            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(frame, "SAP ID already exists");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "SAP ID must be numeric");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
