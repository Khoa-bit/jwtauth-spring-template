package com.khoabit.jwtauth.repo;

import com.khoabit.jwtauth.model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<AuthUser, String> {
  AuthUser findByUsername(String username);
}
