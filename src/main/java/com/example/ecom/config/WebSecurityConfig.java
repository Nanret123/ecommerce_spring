package com.example.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ecom.security.jwt.AuthTokenFilter;
import com.example.ecom.security.services.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

//Connects everything together. Sets security rules, providers, and filters.
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final AuthenticationEntryPoint unauthorizedHandler;
  private final UserDetailsServiceImpl userDetailsService;
  //private final AuthTokenFilter authTokenFilter;

  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService); // fetch user details from the database
    authProvider.setPasswordEncoder(passwordEncoder()); // Use the configured PasswordEncoder to match raw vs hashed
                                                        // password

    return authProvider;
  }

  // bean that Spring uses under the hood to perform authentication.
  // define this manually when using a custom DaoAuthenticationProvider
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  // PasswordEncoder bean that will be used to hash passwords before storing them
  // in the database
  public PasswordEncoder passwordEncoder() {
    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter) throws Exception {
    http.csrf(csrf -> csrf.disable()) // disable csrf since we are using JWT
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // handle unauthorized
                                                                                                 // access
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // dont use
                                                                                                      // sessions at all
                                                                                                      // - ideal for JWT
                                                                                                      // authentication
        .authorizeHttpRequests(auth -> auth
        .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api/auth/**",
                    "/api/public/**"
                ).permitAll()
                .requestMatchers("/api/auth/logout").authenticated()
                .anyRequest().authenticated()
        );
    

    http.authenticationProvider(authenticationProvider()); // use this custom provider to validate users not the default
                                                           // one
    http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); // add JWT filter
                                                                                                      // before the
                                                                                                      // UsernamePasswordAuthenticationFilter

    return http.build();
  }
}
