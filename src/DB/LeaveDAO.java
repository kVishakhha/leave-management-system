package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Admin.Request;
import Student.LeaveRecord;

public class LeaveDAO {

    // ================= ADMIN : GET REQUESTS BY STATUS =================
    public static List<Request> getRequestsByStatus(String status) {

        List<Request> list = new ArrayList<>();

        String sql = """
            SELECT id, student_id, from_date, to_date, reason, status
            FROM leave_requests
            WHERE status = ?
            ORDER BY from_date DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toLowerCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Request(
                        rs.getInt("id"),
                        rs.getString("student_id"),
                        rs.getDate("from_date").toString(),
                        rs.getDate("to_date").toString(),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
 // ================= ADMIN : GET PROCESSED REQUESTS =================
    public static List<Request> getProcessedRequests() {

        List<Request> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                SELECT id, student_id, from_date, to_date, reason, status
                FROM leave_requests
                WHERE status IN ('approved', 'rejected')
                ORDER BY from_date DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Request(
                        rs.getInt("id"),
                        rs.getString("student_id"),
                        rs.getDate("from_date").toString(),
                        rs.getDate("to_date").toString(),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================= ADMIN : UPDATE STATUS =================
    public static void updateStatus(int leaveId, String status) {

        String sql = "UPDATE leave_requests SET status=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toLowerCase());
            ps.setInt(2, leaveId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= STUDENT : GET LEAVES =================
    public static List<LeaveRecord> getLeavesByStudent(String studentId) {

        List<LeaveRecord> list = new ArrayList<>();

        String sql = """
            SELECT from_date, to_date, status
            FROM leave_requests
            WHERE student_id = ?
            ORDER BY from_date DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new LeaveRecord(
                        rs.getDate("from_date").toString(),
                        rs.getDate("to_date").toString(),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
