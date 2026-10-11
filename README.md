# Leave Management System

A Java Swing desktop application for managing student leave requests. Students can register, sign in, submit leave applications, and review their status. Admin/teacher screens support reviewing requests and viewing student leave history.

## Features

- Student registration and login
- Leave requests with start date, end date, and reason
- Pending, approved, and rejected request statuses
- Admin/teacher views for reviewing requests and student history
- JDBC access to a MySQL database
- DAO classes for database operations

## Tech stack

- Java (use JDK 17 or newer; the source uses Java text blocks)
- Java Swing
- MySQL
- JDBC / MySQL Connector/J
- Eclipse IDE (recommended)

## Project structure

~~~text
src/
  Admin/   Admin and teacher screens, request models
  DB/      Database connection, initializer, and DAO classes
  Student/ Student screens and leave application flow
  Utils/   Session utilities
lib/
  mysql-connector-j-9.3.0.jar
~~~

## Setup

### 1. Install prerequisites

Install JDK 17 or newer, MySQL Server, and Eclipse IDE.

### 2. Clone the correct repository

~~~bash
git clone https://github.com/kVishakhha/leave-management-system.git
cd leave-management-system
~~~

### 3. Configure MySQL

The current Java database configuration connects to a local MySQL server at localhost:3306, uses the database leave_management, and assumes the local MySQL account is root with an empty password. These values are hardcoded in the source, so configure your local MySQL instance accordingly or update **DBConnection.java** and **DatabaseInitializer.java** to use your own local credentials before running the app.

The initializer defines the following tables:

~~~sql
CREATE DATABASE IF NOT EXISTS leave_management;
USE leave_management;

CREATE TABLE IF NOT EXISTS users (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  password VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS leave_requests (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  from_date DATE,
  to_date DATE,
  reason VARCHAR(255),
  status VARCHAR(20)
);
~~~

**Admin login setup:** AdminLogin.java queries an admins table with name and password columns, but DatabaseInitializer.java does not create that table. The admin login will require this schema to be added and an admin account to be configured before that flow can work.

### 4. Import into Eclipse

1. Open Eclipse.
2. Select **File → Import → General → Existing Projects into Workspace** (or create a Java project and import the source folder if Eclipse does not detect project metadata).
3. Add the bundled MySQL Connector/J JAR to the build path: right-click the project → **Build Path → Configure Build Path → Libraries → Classpath → Add JARs**, then select **lib/mysql-connector-j-9.3.0.jar**.
4. Confirm the project resources include the image files referenced under **/images/**.
5. Ensure MySQL is running and the database tables are created.
6. Run **src/Admin/MainPage.java** as a Java application.

If Eclipse does not recognize the source folder automatically, mark **src** as a source folder and ensure the **lib** JAR is on the classpath.

## Application flow

1. Launch the main window.
2. Select the student or admin/teacher flow.
3. Sign in or register as a student.
4. Submit a leave request or review requests from the admin/teacher screens.
5. Return to the role-selection screen when finished.

## Security and limitations

This repository is an academic prototype and should not be used with real student records without security improvements.

- **Plaintext passwords:** Current student and admin login queries compare stored passwords directly. Passwords are not hashed. Add a password-hashing mechanism and migrate existing records before real use.
- **Hardcoded database credentials:** Database username/password and connection settings are embedded in Java source. Move configuration to environment variables or a local configuration file that is excluded from version control.
- **Database transport:** The JDBC URL sets useSSL=false. Use TLS with certificate verification for remote or production databases.
- **Authorization:** Review and test role checks and session handling before using sensitive records.
- **Validation and testing:** Validate date ranges and inputs, and add automated tests for login, request creation, and approval/rejection workflows.

The repository currently has no committed automated test suite or standard build file (such as Maven/Gradle). Run and verify the application manually in the configured local environment.

## Contributors

- Meetali Dangi
- Vishakha Khetan

---
Created for educational use.
