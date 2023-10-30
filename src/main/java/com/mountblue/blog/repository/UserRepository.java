package com.mountblue.blog.repository;

import com.mountblue.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT * FROM User u WHERE u.email = :email")
    Optional<User> findByUserEmail(@Param("email") String email);

    @Query("SELECT * FROM User u WHERE u.email = :email AND u.password = :password")
    User findByEmailPassword(String email, String password);
}
