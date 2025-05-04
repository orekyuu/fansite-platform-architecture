package net.orekyuu.fansite.domain.assertj;

import net.orekyuu.fansite.domain.validation.ValidationResult;

public class FansitePlatformAssertions extends org.assertj.core.api.Assertions {

  public static <R, E> ValidationResultAssert<R, E> assertThat(ValidationResult<R, E> actual) {
    return new ValidationResultAssert<>(actual, ValidationResultAssert.class);
  }
}
