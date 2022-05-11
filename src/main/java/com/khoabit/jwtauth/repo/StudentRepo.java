package com.khoabit.jwtauth.repo;

import com.khoabit.jwtauth.model.Student;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepo extends MongoRepository<Student, String> {

  List<Student> findByFirstname(String firstname);

  List<Student> findByLastname(String lastname);
}
