package Admin;

import javax.swing.*;
import java.sql.*;
import DB.DBConnection;
import Utils.Session;

public class MenteeList {

    public MenteeList() {

        JFrame frame = new JFrame("My Mentees");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT sap_id FROM students WHERE mentor_id=?");
            ps.setString(1, Session.currentUserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addElement(rs.getString("sap_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                new StudentLeaveHistory(list.getSelectedValue());
            }
        });

        frame.add(new JScrollPane(list));
        frame.setVisible(true);
    }
}
