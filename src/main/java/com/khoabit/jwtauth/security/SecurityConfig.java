package com.khoabit.jwtauth.security;

import com.khoabit.jwtauth.filter.CustomAuthenticationFilter;
import com.khoabit.jwtauth.filter.CustomAuthorizationFilter;
import com.khoabit.jwtauth.util.JWTUtil;
import com.khoabit.jwtauth.util.SecurityUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String AUTH_LOGIN_PATTERN = "/auth/login*";
  private static final String AUTH_TOKEN_REFRESH_PATTERN = "/auth/token/refresh*";
  public static final HashMap<HttpMethod, String[]> PERMIT_PATTERNS =
      new HashMap<>(
          Map.of(
              HttpMethod.POST,
              new String[] {AUTH_TOKEN_REFRESH_PATTERN, AUTH_LOGIN_PATTERN, "/api/users*"}));
  private final UserDetailsService userDetailsService;
  private final JWTUtil jwtUtil;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(SecurityUtil.passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter =
        new CustomAuthenticationFilter(this.authenticationManagerBean(), jwtUtil);
    customAuthenticationFilter.setFilterProcessesUrl("/auth/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, PERMIT_PATTERNS.get(HttpMethod.POST))
        .permitAll();
    //    http.authorizeRequests().antMatchers(HttpMethod.POST,
    // "/api/user/save/**").hasAuthority("ROLE_ADMIN");
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(
        new CustomAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
  }
}
