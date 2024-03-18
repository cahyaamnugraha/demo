package com.project.demo.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String passwordOld;
  
  @NotBlank
  private String passwordNew1;
  
  @NotBlank
  private String passwordNew2;
}
