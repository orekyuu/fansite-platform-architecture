package net.orekyuu.fansite.domain.basic;

import java.util.Objects;

public class Id<T> {
  private static final Id<?> UNASSIGNED = new Id<>(-1);
  private final long id;
  private Id(long id) {
    this.id = id;
  }

  public boolean isNotAssigned () {
    return id < 0;
  }

  public static <T> Id<T> of(long id) {
    return new Id<>(id);
  }

  @SuppressWarnings("unchecked")
  public static <T> Id<T> unassigned() {
    return (Id<T>) UNASSIGNED;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Id<?> id1 = (Id<?>) o;
    if (this.isNotAssigned() || ((Id<?>) o).isNotAssigned()) {
      return false;
    }

    return id == id1.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
