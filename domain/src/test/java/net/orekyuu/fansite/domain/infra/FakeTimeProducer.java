package net.orekyuu.fansite.domain.infra;

import net.orekyuu.fansite.domain.basic.TimeProducer;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class FakeTimeProducer implements TimeProducer {

  private static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
  private Clock clock = Clock.fixed(DEFAULT_TIME.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);


  @Override
  public Instant now() {
    return clock.instant();
  }

  @Override
  public Instant requestedAt() {
    return clock.instant();
  }

  public void setTime(LocalDateTime time) {
    clock = Clock.fixed(time.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
  }

  public void reset() {
    setTime(DEFAULT_TIME);
  }
}
