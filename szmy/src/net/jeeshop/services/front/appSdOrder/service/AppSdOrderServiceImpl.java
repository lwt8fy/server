package net.jeeshop.services.front.appSdOrder.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.appSdOrder.dao.AppSdOrderDao;
import net.jeeshop.services.front.appSdOrder.service.AppSdOrderService;
import net.jeeshop.services.common.AppSdOrder;


public class AppSdOrderServiceImpl extends ServersManager <AppSdOrder> implements AppSdOrderService {
	private AppSdOrderDao appSdOrderDao;

	public void setAppSdOrderDao(AppSdOrderDao appSdOrderDao) { 
		this.appSdOrderDao=appSdOrderDao;
	}
	@Override
	public List<String> selectSdUser(){
		return appSdOrderDao.selectSdUser();
	}
}
