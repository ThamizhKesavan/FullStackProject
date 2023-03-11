package fullStackProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootConfiguration,@EnableAutoConfiguration,@ComponentScan
public class FullStackProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullStackProjectApplication.class, args);
		System.out.println("Server started");
	}

}
