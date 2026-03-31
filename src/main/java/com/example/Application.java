package com.example;

import com.example.dao.AppUserRepository;
import com.example.dao.ArticleRepository;
import com.example.dao.CategoryRepository;
import com.example.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AppUserRepository appUserRepository;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        //appUserRepository.save(new AppUser("user1", "password123456", "USER"));
    }
}
