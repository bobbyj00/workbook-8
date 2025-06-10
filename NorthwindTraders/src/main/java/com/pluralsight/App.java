package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "BobbyJ1227";

        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection(url, username, password);

            int option;
            do {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        displayAllProducts(conn);
                        break;
                    case 2:
                        displayAllCustomers(conn);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } while (option != 0);

        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private static void displayAllProducts(Connection conn) {
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

        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }
    }

    private static void displayAllCustomers(Connection conn) {
        String sql = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-25s %-30s %-20s %-15s %-15s%n",
                    "Contact Name", "Company Name", "City", "Country", "Phone");
            System.out.println("---------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String contactName = rs.getString("ContactName");
                String companyName = rs.getString("CompanyName");
                String city = rs.getString("City");
                String country = rs.getString("Country");
                String phone = rs.getString("Phone");

                System.out.printf("%-25s %-30s %-20s %-15s %-15s%n",
                        contactName, companyName, city, country, phone);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
    }
}