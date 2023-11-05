package com.mountblue.blog.repository;

import com.mountblue.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
