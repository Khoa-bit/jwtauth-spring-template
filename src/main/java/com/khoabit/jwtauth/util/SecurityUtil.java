package com.khoabit.jwtauth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {
  public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public static String encodePassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
