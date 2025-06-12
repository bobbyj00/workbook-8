package com.pluralsight;

import com.pluralsight.data.DataManager;
import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import java.util.List;
import java.util.Scanner;

public class SakilaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager();

        System.out.print("Enter the last name of an actor: ");
        String lastName = scanner.nextLine();

        List<Actor> actors = dataManager.searchActorsByLastName(lastName);

        if (actors.isEmpty()) {
            System.out.println("No actors found with that last name.");
            return;
        }

        System.out.println("\nMatching actors:");
        for (Actor actor : actors) {
            System.out.println(actor);
        }

        System.out.print("\nEnter the ID of the actor to see their movies: ");
        int actorId = scanner.nextInt();

        List<Film> films = dataManager.getFilmsByActorId(actorId);

        if (films.isEmpty()) {
            System.out.println("No films found for this actor.");
            return;
        }

        System.out.println("\nMovies with actor ID " + actorId + ":");
        for (Film film : films) {
            System.out.println(film);
        }
    }
}