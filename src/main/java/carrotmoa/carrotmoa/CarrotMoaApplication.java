package carrotmoa.carrotmoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//TODO : 1. DB 연결후 기존 어노테이션에서 주석된 어노테이션으로 변경하기
@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CarrotMoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotMoaApplication.class, args);
    }

}
