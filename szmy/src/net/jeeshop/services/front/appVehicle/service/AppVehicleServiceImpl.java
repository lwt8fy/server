package net.jeeshop.services.front.appVehicle.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppVehicle;
import net.jeeshop.services.front.appVehicle.dao.AppVehicleDao;

public class AppVehicleServiceImpl  extends ServersManager<AppVehicle> implements AppVehicleService{
	
	private AppVehicleDao appVehicleDao;

	public void setAppVehicleDao(AppVehicleDao appVehicleDao) {
		this.appVehicleDao = appVehicleDao;
	}

	public AppVehicleDao getAppVehicleDao() {
		return appVehicleDao;
	}

	@Override
	public void deleteById(String id) throws Exception {
		this.appVehicleDao.deleteById(Integer.parseInt(id));
	}
}
