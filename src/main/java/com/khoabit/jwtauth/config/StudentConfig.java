package com.khoabit.jwtauth.config;

import com.khoabit.jwtauth.model.AuthUser;
import com.khoabit.jwtauth.model.Role;
import com.khoabit.jwtauth.model.Student;
import com.khoabit.jwtauth.repo.StudentRepo;
import com.khoabit.jwtauth.repo.UserRepo;
import com.khoabit.jwtauth.service.UserService;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

  @Bean
  CommandLineRunner commandLineRunner(
      StudentRepo studentRepo, UserRepo userRepo, UserService userService) {
    return args -> {
      userRepo.deleteAll();
      studentRepo.deleteAll();

      userService.saveAuthUser(
          new AuthUser(
              null, "alicesmith", "321", new ArrayList<>(), new Student("Alice", "Smith")));
      userService.saveAuthUser(
          new AuthUser(null, "bobsmith", "123", new ArrayList<>(), new Student("Bob", "Smith")));
      userService.addRoleToUser("alicesmith", Role.ROLE_STUDENT);
      userService.addRoleToUser("bobsmith", Role.ROLE_STUDENT);
      System.out.println(
          "==================================================================================");

      //      // fetch all students
      //      System.out.println("Students found with findAll():");
      //      System.out.println("-------------------------------");
      //      for (Student student : studentRepo.findAll()) {
      //        System.out.println(student);
      //      }
      //      System.out.println();
      //
      //      // fetch an individual student
      //      System.out.println("Student found with findByUsername('bobsmith'):");
      //      System.out.println("--------------------------------");
      //      System.out.println(userService.loadUserByUsername("bobsmith"));
      //
      //      System.out.println("Student found with findByFirstName('Alice'):");
      //      System.out.println("--------------------------------");
      //      for (Student student : studentRepo.findByFirstname("Alice")) {
      //        System.out.println(student);
      //      }
      //
      //      System.out.println("Students found with findByLastName('Smith'):");
      //      System.out.println("--------------------------------");
      //      for (Student student : studentRepo.findByLastname("Smith")) {
      //        System.out.println(student);
      //      }
    };
  }
}
