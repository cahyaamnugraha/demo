package com.project.demo.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequest {

  @NotBlank
  @Size(max = 50)
  @Email
  @ApiModelProperty(example = "user123", required = true)
  private String fullname;

  @NotBlank
  @Size(min = 3, max = 20)
  @ApiModelProperty(example = "user123", required = true)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  @ApiModelProperty(example = "user123@gmail.com", required = true)
  private String email;

  private Integer role;

  @NotBlank
  @Size(min = 6, max = 40)
  @ApiModelProperty(example = "password123", required = true)
  private String password;

  public SignupRequest(String fullname, String username, String email, Integer role, String password) {
    this.fullname = fullname;
    this.username = username;
    this.email = email;
    this.role = role;
    this.password = password;
  }

}
