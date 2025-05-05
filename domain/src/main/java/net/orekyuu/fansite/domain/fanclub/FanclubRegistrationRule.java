package net.orekyuu.fansite.domain.fanclub;

import net.orekyuu.fansite.domain.basic.TimeProducer;
import net.orekyuu.fansite.domain.identity.AccountId;
import net.orekyuu.fansite.domain.validation.ValidationResult;

public class FanclubRegistrationRule {
  private final FanclubRepository fanclubRepository;
  private final TimeProducer timeProducer;

  public FanclubRegistrationRule(FanclubRepository fanclubRepository, TimeProducer timeProducer) {
    this.fanclubRepository = fanclubRepository;
    this.timeProducer = timeProducer;
  }

  public enum FailureReason {
    DUPLICATED_SUB_DOMAIN,
  }

  public ValidationResult<CanCreateFanclub, FailureReason> apply(String subDomain, AccountId accountId, CanModifyFanclubProfile canModifyFanclubProfile) {
    var domain = new FanclubSubDomain(subDomain);
    if (fanclubRepository.findBySubDomain(domain).isPresent()) {
      return ValidationResult.withFailure(FailureReason.DUPLICATED_SUB_DOMAIN, "subDomain = " + subDomain);
    }
    var profile = canModifyFanclubProfile.profile();
    return ValidationResult.withSuccess(new CanCreateFanclub(new Fanclub(domain, timeProducer.requestedAt(), profile), accountId));
  }
}
