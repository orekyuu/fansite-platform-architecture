package net.orekyuu.fansite.domain.fanclub;

public class CreateFanclubService {
  private final FanclubRepository fanclubRepository;

  public CreateFanclubService(FanclubRepository fanclubRepository) {
    this.fanclubRepository = fanclubRepository;
  }

  public void create(CanCreateFanclub canCreateFanclub, CanModifyFanclubProfile canModifyFanclubProfile) {
    var fanclub = canCreateFanclub.fanclub();
    fanclub.modifyProfile(canModifyFanclubProfile.profile());

    fanclubRepository.save(fanclub);
  }
}
