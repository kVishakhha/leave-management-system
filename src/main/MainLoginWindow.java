package main;
import DB.DBConnection;
import java.sql.Connection;

public class MainLoginWindow {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("✅ Connection is working!");
        } else {
            System.out.println("❌ Connection failed.");
        }
    }
}
