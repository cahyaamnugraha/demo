package com.project.demo.repository;

import com.project.demo.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Transactional
  Optional<User> findByUsername(String username);

  @Transactional
  Optional<User> findByEmail(String email);

  @Transactional
  Boolean existsByUsername(String username);
  
  @Transactional
  Boolean existsByEmail(String email);

  @Transactional
  User findById(long id);
  
  @Transactional
  List<User> findByUsernameLikeOrEmailLike(String username, String email, PageRequest pageRequest);
  
  @Transactional
  long countByUsernameLikeOrEmailLike(String username, String email);

  @Modifying
  @Transactional
  @Query("update User u set u.isLogin = ?1 where u.id = ?2")
  int setIsLogin(boolean isLogin, Long id);

}
