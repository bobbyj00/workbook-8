package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) {

        try {
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "BobbyJ1227");

            Statement statement = connection.createStatement();

            String query = "SELECT  ProductName FROM Products;";

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                String product = results.getString(1);
                System.out.println(product);
            }
            
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}