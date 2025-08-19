# JDBC & Database Integration

This project demonstrates how to connect a Java application to a MySQL database and perform basic CRUD (Create, Read, Update, Delete) operations using JDBC.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Eclipse IDE
- MySQL Server
- MySQL JDBC Driver (Connector/J)

## Setup Instructions

1. Clone or download this project
2. Import the project into Eclipse
3. Set up MySQL database:
   - Start MySQL server
   - Create a database named `jdbc_demo`
   - Create a table with:
     ```sql
     CREATE TABLE users (
         id INT PRIMARY KEY AUTO_INCREMENT,
         name VARCHAR(100) NOT NULL,
         email VARCHAR(100) NOT NULL UNIQUE
     );
     ```
4. Download MySQL JDBC driver from https://dev.mysql.com/downloads/connector/j/
5. Add the JDBC driver JAR to your project's build path
6. Update database connection credentials in `JDBCDemo.java`:
   ```java
   private static final String USER = "your_mysql_username";
   private static final String PASSWORD = "your_mysql_password";