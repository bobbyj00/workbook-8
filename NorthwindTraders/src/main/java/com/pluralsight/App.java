package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "BobbyJ1227";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.printf("%-5s %-30s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
                System.out.println("-------------------------------------------------------------");

                while (rs.next()) {
                    int id = rs.getInt("ProductID");
                    String name = rs.getString("ProductName");
                    double price = rs.getDouble("UnitPrice");
                    int stock = rs.getInt("UnitsInStock");

                    System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
                }

            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}