package net.jeeshop.services.front.appSlaughter.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppSlaughter;
import net.jeeshop.services.front.appSlaughter.dao.AppSlaughterDao;

public class AppSlaughterServiceImpl  extends ServersManager<AppSlaughter> implements AppSlaughterService{
	private AppSlaughterDao appSlaughterDao;

	public void setAppSlaughterDao(AppSlaughterDao appSlaughterDao) {
		this.appSlaughterDao = appSlaughterDao;
	}


}
