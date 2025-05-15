package com.example.ecom.user.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.model.User;
import com.example.ecom.user.dto.UpdateProfileDTO;
import com.example.ecom.user.dto.UserUpdateDTO;

public interface UserInterface {
  List<UserDto> getAllUsers();
  UserDto getUserById(UUID id);
  UserDto getCurrentUser();
  UserDto updateProfile(UpdateProfileDTO userUpdateDTO);
  UserDto updateUser(UUID id,UserUpdateDTO userUpdateDTO);
  void deleteUser(UUID id);
  User getUserEntityById(UUID id);
  
}
