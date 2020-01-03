package com.skilldistillery.filmquery.app;

import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int mainMenuChoice = 0;
		int filmIdChoice = 0;
		String filmKeywordChoice = "";

		while (true) {
			System.out.println("Select an option:");
			System.out.println("1: Look up film by ID");
			System.out.println("2: Look up film by search keyword");
			System.out.println("3: Exit");
			mainMenuChoice = input.nextInt();
			if (mainMenuChoice == 1) {
				System.out.print("Enter the ID of your film: ");
				filmIdChoice = input.nextInt();
				Film film = db.findFilmById(filmIdChoice);
				if (film == null) {
					System.out.println("No film with ID: " + filmIdChoice);
				} else {
					System.out.println("Title: " + film.getTitle() + 
							"\nRelease Year: " + film.getReleaseYear() + 
							"\nRating: " + film.getRating() + 
							"\nDescription: " + film.getDescription());
				}

			}
			if (mainMenuChoice == 2) {
				System.out.print("Enter your search keyword: ");
				filmKeywordChoice = input.next();
			}
			if (mainMenuChoice == 3) {
				System.out.println("Goodbye.");
				break;
			}

		}
	}

}
