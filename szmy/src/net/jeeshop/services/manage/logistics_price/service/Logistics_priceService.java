package net.jeeshop.services.manage.logistics_price.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.Logistics_price;


public interface Logistics_priceService extends Services <Logistics_price> {
	@Override
	public int insert(Logistics_price e);
	
	public List<Logistics_price> selectListByLogid(Logistics_price e);
	
	public List<Logistics_price> selectDefaultByLogid(Logistics_price e);
	
	public int deleteByLogId(int logisticsid);
	
	public List<Logistics_price> selectPriceByArea(Logistics_price e);
}
