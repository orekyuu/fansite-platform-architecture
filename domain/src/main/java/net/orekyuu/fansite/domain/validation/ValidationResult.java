package net.orekyuu.fansite.domain.validation;

import java.util.Objects;

public sealed interface ValidationResult<R, E> {
  record Success<R, E>(R result) implements ValidationResult<R, E> {}
  record Failure<R, E>(E error, String messageForDeveloper) implements ValidationResult<R, E> {}

  static <R, E> ValidationResult<R, E> withSuccess(R result) {
    return new Success<>(result);
  }

  static <R, E> ValidationResult<R, E> withFailure(E error, String messageForDeveloper) {
    return new Failure<>(error, Objects.requireNonNullElse(messageForDeveloper, ""));
  }
}
