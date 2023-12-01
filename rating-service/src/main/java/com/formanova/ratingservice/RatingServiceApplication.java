package com.formanova.ratingservice;

import com.formanova.ratingservice.persistence.entity.ReviewEntity;
import com.formanova.ratingservice.persistence.repository.ReviewRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(ReviewRepository repo) {
		return args -> {
			ReviewEntity reviewEntity = new ReviewEntity(
					null, null, null, 2, "a", "b", 1, "c", (byte) 5, "d", "e");
			repo.save(reviewEntity).subscribe();
		};
	}

}
