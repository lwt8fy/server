package net.jeeshop.services.front.appOrder.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppOrder;
import net.jeeshop.services.front.appOrder.dao.AppOrderDao;

public class AppOrderServiceImpl  extends ServersManager<AppOrder> implements AppOrderService{
	private AppOrderDao appOrderDao;

	public void setAppOrderDao(AppOrderDao appOrderDao) {
		this.appOrderDao = appOrderDao;
	}

	@Override
	public void updatePayStatus(AppOrder e) {
		// TODO Auto-generated method stub
		appOrderDao.updatePayStatus(e);
	}
	@Override
	public Double countAppOrderJye(AppOrder e){
		return appOrderDao.countAppOrderJye(e);
	}
	
}
