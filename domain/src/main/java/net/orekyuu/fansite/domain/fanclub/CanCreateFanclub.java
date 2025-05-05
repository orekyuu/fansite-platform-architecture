package net.orekyuu.fansite.domain.fanclub;

import net.orekyuu.fansite.domain.identity.AccountId;

public record CanCreateFanclub(Fanclub fanclub, AccountId accountId) {

}
