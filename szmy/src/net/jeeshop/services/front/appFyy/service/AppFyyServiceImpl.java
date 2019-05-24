package net.jeeshop.services.front.appFyy.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appFyy.dao.AppFyyDao;
import net.jeeshop.services.front.appFyy.service.AppFyyService;
import net.jeeshop.services.common.AppFyy;


public class AppFyyServiceImpl extends ServersManager <AppFyy> implements AppFyyService {
	private AppFyyDao appFyyDao;

	public void setAppFyyDao(AppFyyDao appFyyDao) { 
		this.appFyyDao=appFyyDao;
	}
}
