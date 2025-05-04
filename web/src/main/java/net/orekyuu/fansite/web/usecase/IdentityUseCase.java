package net.orekyuu.fansite.web.usecase;

import net.orekyuu.fansite.domain.basic.TimeProducer;
import net.orekyuu.fansite.domain.identity.AccountId;
import net.orekyuu.fansite.domain.identity.AccountRegistrationRule;
import net.orekyuu.fansite.domain.identity.AccountRepository;
import net.orekyuu.fansite.domain.validation.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public record IdentityUseCase(
        AccountRepository accountRepository,
        TimeProducer timeProducer) {
  private static final Logger logger = LoggerFactory.getLogger(IdentityUseCase.class);

  public static class AccountAlreadyRegisteredException extends Exception { }

  public void register(String id, String email) throws AccountAlreadyRegisteredException {
    var rule = new AccountRegistrationRule(accountRepository, timeProducer);
    var canRegistration = switch (rule.apply(new AccountId(id), email)) {
      case ValidationResult.Failure(var reason, var message) -> {
        logger.debug("ValidationResult is Failure. reason = {}, message = {}", reason, message);
        throw new AccountAlreadyRegisteredException();
      }
      case ValidationResult.Success(var canAccountRegistration) -> canAccountRegistration;
    };

    accountRepository.save(canRegistration.account());
  }
}
