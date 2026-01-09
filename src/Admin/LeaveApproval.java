package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import DB.DBConnection;

public class LeaveApproval {

    public LeaveApproval() {

        JFrame frame = new JFrame("Approve / Reject Leaves");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "SAP ID", "From", "To", "Reason", "Status"}, 0);

        JTable table = new JTable(model);

        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM leaves WHERE status='PENDING'");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("leave_id"),
                        rs.getString("sap_id"),
                        rs.getDate("from_date"),
                        rs.getDate("to_date"),
                        rs.getString("reason"),
                        rs.getString("status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton approve = new JButton("Approve");
        JButton reject = new JButton("Reject");

        approve.addActionListener(e -> updateStatus(table, "APPROVED"));
        reject.addActionListener(e -> updateStatus(table, "REJECTED"));

        JPanel btnPanel = new JPanel();
        btnPanel.add(approve);
        btnPanel.add(reject);

        frame.add(new JScrollPane(table));
        frame.add(btnPanel, "South");
        frame.setVisible(true);
    }

    private void updateStatus(JTable table, String status) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a leave first");
            return;
        }

        int leaveId = (int) table.getValueAt(row, 0);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE leaves SET status=? WHERE leave_id=?");
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Leave " + status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
