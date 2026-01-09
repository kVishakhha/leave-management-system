package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import DB.DBConnection;

public class StudentLeaveHistory {

    public StudentLeaveHistory(String sapId) {

        JFrame frame = new JFrame("Leave History: " + sapId);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"From", "To", "Reason", "Status"}, 0);

        JTable table = new JTable(model);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM leaves WHERE sap_id=?");
            ps.setString(1, sapId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getDate("from_date"),
                        rs.getDate("to_date"),
                        rs.getString("reason"),
                        rs.getString("status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }
}
