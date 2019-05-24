package net.jeeshop.services.manage.logistics_free.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.logistics_free.dao.Logistics_freeDao;
import net.jeeshop.services.manage.logistics_free.service.Logistics_freeService;
import net.jeeshop.services.common.Logistics_free;


public class Logistics_freeServiceImpl extends ServersManager <Logistics_free> implements Logistics_freeService {
	private Logistics_freeDao logistics_freeDao;

	public void setLogistics_freeDao(Logistics_freeDao logistics_freeDao) { 
		this.logistics_freeDao=logistics_freeDao;
	}
	
	@Override
	public int insert(Logistics_free e){
		return this.logistics_freeDao.insert(e);
	}
	@Override
	public List<Logistics_free> selectListByLogid(String logisticsid){
		return this.logistics_freeDao.selectListByLogid(logisticsid);
	}
	
	@Override
	public int deleteByLogId(int logisticsid){
		return this.logistics_freeDao.deleteByLogId(logisticsid);
	}
	
	@Override
	public List<Logistics_free> selectFreeByArea(Logistics_free e){
		return this.logistics_freeDao.selectFreeByArea(e);
	}
}
