package net.jeeshop.services.front.appPigFarm.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppPigFarm;
import net.jeeshop.services.front.appPigFarm.dao.AppPigFarmDao;

public class AppPigFarmServiceImpl  extends ServersManager<AppPigFarm> implements AppPigFarmService{
	private AppPigFarmDao appPigFarmDao;

	public void setAppPigFarmDao(AppPigFarmDao appPigFarmDao) {
		this.appPigFarmDao = appPigFarmDao;
	}

	

	
	
}
