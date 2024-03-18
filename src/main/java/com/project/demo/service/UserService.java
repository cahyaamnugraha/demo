package com.project.demo.service;

import com.project.demo.model.User;
import com.project.demo.request.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<?> createUser(SignupRequest SignupRequest);
  
  ResponseEntity<?> updateUser(User user);

  ResponseEntity<?> signIn(LoginRequest loginRequest, String uri);

  ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest);  
  
  ResponseEntity<?> findAll();
}
