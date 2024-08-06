package org.example.lab5;

import org.example.lab5.entities.Student;
import org.example.lab5.repositories.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.util.Date;


@SpringBootApplication
public class Lab5Application {

    public static void main(String[] args) {

        SpringApplication.run(Lab5Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
//        return args -> {
//            studentRepository.save(new Student(null, "Jam", new Date(), false, 1.2, 20));
//            studentRepository.save(new Student(null, "Jen", new Date(), true, 3.2, 25));
//            studentRepository.save(new Student(null, "Jok", new Date(), false, 1.0, 30));
//            studentRepository.save(new Student(null, "Jos", new Date(), false, 4.2, 35));
//            studentRepository.findAll().forEach(p->{
//                System.out.println(p.getName());
//            });
//        };
//    }

}
