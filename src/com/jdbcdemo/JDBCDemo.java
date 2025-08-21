package com.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCDemo {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/db_namw";
    private static final String USER = "mysql_username"; // Replace with your MySQL username
    private static final String PASSWORD = "mysql_password"; // Replace with your MySQL password
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
            
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== User Management System ===");
                System.out.println("1. Add User");
                System.out.println("2. View All Users");
                System.out.println("3. Update User");
                System.out.println("4. Delete User");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        addUser(connection, scanner);
                        break;
                    case 2:
                        viewAllUsers(connection);
                        break;
                    case 3:
                        updateUser(connection, scanner);
                        break;
                    case 4:
                        deleteUser(connection, scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            
            // Close connection
            connection.close();
            System.out.println("Disconnected from the database.");
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection error.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    // Create (Insert) operation
    private static void addUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User added successfully!");
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
    
    // Read (Select) operation
    private static void viewAllUsers(Connection connection) throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            System.out.println("\n=== User List ===");
            System.out.println("ID\tName\tEmail");
            System.out.println("-----------------------------");
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                
                System.out.println(id + "\t" + name + "\t" + email);
            }
            
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No users found.");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
    
    // Update operation
    private static void updateUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter user ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setInt(3, id);
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with ID: " + id);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
    
    // Delete operation
    private static void deleteUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter user ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with ID: " + id);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
