package net.orekyuu.fansite.domain.identity;

import net.orekyuu.fansite.domain.identity.AccountRegistrationRule.FailureReason;
import net.orekyuu.fansite.domain.infra.FakeTimeProducer;
import net.orekyuu.fansite.domain.infra.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static net.orekyuu.fansite.domain.assertj.FansitePlatformAssertions.assertThat;

class AccountRegistrationRuleTest {

  AccountRegistrationRule rule;
  AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    accountRepository = new InMemoryAccountRepository();
    var timeProducer = new FakeTimeProducer();
    rule = new AccountRegistrationRule(accountRepository, timeProducer);
  }

  @Test
  void succeeded() {
    var result = rule.apply(new AccountId("account1"), "orekyuu@hoge.test");

    assertThat(result).successSatisfies(success -> {
      var canAccountRegistration = success.result();
      var account = canAccountRegistration.account();
      assertThat(account.getId()).isEqualTo(new AccountId("account1"));
      assertThat(account.getEmail()).isEqualTo("orekyuu@hoge.test");
      assertThat(account.getCreatedAt()).isNotNull();
      assertThat(account.getLastLoginAt()).isNotNull();
    });
  }

  @Test
  void duplicatedAccountId() {
    accountRepository.create(new Account(new AccountId("account1"), "orekyuu+1@hoge.test", Instant.now(), Instant.now()));

    var result = rule.apply(new AccountId("account1"), "orekyuu@hoge.test");

    assertThat(result).failureSatisfies(failure -> {
      assertThat(failure.error()).isEqualTo(FailureReason.DUPLICATED_ACCOUNT_ID);
    });
  }

  @Test
  void duplicatedEmail() {
    accountRepository.create(new Account(new AccountId("account1"), "orekyuu+1@hoge.test", Instant.now(), Instant.now()));

    var result = rule.apply(new AccountId("account2"), "orekyuu+1@hoge.test");

    assertThat(result).failureSatisfies(failure -> {
      assertThat(failure.error()).isEqualTo(FailureReason.DUPLICATED_EMAIL);
    });
  }
}