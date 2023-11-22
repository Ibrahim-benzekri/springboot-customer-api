package com.mycourse;


import com.mycourse.Costumer.Custumer;
import com.mycourse.Costumer.CustumerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class,args);
    }

@Bean
CommandLineRunner runner(CustumerRepository custumerRepository){
        return args -> {

            //custumerRepository.save();
        };
}

}
