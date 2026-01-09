# 📚 Leave Management System (Java Swing + MySQL)

A **desktop-based Leave Management System** built using **Java Swing**, **JDBC**, and **MySQL**.  
Designed for academic institutions to manage student leave requests efficiently with **role-based access** for Students and Admins.

---

## 🚀 Features

### 👩‍🎓 Student Module
- Student signup and login
- Apply for leave (date range + reason)
- View leave status (Pending / Approved / Rejected)
- View leave history and statistics
- Secure session handling

### 👨‍🏫 Admin Module
- Admin login
- View all pending leave requests
- Approve or reject leave requests
- View registered students (mentees)
- View approved and rejected leave history

---

## 🛠️ Tech Stack

| Layer | Technology |
|------|------------|
| Language | Java (JDK 21) |
| UI | Java Swing |
| Database | MySQL |
| Connectivity | JDBC |
| IDE | Eclipse |
| Version Control | Git & GitHub |

---

## 📂 Project Structure

LeaveManagementSystem/
│
├── src/
│ ├── Admin/ # Admin dashboard, login, approval logic
│ ├── Student/ # Student login, dashboard, leave apply & stats
│ ├── DB/ # DAO classes, DB connection, DB initializer
│ ├── Utils/ # Session handling and validations
│ ├── images/ # UI images and icons
│ └── main/ # Application entry point
│
├── lib/
│ └── mysql-connector-j-9.3.0.jar
│
├── .gitignore
└── README.md

pgsql
Copy code

---

## 🗄️ Database Schema

### 👤 Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(100)
);
📄 Leave Requests Table
sql
Copy code
CREATE TABLE leave_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    from_date DATE,
    to_date DATE,
    reason TEXT,
    status VARCHAR(20)
);
``` 
### ⚙️ How to Run the Project
✅ Prerequisites
Java JDK 17 or above (JDK 21 recommended)

MySQL Server

Eclipse IDE

MySQL Connector/J
(Already included in the lib/ folder)

### ▶️ Steps to Run
Clone the repository

bash
Copy code
git clone https://github.com/meetalidangi/leave-management-system.git
Open Eclipse IDE

Import the project

mathematica
Copy code
File → Import → Existing Projects into Workspace
Add MySQL Connector to Build Path

sql
Copy code
Right click project → Build Path → Add External JARs
→ Select lib/mysql-connector-j-9.3.0.jar
Start MySQL Server

Run the application

java
Copy code
Admin.MainPage
### 🔐 Application Flow
Launch the application

Select role (Student / Admin)

Login or Signup

Perform role-based actions

Logout redirects back to role selection page

### 📌 Scalability & Design
Implements DAO (Data Access Object) pattern

JDBC-based database abstraction

Modular and clean architecture

Easily extendable to:

Spring Boot backend

REST / Web-based application

Cloud-hosted databases (AWS RDS, Azure SQL)

### 🤝 Contributors
Meetali Dangi

Vishakha Khetan

### 📜 License
This project is developed for educational and academic purposes.

### 🚀 Future Enhancements
Password hashing & encryption

Role-based authorization

Email notifications

Attendance management integration

Web version using Spring Boot
