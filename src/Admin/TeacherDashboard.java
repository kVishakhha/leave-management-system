package Admin;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import DB.LeaveDAO;
import DB.UserDAO;

public class TeacherDashboard {

    private JFrame frame;
    private JPanel mainPanel;

    public TeacherDashboard() {

        frame = new JFrame("Admin Dashboard");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(128, 0, 32));

        ImageIcon imgIcon = new ImageIcon(
                TeacherDashboard.class.getResource("/images/nmimslogo.png")
        );
        Image img = imgIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        topPanel.add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new MainPage();
        });
        topPanel.add(logoutBtn, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, frame.getHeight()));
        sidebar.setBackground(new Color(245, 245, 245));

        JLabel leaveBtn = createSidebarLabel("Leave Requests", this::showPending);
        JLabel menteeBtn = createSidebarLabel("Mentees", this::showMentees);
        JLabel historyBtn = createSidebarLabel("History", this::showHistory);

        sidebar.add(Box.createVerticalStrut(40));
        sidebar.add(leaveBtn);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(menteeBtn);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(historyBtn);
        sidebar.add(Box.createVerticalGlue());

        frame.add(sidebar, BorderLayout.WEST);

        // ================= MAIN PANEL =================
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        // Default section
        showPending();

        frame.setVisible(true);
    }

    // ================= SECTION SWITCH =================
    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(new JScrollPane(panel), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // ================= PENDING =================
    private void showPending() {
        switchPanel(createLeavePanel("pending", true));
    }

    // ================= HISTORY =================
    private void showHistory() {
        switchPanel(createHistoryPanel());
    }

    // ================= MENTEES =================
    private void showMentees() {
        switchPanel(createMenteePanel());
    }

    // ================= LEAVE PANEL =================
    private JPanel createLeavePanel(String status, boolean allowAction) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(
                status.equals("pending")
                        ? "Pending Leave Requests"
                        : "Leave History"
        );
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panel.add(heading);

        List<Request> requests = LeaveDAO.getRequestsByStatus(status);

        if (requests.isEmpty()) {
            panel.add(new JLabel("No records found"));
        } else {
            for (Request r : requests) {
                panel.add(createRequestRow(r, allowAction));
            }
        }

        return panel;
    }

    private JPanel createHistoryPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Approved / Rejected Requests");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panel.add(heading);

        List<Request> approved = LeaveDAO.getRequestsByStatus("approved");
        List<Request> rejected = LeaveDAO.getRequestsByStatus("rejected");

        if (approved.isEmpty() && rejected.isEmpty()) {
            panel.add(new JLabel("No history available"));
        }

        for (Request r : approved) {
            panel.add(createHistoryRow(r));
        }
        for (Request r : rejected) {
            panel.add(createHistoryRow(r));
        }

        return panel;
    }

    private JPanel createRequestRow(Request r, boolean allowAction) {

        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(700, 40));
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        row.setBackground(new Color(240, 240, 240));

        row.add(new JLabel("Student ID: " + r.studentId), BorderLayout.WEST);

        if (allowAction) {
            JButton view = new JButton("View");
            view.addActionListener(e -> openRequestWindow(r));
            row.add(view, BorderLayout.EAST);
        }

        return row;
    }

    private JPanel createHistoryRow(Request r) {

        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(700, 35));
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        Color bg = r.status.equalsIgnoreCase("approved")
                ? new Color(220, 255, 220)
                : new Color(255, 220, 220);

        row.setBackground(bg);

        row.add(new JLabel(
                "Student ID: " + r.studentId +
                " | " + r.fromDate + " → " + r.toDate +
                " | " + r.status.toUpperCase()
        ), BorderLayout.WEST);

        return row;
    }

    private void openRequestWindow(Request r) {

        JFrame f = new JFrame("Leave Request");
        f.setSize(400, 350);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
        f.setLocationRelativeTo(frame);

        f.add(new JLabel("Student ID: " + r.studentId));
        f.add(new JLabel("From: " + r.fromDate));
        f.add(new JLabel("To: " + r.toDate));
        f.add(new JLabel("Reason: " + r.reason));

        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");

        approve.addActionListener(e -> {
            LeaveDAO.updateStatus(r.id, "approved");
            f.dispose();
            showPending();
        });

        reject.addActionListener(e -> {
            LeaveDAO.updateStatus(r.id, "rejected");
            f.dispose();
            showPending();
        });

        JPanel btns = new JPanel();
        btns.add(approve);
        btns.add(reject);

        f.add(btns);
        f.setVisible(true);
    }

    // ================= MENTEES =================
    private JPanel createMenteePanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Mentees");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panel.add(heading);

        List<Mentee> mentees = UserDAO.getAllStudents();

        if (mentees.isEmpty()) {
            panel.add(new JLabel("No students found"));
        } else {
            for (Mentee m : mentees) {
                panel.add(new JLabel("SAP ID: " + m.id + " | Name: " + m.name));
            }
        }

        return panel;
    }

    // ================= SIDEBAR LABEL =================
    private JLabel createSidebarLabel(String text, Runnable action) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });

        return label;
    }
}
