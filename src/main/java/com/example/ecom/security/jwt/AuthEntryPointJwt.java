package com.example.ecom.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This class handles unauthorized access attempts to protected resources.
// It implements the AuthenticationEntryPoint interface from Spring Security.
// The class is annotated with @Component, making it a Spring bean that can be injected into other components.
//This is a handler that catches any unauthorized request (e.g., token is missing or invalid) and returns a clean JSON error response.
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

  //creates a logger instance for this class
  //This logger will be used to log for debugging and monitoring.
  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  //his method is called automatically whenever a user who is not authenticated tries to access a secured endpoint.
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    //log the error message
    logger.error("Unauthorized error: {}", authException.getMessage());

    //set the response type and the status
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    //build the json response body
    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", authException.getMessage());
    body.put("path", request.getServletPath());

    //use jackson to convert the map to a json string and write it to the response output stream
    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }

}
