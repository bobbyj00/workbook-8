package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class SakilaApp {
    public static void main(String[] args) {
        try (Connection conn = Database.getDataSource().getConnection();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the last name of an actor you like: ");
            String lastName = scanner.nextLine();

            String actorSql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(actorSql)) {
                stmt.setString(1, lastName);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("\nMatching actors:");
                        do {
                            int id = rs.getInt("actor_id");
                            String firstName = rs.getString("first_name");
                            String lName = rs.getString("last_name");
                            System.out.printf("%d: %s %s%n", id, firstName, lName);
                        } while (rs.next());
                    } else {
                        System.out.println("No actors found with that last name.");
                        return;
                    }
                }
            }

            System.out.print("\nEnter the FIRST name of the actor: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter the LAST name of the actor: ");
            lastName = scanner.nextLine();

            String filmSql = """
                    SELECT f.title
                    FROM film f
                    JOIN film_actor fa ON f.film_id = fa.film_id
                    JOIN actor a ON fa.actor_id = a.actor_id
                    WHERE a.first_name = ? AND a.last_name = ?
                    """;

            try (PreparedStatement stmt = conn.prepareStatement(filmSql)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("\nFilms featuring " + firstName + " " + lastName + ":");
                        do {
                            String title = rs.getString("title");
                            System.out.println("- " + title);
                        } while (rs.next());
                    } else {
                        System.out.println("No films found for that actor.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
