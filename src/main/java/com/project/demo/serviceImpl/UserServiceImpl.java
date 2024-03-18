package com.project.demo.serviceImpl;

import com.project.demo.enums.EnumRole;
import com.project.demo.model.RefreshToken;
import com.project.demo.model.Result;
import com.project.demo.model.Role;
import com.project.demo.model.User;
import com.project.demo.repository.RoleRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.request.*;
import com.project.demo.response.JwtResponse;
import com.project.demo.service.UserService;
import com.project.demo.util.JwtUtils;
import com.project.demo.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  RefreshTokenService refreshTokenService;

  @Autowired
  StringUtil stringUtil;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @Value("${demo.jwtExpirationMs}")
  private int jwtExpirationMs;

  Result result;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public ResponseEntity<?> createUser(SignupRequest signUpRequest) {
    result = new Result();
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      result.setSuccess(false);
      result.setCode(HttpStatus.BAD_REQUEST.value());
      result.setMessage("Email has been used");

      return ResponseEntity.badRequest().body(result);
    } else if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      result.setSuccess(false);
      result.setCode(HttpStatus.BAD_REQUEST.value());
      result.setMessage("Username has been used");

      return ResponseEntity.badRequest().body(result);
    } else {
      User userResult = userRepository.save(
          new User(
              signUpRequest.getFullname(),
              signUpRequest.getUsername(),
              signUpRequest.getEmail(),
              encoder.encode(signUpRequest.getPassword()),
              new Role(signUpRequest.getRole())
          ));

      result.setSuccess(true);
      result.setCode(HttpStatus.OK.value());
      result.setMessage("signup success");
      result.setData(userResult);

      return ResponseEntity.ok(result);
    }
  }

  @Override
  public ResponseEntity<?> signIn(LoginRequest loginRequest, String uri) {

    User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(new User());
    if (user.getUsername() != null) {
      Date dateNow = new Date();
      Date dateExpired = new Date((dateNow).getTime() + jwtExpirationMs);

      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

      Role role = roleRepository.findByName(EnumRole.valueOf(userDetails.getAuthorities().stream().findAny().get().getAuthority())).orElse(new Role());
      String jwt = jwtUtils.generateJwtToken(authentication, dateNow, dateExpired);

      result.setSuccess(true);
      result.setMessage("success");
      result.setData(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
          userDetails.getEmail(), role, dateExpired.getTime()));

      userRepository.setIsLogin(true, userDetails.getId());
    } else {
      result.setSuccess(false);
      result.setCode(HttpStatus.BAD_REQUEST.value());
      result.setMessage("Email not registered");
    }

    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
    result = new Result();
    if (!changePasswordRequest.getPasswordNew1().equals(changePasswordRequest.getPasswordNew2())) {
      result.setSuccess(false);
      result.setCode(HttpStatus.BAD_REQUEST.value());
      result.setMessage("the new password is not the same.");

      return ResponseEntity.badRequest().body(result);
    } else {
      User user = userRepository.findByUsername(changePasswordRequest.getUsername()).orElse(null);
      if (user == null) {
        result.setSuccess(false);
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMessage("cannot find user with this email");

        return ResponseEntity.badRequest().body(result);
      } else {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), changePasswordRequest.getPasswordOld()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userDetails == null) {
          result.setSuccess(false);
          result.setCode(HttpStatus.BAD_REQUEST.value());
          result.setMessage("combination of email and password is wrong");

          return ResponseEntity.badRequest().body(result);
        } else {
          user.setPassword(encoder.encode(changePasswordRequest.getPasswordNew1()));
          User userResult = userRepository.save(user);

          result.setSuccess(true);
          result.setCode(HttpStatus.OK.value());
          result.setMessage("update user success");
          result.setData(userResult);

          return ResponseEntity.ok().body(result);
        }
      }
    }
  }

  @Override
  public ResponseEntity<?> findAll() {
    result.setSuccess(true);
    result.setCode(HttpStatus.OK.value());
    result.setMessage("update user success");
    result.setData(userRepository.findAll());

    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<?> updateUser(User user) {
    User userResult = userRepository.save(user);

    result.setSuccess(true);
    result.setCode(HttpStatus.OK.value());
    result.setMessage("Update user success");
    result.setData(userResult);

    return ResponseEntity.ok(result);
  }

}
