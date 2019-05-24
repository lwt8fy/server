package net.jeeshop.services.front.appUser.service;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppUser;

public interface AppUserService  extends Services<AppUser>{
	AppUser selectByUuid(String uuid);
	void updateMoney(AppUser u);
}
