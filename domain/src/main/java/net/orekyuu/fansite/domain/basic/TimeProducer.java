package net.orekyuu.fansite.domain.basic;

import java.time.Instant;

public interface TimeProducer {

  default Instant now() {
    return Instant.now();
  }

  Instant requestedAt();
}
