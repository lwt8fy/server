package net.jeeshop.services.front.appBroker.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppBroker;
import net.jeeshop.services.front.appBroker.dao.AppBrokerDao;

public class AppBrokerServiceImpl  extends ServersManager<AppBroker> implements AppBrokerService{
	private AppBrokerDao appBrokerDao;

	public void setAppBrokerDao(AppBrokerDao appBrokerDao) {
		this.appBrokerDao = appBrokerDao;
	}

	
}
