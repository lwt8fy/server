package net.jeeshop.services.front.appVersions.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppVersions;
import net.jeeshop.services.front.appVersions.dao.AppVersionsDao;

public class AppVersionsServiceImpl  extends ServersManager<AppVersions> implements AppVersionsService{
	private AppVersionsDao appVersionsDao;

	public void setAppVersionsDao(AppVersionsDao appVersionsDao) {
		this.appVersionsDao = appVersionsDao;
	}

	@Override
	public AppVersions selectNewest() {
		// TODO Auto-generated method stub
		return appVersionsDao.selectNewest();
	}
	
}
