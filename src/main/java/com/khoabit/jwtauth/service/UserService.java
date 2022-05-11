package com.khoabit.jwtauth.service;

import com.khoabit.jwtauth.model.AuthUser;
import com.khoabit.jwtauth.model.Role;
import com.khoabit.jwtauth.repo.UserRepo;
import com.khoabit.jwtauth.util.SecurityUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
  private final UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser authUser = userRepo.findByUsername(username);
    if (authUser == null) {
      log.error("Username {} not found", username);
      throw new UsernameNotFoundException(String.format("Username %s not found", username));
    } else {
      log.info("Username {} found", username);
    }

    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authUser
        .getRoles()
        .forEach(appRole -> authorities.add(new SimpleGrantedAuthority(appRole.toString())));
    return new org.springframework.security.core.userdetails.User(
        authUser.getUsername(), authUser.getPassword(), authorities);
  }

  public AuthUser saveAuthUser(AuthUser authUser) {
    log.info("Saving new AppUser {} to the database", authUser.getUsername());
    authUser.setPassword(SecurityUtil.encodePassword(authUser.getPassword()));
    return userRepo.save(authUser);
  }

  public void addRoleToUser(String username, Role role) {
    log.info("Adding new AppRole {} to AppUser {}", role, username);
    AuthUser authUser = userRepo.findByUsername(username);
    authUser.getRoles().add(role);
    userRepo.save(authUser);
  }

  public AuthUser getAppUser(String username) {
    log.info("Fetching AppUser {}", username);
    return userRepo.findByUsername(username);
  }

  public List<AuthUser> getAppUsers() {
    log.info("Fetching all AppUsers");
    return userRepo.findAll();
  }
}
