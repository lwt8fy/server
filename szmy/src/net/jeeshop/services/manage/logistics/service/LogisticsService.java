package net.jeeshop.services.manage.logistics.service;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.logistics.bean.Logistics;


public interface LogisticsService extends Services <Logistics> {
	
	@Override
	public int insert(Logistics e);
	
	@Override
	public PagerModel selectPageList(Logistics e);
	
	public Logistics selectLogisticsByProductId(String productid);
}
