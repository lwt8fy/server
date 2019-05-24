package net.jeeshop.services.front.appUser.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.common.AppUser;

public interface AppUserDao  extends DaoManager<AppUser> {

	AppUser selectByUuid(String uuid);

	void updateMoney(AppUser u);
	
}
