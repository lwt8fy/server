package net.jeeshop.services.manage.logistics_price.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.Logistics_price;
import net.jeeshop.services.manage.logistics_price.dao.Logistics_priceDao;


public class Logistics_priceServiceImpl extends ServersManager <Logistics_price> implements Logistics_priceService {
	private Logistics_priceDao logistics_priceDao;

	public void setLogistics_priceDao(Logistics_priceDao logistics_priceDao) { 
		this.logistics_priceDao=logistics_priceDao;
	}
	@Override
	public int insert(Logistics_price e){
		return this.logistics_priceDao.insert(e);
	}
	@Override
	public List<Logistics_price> selectListByLogid(Logistics_price e){
		return this.logistics_priceDao.selectListByLogid(e);
	}
	
	@Override
	public List<Logistics_price> selectDefaultByLogid(Logistics_price e){
		return this.logistics_priceDao.selectDefaultByLogid(e);
	}
	
	@Override
	public int deleteByLogId(int logisticsid){
		return this.logistics_priceDao.deleteByLogId(logisticsid);
	}
	@Override
	public List<Logistics_price> selectPriceByArea(Logistics_price e){
		return this.logistics_priceDao.selectPriceByArea(e);
	}
}
