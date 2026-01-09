package Student;

import javax.swing.*;
import Utils.Session;

public class StudentDashboard {

    public StudentDashboard() {

        JFrame frame = new JFrame("Student Dashboard");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JButton apply = new JButton("Apply Leave");
        apply.setBounds(100, 50, 200, 30);

        JButton stats = new JButton("View Leave Stats");
        stats.setBounds(100, 100, 200, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(100, 150, 200, 30);

        frame.add(apply);
        frame.add(stats);
        frame.add(logout);
        frame.setVisible(true);

        apply.addActionListener(e ->
                new ApplyLeaveForm(Session.currentUserId));

        stats.addActionListener(e ->
                new LeaveStatsViewer(Session.currentUserId));

        logout.addActionListener(e -> {
            frame.dispose();
            new StudentLogin();
        });
    }
}
