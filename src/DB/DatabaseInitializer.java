package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SERVER_URL =
        "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/leave_management?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void init() {

        try {
            // 1️⃣ Create database
            try (Connection con = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
                 Statement st = con.createStatement()) {

                st.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS leave_management"
                );
            }

            // 2️⃣ Create tables
            try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 Statement st = con.createStatement()) {

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        password VARCHAR(100)
                    )
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS leave_requests (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        student_id INT,
                        from_date DATE,
                        to_date DATE,
                        reason VARCHAR(255),
                        status VARCHAR(20)
                    )
                """);
            }

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
