package Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import DB.LeaveDAO;

public class LeaveStatsViewer {

    public LeaveStatsViewer(String studentId) {

        JFrame frame = new JFrame("Leave Stats");
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ================= TABLE =================
        String[] columns = {"From Date", "To Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ================= LOAD DATA =================
        List<LeaveRecord> leaves = LeaveDAO.getLeavesByStudent(studentId);

        int approved = 0, pending = 0, rejected = 0;

        for (LeaveRecord lr : leaves) {
            model.addRow(new Object[]{
                    lr.fromDate,
                    lr.toDate,
                    lr.status
            });

            switch (lr.status) {
                case "APPROVED" -> approved++;
                case "REJECTED" -> rejected++;
                default -> pending++;
            }
        }

        // ================= FOOTER =================
        JLabel statsLabel = new JLabel(
                "Approved: " + approved +
                "   Pending: " + pending +
                "   Rejected: " + rejected
        );
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(statsLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
