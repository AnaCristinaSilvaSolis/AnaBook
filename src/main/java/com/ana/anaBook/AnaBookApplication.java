package com.ana.anaBook;


import com.ana.anaBook.Principal.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AnaBookApplication implements CommandLineRunner {
	@Autowired
	private Menu menu;

	public static void main(String[] args) {
		SpringApplication.run(AnaBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.mostrarMenu();
	}
}
