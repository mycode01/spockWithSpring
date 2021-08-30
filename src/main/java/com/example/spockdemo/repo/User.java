package com.example.spockdemo.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(unique = true)
  String marketUserId;
  String email;


  public String getEmail() {
    return email;
  }

  public User(String marketUserId, String email) {
    this.marketUserId = marketUserId;
    this.email = email;
  }

  protected User() {
  }
}
