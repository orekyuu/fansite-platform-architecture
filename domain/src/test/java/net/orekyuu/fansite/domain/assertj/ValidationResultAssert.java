package net.orekyuu.fansite.domain.assertj;

import net.orekyuu.fansite.domain.validation.ValidationResult;
import org.assertj.core.api.AbstractAssert;

import java.util.function.Consumer;

public class ValidationResultAssert<R, E> extends AbstractAssert<ValidationResultAssert<R, E>, ValidationResult<R, E>> {
  protected ValidationResultAssert(ValidationResult<R, E> actual, Class<?> selfType) {
    super(actual, selfType);
  }

  public ValidationResultAssert<R, E> isSuccess() {
    if (actual() instanceof ValidationResult.Failure<?,?> failure) {
      failWithMessage("ValidationResult is Failure. expected: Success but was: %s", failure);
    }
    return this;
  }

  public ValidationResultAssert<R, E> isFailure() {
    if (actual() instanceof ValidationResult.Success<?,?> success) {
      failWithMessage("ValidationResult is Success. expected: Failure but was: %s", success);
    }
    return this;
  }

  public ValidationResultAssert<R, E> successSatisfies(Consumer<ValidationResult.Success<R,E>> consumer) {
    isSuccess();
    consumer.accept((ValidationResult.Success<R,E>) actual());
    return this;
  }

  public ValidationResultAssert<R, E> failureSatisfies(Consumer<ValidationResult.Failure<R,E>> consumer) {
    isFailure();
    consumer.accept((ValidationResult.Failure<R,E>) actual());
    return this;
  }
}
