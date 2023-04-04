package edu.tcu.cs.superfrogscheduler;

import edu.tcu.cs.superfrogscheduler.system.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperfrogSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperfrogSchedulerApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
