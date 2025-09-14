package cl.proyectum.product.neg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MsProyectumProductNegApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProyectumProductNegApplication.class, args);
    }

}
