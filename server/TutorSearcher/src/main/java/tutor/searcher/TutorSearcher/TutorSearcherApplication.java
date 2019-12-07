package tutor.searcher.TutorSearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TutorSearcherApplication {

	private String profileImagesPath = "res/profile-images/";

	public static void main(String[] args) {
		SpringApplication.run(TutorSearcherApplication.class, args);
		System.out.println("Application running");
	}
}
