package net.jeeshop.services.front.appLocation.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appLocation.dao.AppLocationDao;
import net.jeeshop.services.front.appLocation.service.AppLocationService;
import net.jeeshop.services.common.AppLocation;


public class AppLocationServiceImpl extends ServersManager <AppLocation> implements AppLocationService {
	private AppLocationDao appLocationDao;

	public void setAppLocationDao(AppLocationDao appLocationDao) { 
		this.appLocationDao=appLocationDao;
	}
}
