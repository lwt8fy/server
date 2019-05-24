package net.jeeshop.services.front.appJyy.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appJyy.dao.AppJyyDao;
import net.jeeshop.services.front.appJyy.service.AppJyyService;
import net.jeeshop.services.common.AppJyy;


public class AppJyyServiceImpl extends ServersManager <AppJyy> implements AppJyyService {
	private AppJyyDao appJyyDao;

	public void setAppJyyDao(AppJyyDao appJyyDao) { 
		this.appJyyDao=appJyyDao;
	}
}
