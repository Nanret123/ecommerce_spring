package com.example.ecom.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// this class connects your Spring Security authentication system with your database. It fetches the user from the db and hands it to spring
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional // lazy-loaded fields (like roles) are loaded in a transaction.
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
    return UserDetailsImpl.build(user);
  }
}
