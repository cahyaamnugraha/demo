package com.project.demo.serviceImpl;

import com.project.demo.repository.RoleRepository;
import com.project.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleRepository repository;

  @Override
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok().body(repository.findAll());
  }

}
