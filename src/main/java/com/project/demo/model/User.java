package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user", uniqueConstraints = {
  @UniqueConstraint(columnNames = "email")})
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 50)
  private String fullname;

  @Size(max = 50)
  private String birthPlace;

  private Long birthDate;

  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Size(max = 120)
  private String password;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Role role;

  private Boolean jenisKelamin;
  private boolean isActive;
  private boolean isLogin;

  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @JsonIgnore
  private Date createdTime;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonIgnore
  private Date updatedTime;

  @JsonIgnore
  private boolean banned;
  @Temporal(TemporalType.TIMESTAMP)
  @JsonIgnore
  private Date bannedTime;

  public User(String fullname, String username, String email, String password, Role role) {
    Date date = new Date();
    this.fullname = fullname;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.isActive = true;
    this.isLogin = false;
    this.createdTime = date;
    this.updatedTime = date;
    this.banned = false;
    this.bannedTime = date;
    this.isActive = true;
  }

  public User(Long id) {
    this.id = id;
  }

}
