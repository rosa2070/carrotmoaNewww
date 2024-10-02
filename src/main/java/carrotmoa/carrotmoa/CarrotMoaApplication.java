package carrotmoa.carrotmoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CarrotMoaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarrotMoaApplication.class, args);
    }
}
