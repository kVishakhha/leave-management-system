package Admin;

import javax.swing.*;

public class AdminDashboard {

    public AdminDashboard() {

        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JButton mentees = new JButton("View Mentees");
        mentees.setBounds(100, 60, 200, 30);

        JButton leaves = new JButton("Approve / Reject Leaves");
        leaves.setBounds(100, 120, 200, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(100, 180, 200, 30);

        frame.add(mentees);
        frame.add(leaves);
        frame.add(logout);
        frame.setVisible(true);

        mentees.addActionListener(e -> new MenteeList());
        leaves.addActionListener(e -> new LeaveApproval());
        logout.addActionListener(e -> {
            frame.dispose();
            new AdminLogin();
        });
    }
}
