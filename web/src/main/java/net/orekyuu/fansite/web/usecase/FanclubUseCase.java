package net.orekyuu.fansite.web.usecase;

import net.orekyuu.fansite.domain.basic.TimeProducer;
import net.orekyuu.fansite.domain.fanclub.CanCreateFanclub;
import net.orekyuu.fansite.domain.fanclub.CanModifyFanclubProfile;
import net.orekyuu.fansite.domain.fanclub.CreateFanclubService;
import net.orekyuu.fansite.domain.fanclub.FanclubProfile;
import net.orekyuu.fansite.domain.fanclub.FanclubRegistrationRule;
import net.orekyuu.fansite.domain.fanclub.FanclubRepository;
import net.orekyuu.fansite.domain.fanclub.ModifyFanclubProfileRule;
import net.orekyuu.fansite.domain.identity.AccountId;
import net.orekyuu.fansite.domain.identity.AccountRepository;
import net.orekyuu.fansite.domain.validation.ValidationResult.Failure;
import net.orekyuu.fansite.domain.validation.ValidationResult.Success;

public record FanclubUseCase(FanclubRepository fanclubRepository, AccountRepository accountRepository, TimeProducer timeProducer) {

  public void createFanclub(String accountId, String fanclubName, String fanclubSubDomain) {
    var modifyFanclubProfileRule = new ModifyFanclubProfileRule();
    var defaultProfile = FanclubProfile.createDefaultProfile(accountId, "", null);
    var canModifyProfile = switch (modifyFanclubProfileRule.apply(defaultProfile)) {
      case Failure<CanModifyFanclubProfile, Void> v -> throw new RuntimeException("ValidationResult is Failure.");
      case Success<CanModifyFanclubProfile, Void>(var $) -> $;
    };

    var registrationRule = new FanclubRegistrationRule(fanclubRepository, timeProducer);
    var canRegistration = switch (registrationRule.apply(fanclubSubDomain, new AccountId(accountId), canModifyProfile)) {
      case Failure<CanCreateFanclub, FanclubRegistrationRule.FailureReason> v -> throw new RuntimeException("ValidationResult is Failure.");
      case Success<CanCreateFanclub, FanclubRegistrationRule.FailureReason>(var $) -> $;
    };

    new CreateFanclubService(fanclubRepository).create(canRegistration, canModifyProfile);
  }
}
