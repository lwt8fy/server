package net.jeeshop.services.front.appVehicleCompany.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppVehicleCompany;
import net.jeeshop.services.front.appVehicleCompany.dao.AppVehicleCompanyDao;

public class AppVehicleCompanyServiceImpl extends ServersManager<AppVehicleCompany> implements AppVehicleCompanyService {
	
	private AppVehicleCompanyDao appVehicleCompanyDao;

	public void setAppVehicleCompanyDao(AppVehicleCompanyDao appVehicleCompanyDao) {
		this.appVehicleCompanyDao = appVehicleCompanyDao;
	}

	public AppVehicleCompanyDao getAppVehicleCompanyDao() {
		return appVehicleCompanyDao;
	}

}
