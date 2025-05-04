package net.orekyuu.fansite.domain.identity;

import java.util.Optional;

public interface AccountRepository {
  Optional<Account> findById(AccountId id);

  Optional<Account> findByEmail(String email);

  void save(Account account);

  Account create(Account account);
}
