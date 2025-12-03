package com.example.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
		// id : auto_increment
		// isbn(unique) : B10102
		// title : 개미
		// prive : 128000
		// author : 베르나르 베르베르
	}

}
