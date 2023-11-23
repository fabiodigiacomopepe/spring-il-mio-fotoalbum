package org.lessons.java.springilmiofotoalbum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringIlMioFotoalbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
    }

}
