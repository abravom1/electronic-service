package ec.com.ups.electronic;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ec.com.ups.electronic")
@OpenAPIDefinition(info = @Info(title = "${info.project.title}", version = "${info.project.version}", description = "${info.project.description}"))
public class ElectronicApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElectronicApplication.class, args);
	}

}
