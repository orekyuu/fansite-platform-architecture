package net.orekyuu.fansite.domain.fanclub;

import java.net.URI;

public class FanclubProfile {
  private String name;
  private String description;
  private URI headerImage;
  private FanclubPublishState state;

  FanclubProfile(String name, String description, URI headerImage, FanclubPublishState state) {
    this.name = name;
    this.description = description;
    this.headerImage = headerImage;
    this.state = state;
  }

  public static FanclubProfile createDefaultProfile(String name, String description, URI headerImage) {
    return new FanclubProfile(name, description, headerImage, FanclubPublishState.DRAFT);
  }
}
