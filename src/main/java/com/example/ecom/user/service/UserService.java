package com.example.ecom.user.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.model.User;
import com.example.ecom.repository.RoleRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.security.jwt.JwtService;
import com.example.ecom.user.interfaces.UserInterface;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface {

  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final HttpServletRequest request;
    @Override
    public List<UserDto> getAllUsers() {
       return userRepo.findAll().stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(UUID id) {
      return userRepo.findById(id)
              .map(this::convertToDto)
              .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public UserDto getCurrentUser() {
      String jwt = request.getHeader("Authorization").substring(7);
      String username = jwtService.getUserNameFromToken(jwt);
      return userRepo.findByUsername(username)
              .map(this::convertToDto)
              .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @O


    private UserDto convertToDto(User user){
      UserDto dto = new UserDto();
      dto.setId(user.getId());
      dto.setUsername(user.getUsername());
      dto.setEmail(user.getEmail());
      dto.setFirstName(user.getFirstName());
      dto.setLastName(user.getLastName());
      dto.setActive(user.getActive());
      dto.setRole(user.getRole().getName().name());
      dto.setCreatedAt(user.getCreatedAt());
      dto.setUpdatedAt(user.getUpdatedAt());
      return dto;
    }
  }