package net.orekyuu.fansite.domain.fanclub;

import net.orekyuu.fansite.domain.identity.AccountId;

import java.util.Optional;

public interface FanclubRepository {

  Optional<Fanclub> findBySubDomain(FanclubSubDomain subDomain);

  Optional<Fanclub> findByAccountId(AccountId accountId);

  void save(Fanclub fanclub);
}
