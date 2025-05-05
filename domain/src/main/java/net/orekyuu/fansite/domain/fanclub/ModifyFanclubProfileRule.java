package net.orekyuu.fansite.domain.fanclub;

import net.orekyuu.fansite.domain.validation.ValidationResult;

public class ModifyFanclubProfileRule {
  public ValidationResult<CanModifyFanclubProfile, Void> apply(FanclubProfile profile) {
    return ValidationResult.withSuccess(new CanModifyFanclubProfile(profile));
  }
}
