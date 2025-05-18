package net.orekyuu.fansite.domain.fanclub;

import java.time.Instant;

public class Fanclub {
  private final FanclubSubDomain subDomain;
  private final Instant createdAt;
  private FanclubProfile profile;

  public Fanclub(FanclubSubDomain subDomain, Instant createdAt, FanclubProfile profile) {
    this.subDomain = subDomain;
    this.createdAt = createdAt;
    this.profile = profile;
  }

  void modifyProfile(FanclubProfile profile) {
    this.profile = profile;
  }
}
