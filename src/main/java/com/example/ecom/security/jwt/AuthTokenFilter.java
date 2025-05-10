package com.example.ecom.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ecom.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private UserDetailsServiceImpl userDetailsService;

  public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
    this.jwtUtils = jwtUtils;
  }

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  // JWT validation happens on every request that passes through the filter.
  // Checks if there's a token in the Authorization header
  // Validates the token
  // If valid, sets the authentication in the Spring Security context
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Represents the user’s authenticated identity. Includes the roles/authorities
        // from the DB
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        // Adds extra info like IP address and session ID (Spring uses this internally)
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // makes Spring Security recognize the user as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    // pass it to the next filter or controller
    filterChain.doFilter(request, response);
  }

  // extract jwt from the headers
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }
}