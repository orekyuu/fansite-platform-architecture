package net.orekyuu.fansite.web.usecase;

import net.orekyuu.fansite.domain.basic.TimeProducer;
import net.orekyuu.fansite.domain.identity.AccountId;
import net.orekyuu.fansite.domain.identity.AccountRegistrationRule;
import net.orekyuu.fansite.domain.identity.AccountRepository;
import net.orekyuu.fansite.domain.validation.ValidationResult;

public record IdentityUseCase(
        AccountRepository accountRepository,
        TimeProducer timeProducer) {

  public static class AccountAlreadyRegisteredException extends RuntimeException { }

  public void register(String id, String email) {
    var rule = new AccountRegistrationRule(accountRepository, timeProducer);
    var canRegistration = switch (rule.apply(new AccountId(id), email)) {
      case ValidationResult.Failure(var reason, var message) -> {
        throw new AccountAlreadyRegisteredException();
      }
      case ValidationResult.Success(var canAccountRegistration) -> canAccountRegistration;
    };

    accountRepository.save(canRegistration.account());
  }
}
