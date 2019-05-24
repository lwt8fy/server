package net.jeeshop.services.front.appNatural.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appNatural.dao.AppNaturalDao;
import net.jeeshop.services.front.appNatural.service.AppNaturalService;
import net.jeeshop.services.common.AppNatural;


public class AppNaturalServiceImpl extends ServersManager <AppNatural> implements AppNaturalService {
	private AppNaturalDao appNaturalDao;

	public void setAppNaturalDao(AppNaturalDao appNaturalDao) { 
		this.appNaturalDao=appNaturalDao;
	}
}
