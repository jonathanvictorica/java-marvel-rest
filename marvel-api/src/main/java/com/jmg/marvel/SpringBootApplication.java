package com.jmg.marvel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class SpringBootApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
