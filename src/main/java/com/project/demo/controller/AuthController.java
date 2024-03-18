package com.project.demo.controller;

import com.project.demo.request.ChangePasswordRequest;
import com.project.demo.request.LoginRequest;
import com.project.demo.request.SignupRequest;
import com.project.demo.service.UserService;
import com.project.demo.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  UserService service;

  @Autowired
  StringUtil stringUtil;

  @Autowired
  HttpServletRequest request;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String uri = stringUtil.getLogParam(request);
    logger.info(uri);

    return service.signIn(loginRequest, uri);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
    String uri = stringUtil.getLogParam(request);
    logger.info(uri);

    return service.createUser(signupRequest);
  }

  @PostMapping("/ubah-password-sendiri")
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    String uri = stringUtil.getLogParam(request);
    logger.info(uri);

    return service.changePassword(changePasswordRequest);
  }

}
