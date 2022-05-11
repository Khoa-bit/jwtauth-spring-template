package com.khoabit.jwtauth.filter;

import static java.util.Arrays.stream;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.khoabit.jwtauth.security.SecurityConfig;
import com.khoabit.jwtauth.util.JWTUtil;
import com.khoabit.jwtauth.util.ResponseUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
  private final JWTUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    String requestURI = request.getServletPath();

    String[] patterns = SecurityConfig.PERMIT_PATTERNS.get(HttpMethod.valueOf(request.getMethod()));
    patterns = (patterns != null) ? patterns : new String[] {};
    for (String pattern : patterns) {
      if (antPathMatcher.match(pattern, requestURI)) {
        filterChain.doFilter(request, response);
        return;
      }
    }

    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      ResponseUtil.sendError(
          response,
          HttpServletResponse.SC_UNAUTHORIZED,
          "UNAUTHORIZED",
          "Missing Authorization header");
      return;
    }

    try {
      String access_token = authorizationHeader.substring("Bearer ".length());
      DecodedJWT decodedJWT = jwtUtil.decodeJWT(access_token);
      if (jwtUtil.isRefreshToken(decodedJWT.getClaims())) {
        ResponseUtil.sendError(
            response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED", "Invalid access token");
        return;
      }
      String username = decodedJWT.getSubject();
      String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      stream(roles)
          .forEach(
              role -> {
                authorities.add(new SimpleGrantedAuthority(role));
              });

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(username, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      ResponseUtil.sendError(
          response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED", e.getMessage());
    }
  }
}
