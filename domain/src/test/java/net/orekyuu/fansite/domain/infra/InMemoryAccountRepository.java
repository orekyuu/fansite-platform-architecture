package net.orekyuu.fansite.domain.infra;

import net.orekyuu.fansite.domain.identity.Account;
import net.orekyuu.fansite.domain.identity.AccountId;
import net.orekyuu.fansite.domain.identity.AccountRepository;

import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
  private final InMemoryDatabase<AccountId, Account> database = new InMemoryDatabase<>(Account::getId);

  @Override
  public Optional<Account> findById(AccountId id) {
    return database.findById(id);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    return database.find(it -> it.getEmail().equals(email)).stream().findFirst();
  }

  @Override
  public void save(Account account) {
    database.upsert(account);
  }

  @Override
  public Account create(Account account) {
    database.insert(account);
    return account;
  }
}
