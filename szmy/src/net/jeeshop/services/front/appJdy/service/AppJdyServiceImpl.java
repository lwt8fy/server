package net.jeeshop.services.front.appJdy.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appJdy.dao.AppJdyDao;
import net.jeeshop.services.front.appJdy.service.AppJdyService;
import net.jeeshop.services.common.AppJdy;


public class AppJdyServiceImpl extends ServersManager <AppJdy> implements AppJdyService {
	private AppJdyDao appJdyDao;

	public void setAppJdyDao(AppJdyDao appJdyDao) { 
		this.appJdyDao=appJdyDao;
	}
}
