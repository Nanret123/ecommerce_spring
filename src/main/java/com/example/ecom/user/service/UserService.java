package com.example.ecom.user.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.model.ERole;
import com.example.ecom.model.Role;
import com.example.ecom.model.User;
import com.example.ecom.repository.RoleRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.user.dto.UpdateProfileDTO;
import com.example.ecom.user.dto.UserUpdateDTO;
import com.example.ecom.user.interfaces.UserInterface;
import com.example.ecom.user.utils.CurrentUserUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface {

  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final CurrentUserUtil currentUserUtil;

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
    User currentUser = currentUserUtil.getCurrentUser();
    return convertToDto(currentUser);
  }

  @Override
  @Transactional
  public UserDto updateProfile(UpdateProfileDTO userUpdateDTO) {
    User currentUser = currentUserUtil.getCurrentUser();
    if (userUpdateDTO.getUsername() != null) {
      currentUser.setUsername(userUpdateDTO.getUsername());
    }
    if (userUpdateDTO.getFirstName() != null) {
      currentUser.setFirstName(userUpdateDTO.getFirstName());
    }
    if (userUpdateDTO.getLastName() != null) {
      currentUser.setLastName(userUpdateDTO.getLastName());
    }
    userRepo.save(currentUser);
    return convertToDto(currentUser);
  }

  @Override
  @Transactional
  public UserDto updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
    User userToUpdate = getUserEntityById(id);
    if (userUpdateDTO.getFirstName() != null) {
      userToUpdate.setFirstName(userUpdateDTO.getFirstName());
    }
    if (userUpdateDTO.getLastName() != null) {
      userToUpdate.setLastName(userUpdateDTO.getLastName());
    }
    if (userUpdateDTO.getEmail() != null) {
      userToUpdate.setEmail(userUpdateDTO.getEmail());
    }
    if (userUpdateDTO.getUsername() != null) {
      userToUpdate.setUsername(userUpdateDTO.getUsername());
    }
    if (userUpdateDTO.getPassword() != null) {
      userToUpdate.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
    }
    if (userUpdateDTO.getActive() != null) {
      userToUpdate.setActive(userUpdateDTO.getActive());
    }
    if (userUpdateDTO.getRole() != null) {
      ERole role = ERole.valueOf(userUpdateDTO.getRole().toUpperCase());
      Role roleName = roleRepo.findByName(role)
          .orElseThrow(() -> new RuntimeException("Role not found"));
      userToUpdate.setRole(roleName);
    }
    userRepo.save(userToUpdate);
    return convertToDto(userToUpdate);

  }

  @Override
  @Transactional
  public void deleteUser(UUID id) {
    User userToDelete = getUserEntityById(id);
    userRepo.delete(userToDelete);
  }

  @Override
  public User getUserEntityById(UUID id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("User Not Found"));
  }

  private UserDto convertToDto(User user) {
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