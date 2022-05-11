package com.khoabit.jwtauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
public class ResponseUtil {

  public static void sendError(
      HttpServletResponse response, int statusCode, String error, String message)
      throws IOException {
    log.error("Error {}: {}", error, message);
    response.setStatus(statusCode);

    HashMap<String, String> errorMap = new HashMap<>();
    errorMap.put("error", error);
    errorMap.put("message", message);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
  }

  public static void sendTokens(
      HttpServletResponse response, String access_token, String refresh_token) throws IOException {
    HashMap<String, String> tokens = new HashMap<>();
    tokens.put("access_token", access_token);
    tokens.put("refresh_token", refresh_token);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }
}
