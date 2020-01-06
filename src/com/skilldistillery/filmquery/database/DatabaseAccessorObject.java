package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private String user = "student";
	private String pw = "student";
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sql = "select film.id as id, title, description, release_year, language_id,"
				+ " language.name as film_language, rental_duration, rental_rate, length, replacement_cost,"
				+ " rating, special_features, category.name as film_category"
				+ " from film"
				+ " join language on film.language_id = language.id"
				+ " join film_category on film.id = film_category.film_id"
				+ " join category on film_category.category_id = category.id"
				+ " where film.id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getString("film_language"),
						rs.getInt("rental_duration"), rs.getDouble("rental_rate"), rs.getInt("length"),
						rs.getDouble("replacement_cost"), rs.getString("rating"), rs.getString("special_features"),
						findActorsByFilmId(rs.getInt("id")), rs.getString("film_category"));
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();;
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "select * from actor where id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, actorId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.err.println(e);
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actorList = new ArrayList<Actor>();
		String sql = "select * from film_actor join actor on film_actor.actor_id = actor.id where film_id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				actorList.add(new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
			}
			
			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.err.println(e);
		}

		return actorList;
	}
	
	@Override
	public List<Film> findFilmsBySearch(String inputText) {
		List<Film> filmList = new ArrayList<>();
		
		String sql = "select film.id as id, title, description, release_year, language_id,"
				+ " language.name as film_language, rental_duration, rental_rate, length, replacement_cost,"
				+ " rating, special_features, category.name as film_category"
				+ " from film"
				+ " join language on film.language_id = language.id"
				+ " join film_category on film.id = film_category.film_id"
				+ " join category on film_category.category_id = category.id"
				+ " where title like ? or description like ?";
		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + inputText + "%");
			pstmt.setString(2, "%" + inputText + "%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				filmList.add(new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getString("film_language"), // note name is the name of the language
						rs.getInt("rental_duration"), rs.getDouble("rental_rate"), rs.getInt("length"),
						rs.getDouble("replacement_cost"), rs.getString("rating"), rs.getString("special_features"),
						findActorsByFilmId(rs.getInt("id")), rs.getString("film_category")));
			}
			
			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.err.println(e);
		}

		return filmList;
	}

}
