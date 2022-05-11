package com.khoabit.jwtauth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.khoabit.jwtauth.util.JWTUtil;
import com.khoabit.jwtauth.util.ResponseUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class JWTController {
  private final JWTUtil jwtUtil;

  @PostMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refresh_token = authorizationHeader.substring("Bearer ".length());
        DecodedJWT decodedJWT = jwtUtil.decodeJWT(refresh_token);
        if (!jwtUtil.isRefreshToken(decodedJWT.getClaims())) {
          ResponseUtil.sendError(
              response,
              HttpServletResponse.SC_UNAUTHORIZED,
              "UNAUTHORIZED",
              "Invalid refresh token");
          return;
        }
        String username = decodedJWT.getSubject();
        String access_token =
            jwtUtil.generateAccessToken(
                username, decodedJWT.getClaims(), request.getRequestURL().toString());

        ResponseUtil.sendTokens(response, access_token, refresh_token);
      } catch (Exception e) {
        ResponseUtil.sendError(
            response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED", e.getMessage());
      }
    } else {
      ResponseUtil.sendError(
          response,
          HttpServletResponse.SC_UNAUTHORIZED,
          "UNAUTHORIZED",
          "Refresh token is missing");
    }
  }
}
