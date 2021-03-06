package com.example.applicationform.repository;

import com.example.applicationform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE p.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM User p WHERE p.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);
}
