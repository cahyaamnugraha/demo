package com.project.demo.controller;

import com.project.demo.service.RoleService;
import com.project.demo.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/pegawai/combo/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

  @Autowired
  StringUtil stringUtil;

  @Autowired
  HttpServletRequest request;

  @Autowired
  RoleService service;
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/role")
  public ResponseEntity<?> getAll() {
    String uri = stringUtil.getLogParam(request);
    logger.info(uri);

    return service.findAll();
  }

}
