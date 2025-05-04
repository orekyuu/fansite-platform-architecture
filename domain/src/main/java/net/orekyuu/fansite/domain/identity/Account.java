package net.orekyuu.fansite.domain.identity;

import java.time.Instant;

public class Account {
  private AccountId id;
  private String email;
  private Instant createdAt;
  private Instant lastLoginAt;

  public Account(AccountId id, String email, Instant createdAt, Instant lastLoginAt) {
    this.id = id;
    this.email = email;
    this.createdAt = createdAt;
    this.lastLoginAt = lastLoginAt;
  }

  public AccountId getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getLastLoginAt() {
    return lastLoginAt;
  }
}
