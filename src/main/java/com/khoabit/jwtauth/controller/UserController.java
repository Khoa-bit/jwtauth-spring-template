package com.khoabit.jwtauth.controller;

import com.khoabit.jwtauth.model.AuthUser;
import com.khoabit.jwtauth.service.UserService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<AuthUser>> getUsers() {
    return ResponseEntity.ok().body(userService.getAppUsers());
  }

  @PostMapping("/users")
  public ResponseEntity<AuthUser> saveUser(@RequestBody AuthUser authUser) {
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/save")
                .toUriString());
    return ResponseEntity.created(uri).body(userService.saveAuthUser(authUser));
  }
}
