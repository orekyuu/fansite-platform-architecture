package net.orekyuu.fansite.domain.identity;

import net.orekyuu.fansite.domain.basic.TimeProducer;
import net.orekyuu.fansite.domain.validation.ValidationResult;

public class AccountRegistrationRule {
  private final AccountRepository accountRepository;
  private final TimeProducer timeProducer;

  public AccountRegistrationRule(AccountRepository accountRepository, TimeProducer timeProducer) {
    this.accountRepository = accountRepository;
    this.timeProducer = timeProducer;
  }

  public enum FailureReason {
    DUPLICATED_EMAIL,
    DUPLICATED_ACCOUNT_ID,
  }

  public ValidationResult<CanAccountRegistration, FailureReason> apply(AccountId accountId, String email) {
    if (accountRepository.findById(accountId).isPresent()) {
      return ValidationResult.withFailure(FailureReason.DUPLICATED_ACCOUNT_ID, "id = " + accountId.value());
    }

    if (accountRepository.findByEmail(email).isPresent()) {
      return ValidationResult.withFailure(FailureReason.DUPLICATED_EMAIL, "email = " + email);
    }

    return ValidationResult.withSuccess(new CanAccountRegistration(new Account(accountId, email, timeProducer.requestedAt(), timeProducer.requestedAt())));
  }
}
