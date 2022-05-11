package com.khoabit.jwtauth.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthUser {
  @Id private String id;
  private String username;
  private String password;
  private ArrayList<Role> roles;
  private Student student;
}
