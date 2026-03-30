package com.example;

import com.example.dao.ArticleRepository;
import com.example.dao.CategoryRepository;
import com.example.entities.Article;
import com.example.entities.Category;
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

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
//        articleRepository.save(new Article("Apple", "Iphone 15", "L'iphone 15,totalement révolutionnaire !", 599.99));
//        articleRepository.save(new Article("Apple", "Iphone 19", "L'iphone 19, une véritable révolution !", 899.99));
//        articleRepository.save(new Article("Apple", "Ipad 12", "L'ipad 12, la révolution est en marche !", 999.99));
//        articleRepository.save(new Article("Samsung", "Galaxy S25", "Moins révolutionnaire que l'iphone !", 899.99));
//        articleRepository.save(new Article("Samsung", "Galaxy Tab 6", "La tablette du futur !", 899.99));
//        categoryRepository.save(new Category("Smartphone"));
//        categoryRepository.save(new Category("Tablette"));
//        categoryRepository.save(new Category("PC"));
//        categoryRepository.save(new Category("Jeux Vidéo"));

    }
}
