package levelUp_backEnd.levelup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.levelup.backend") // escanea solo el código externo (ajusta si quieres lo contrario)
@EnableJpaRepositories(basePackages = "com.levelup.backend.repo")
@EntityScan(basePackages = "com.levelup.backend.model")
public class LevelupBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(LevelupBackendApplication.class, args);
    }
}
