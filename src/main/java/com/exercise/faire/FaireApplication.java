package com.exercise.faire;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaireApplication  implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FaireApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        boolean constainsApiKey = args.containsOption("api.key");
        
        if(!constainsApiKey) {
            throw new RuntimeException("You must to provide the api key argument!");
        }        
    }
}
