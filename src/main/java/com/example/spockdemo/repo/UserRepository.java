package com.example.spockdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByMarketUserId(String marketUserId);

}
