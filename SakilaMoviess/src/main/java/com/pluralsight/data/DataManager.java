package com.pluralsight.data;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import com.pluralsight.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public List<Actor> searchActorsByLastName(String lastName) {
        List<Actor> actors = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actor actor = new Actor(
                            rs.getInt("actor_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                    actors.add(actor);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching actors: " + e.getMessage());
        }

        return actors;
    }

    public List<Film> getFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM actor\n" +
                "WHERE last_name = ?";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, actorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("release_year"),
                            rs.getInt("length")
                    );
                    films.add(film);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching films: " + e.getMessage());
        }

        return films;
    }
}