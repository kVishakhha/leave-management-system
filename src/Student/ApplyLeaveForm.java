package Student;

import javax.swing.*;
import java.sql.*;
import java.util.Date;
import DB.DBConnection;
import Utils.Session;

public class ApplyLeaveForm {

    public ApplyLeaveForm(String studentId) {

        JFrame frame = new JFrame("Apply Leave");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JSpinner from = new JSpinner(new SpinnerDateModel());
        JSpinner to = new JSpinner(new SpinnerDateModel());
        JTextArea reason = new JTextArea();

        frame.add(new JLabel("From Date:")).setBounds(50, 40, 100, 25);
        from.setBounds(150, 40, 150, 25);

        frame.add(new JLabel("To Date:")).setBounds(50, 80, 100, 25);
        to.setBounds(150, 80, 150, 25);

        frame.add(new JLabel("Reason:")).setBounds(50, 120, 100, 25);
        frame.add(new JScrollPane(reason)).setBounds(150, 120, 150, 60);

        JButton submit = new JButton("Submit");
        submit.setBounds(150, 200, 100, 30);

        frame.add(from);
        frame.add(to);
        frame.add(submit);
        frame.setVisible(true);

        submit.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO leave_requests (student_id, from_date, to_date, reason, status) VALUES (?,?,?,?,?)"
                );

                ps.setString(1, studentId);
                ps.setDate(2, new java.sql.Date(((Date) from.getValue()).getTime()));
                ps.setDate(3, new java.sql.Date(((Date) to.getValue()).getTime()));
                ps.setString(4, reason.getText());
                ps.setString(5, "PENDING");

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Leave Applied Successfully");
                frame.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
