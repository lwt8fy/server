package net.jeeshop.services.manage.logistics.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.logistics.bean.Logistics;
import net.jeeshop.services.manage.logistics.dao.LogisticsDao;


public class LogisticsServiceImpl extends ServersManager <Logistics> implements LogisticsService {
	private LogisticsDao logisticsDao;

	public void setLogisticsDao(LogisticsDao logisticsDao) { 
		this.logisticsDao=logisticsDao;
	}
	
	@Override
	public int insert(Logistics e){
		return this.logisticsDao.insert(e);
	}
	
	@Override
	public PagerModel selectPageList(Logistics e){
		return this.logisticsDao.selectPageList(e);
	}
	
	@Override
	public Logistics selectLogisticsByProductId(String productid){
		return this.logisticsDao.selectLogisticsByProductId(productid);
	}
}
