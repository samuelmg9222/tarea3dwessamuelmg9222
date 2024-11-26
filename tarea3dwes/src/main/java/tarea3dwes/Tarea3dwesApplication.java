package tarea3dwes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Tarea3dwesApplication {

	@Bean
	public Principal applicationStartupRunner() {
		return new Principal();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(Tarea3dwesApplication.class, args);
	}
}
