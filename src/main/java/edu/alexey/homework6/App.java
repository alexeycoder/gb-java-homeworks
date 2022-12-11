package edu.alexey.homework6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public static boolean askYesNo(Scanner inputScanner) {
		System.out.print("\nНовый поиск? (Y/n)");
		var answer = inputScanner.nextLine();

		if (answer.isBlank()) {
			return true;
		}

		answer = answer.toLowerCase();
		return answer.startsWith("y") || answer.startsWith("д");
	}

	public static void main(String[] args) {
		Infrastructure infrastructure = new Infrastructure();

		// System.out.println(infrastructure.getFilmInfo(1));
		// System.out.println(infrastructure.getFilmInfo(2));
		// System.out.println(infrastructure.getFilmInfo(3));
		// System.out.println(infrastructure.getFilmInfo(4));
		// System.out.println(infrastructure.getFilmInfo(5));

		System.out.println("\nФИЛЬМОТЕКА");
		System.out.println("\nВсе фильмы в базе данных:");
		for (String info : infrastructure.getAllFilmsInfo()) {
			System.out.println(info);
		}

		try (Scanner scanner = new Scanner(System.in)) {
			do {
				System.out.println("\nВведите строку для поиска фильма по названию:\n");
				String input = scanner.nextLine();
				if (input.isBlank()) {
					System.out.println("Введена пустая строка \u2014 поиск невозможен.");
					continue;
				}

				System.out.println();

				var infos = infrastructure.getFilmsInfoByTitle(input, false);
				if (infos.size() == 0) {
					System.out.println("Подходящих фильмов не найдено.");
					continue;
				}

				System.out.printf("Фильмов найдено: %d\n", infos.size());
				for (String info : infos) {
					System.out.println(info);
				}

			} while (askYesNo(scanner));
		}
	}
}

class Infrastructure {
	public Infrastructure() {
		init();
	}

	Db db;

	public Db getDb() {
		return db;
	}

	public List<String> getFilmsInfoByTitle(String title, boolean fullMatch) {
		if (db == null) {
			return List.of();
		}
		var films = db.getAllFilmsByTitle(title, fullMatch);
		return films.stream().map(f -> getFilmInfo(f.id)).toList();
	}

	public List<String> getAllFilmsInfo() {
		if (db == null) {
			return List.of();
		}
		var films = db.getAllFilms();
		return films.stream().map(f -> getFilmInfo(f.id)).toList();
	}

	public String getFilmInfo(int movieId) {
		Cinema c = db.films.get(movieId - 1);

		return String.format("%d. %s - %s - %s",
				c.id,
				c.name,
				db.genres.get(c.genre - 1).name,
				db.prod.get(c.filmProd - 1).titleName);
	}

	Db init() {
		db = new Db();
		Cinema c1 = new Cinema(1, "Тьма", 1, 1);
		Cinema c2 = new Cinema(2, "Свет", 1, 2);
		Cinema c3 = new Cinema(3, "Особенности охоты...", 3, 3);
		Cinema c4 = new Cinema(4, "Человек паук", 3, 4);
		Cinema c5 = new Cinema(5, "Особь", 1, 5);

		db.films.add(c1);
		db.films.add(c2);
		db.films.add(c3);
		db.films.add(c4);
		db.films.add(c5);

		db.genres.add(new Genre(1, "Ужасы"));
		db.genres.add(new Genre(2, "Драма"));
		db.genres.add(new Genre(3, "Комедия"));
		FilmProducerFactory pf = new FilmProducerFactory();
		db.addFilmProducer(pf.getFilmProducer("Ленфильм"));
		db.addFilmProducer(pf.getFilmProducer("Марвел"));
		db.addFilmProducer(pf.getFilmProducer("Мосфильм"));
		db.addFilmProducer(pf.getFilmProducer("ДиСи"));
		db.addFilmProducer(pf.getFilmProducer("ЭмДжиЭм"));

		return db;
	}

}

class Db {
	ArrayList<Cinema> films = new ArrayList<>();
	ArrayList<FilmProducer> prod = new ArrayList<>();
	ArrayList<Genre> genres = new ArrayList<>();

	public List<Cinema> getAllFilmsByTitle(String title, boolean fullMatch) {
		if (title == null || title.isEmpty()) {
			return List.of();
		}

		if (fullMatch) {
			return films.stream().filter(t -> t != null && title.equalsIgnoreCase(t.name)).toList();
		} else {
			String ttl = title.toLowerCase();
			return films.stream().filter(t -> t != null && t.name != null && t.name.toLowerCase().contains(ttl))
					.toList();
		}
	}

	public List<Cinema> getAllFilms() {
		return List.copyOf(films);
	}

	public void addFilmProducer(FilmProducer producer) {
		prod.add(producer);
	}
}

class Cinema {
	int id;
	int filmProd;
	String name;
	int genre;

	public Cinema(int id, String name, int genre, int filmProd) {
		this.id = id;
		this.filmProd = filmProd;
		this.name = name;
		this.genre = genre;
	}
}

class FilmProducer {
	int id;
	String titleName;
}

class Genre {
	int id;
	String name;

	public Genre(int id, String name) {
		this.id = id;
		this.name = name;
	}
}

class FilmProducerFactory {
	int count = 1;

	public FilmProducer getFilmProducer(String name) {
		FilmProducer fp = new FilmProducer();
		fp.id = count++;
		fp.titleName = name;
		return fp;
	}
}