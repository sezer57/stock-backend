package com.example.demo.repository;

import com.example.demo.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findByName(String username);

}
