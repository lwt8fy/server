package net.jeeshop.services.front.appUser.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.front.appUser.dao.AppUserDao;

public class AppUserServiceImpl  extends ServersManager<AppUser> implements AppUserService{
	private AppUserDao appUserDao;

	public void setAppUserDao(AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}

	@Override
	public AppUser selectByUuid(String uuid) {
		// TODO Auto-generated method stub
		return appUserDao.selectByUuid(uuid);
	}

	@Override
	public void updateMoney(AppUser u) {
		// TODO Auto-generated method stub
		appUserDao.updateMoney(u);
	}

	
}
