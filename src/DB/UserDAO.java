package DB;

import java.sql.*;
import java.util.*;
import Admin.Mentee;

public class UserDAO {

    public static List<Mentee> getAllStudents() {

        List<Mentee> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                 con.prepareStatement("SELECT id, name FROM users");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Mentee(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
