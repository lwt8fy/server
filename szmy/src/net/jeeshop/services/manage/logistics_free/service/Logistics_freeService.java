package net.jeeshop.services.manage.logistics_free.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.Logistics_free;


public interface Logistics_freeService extends Services <Logistics_free> {
	@Override
	public int insert(Logistics_free e);
	
	public List<Logistics_free> selectListByLogid(String logisticsid);
	
	public int deleteByLogId(int logisticsid);
	
	public List<Logistics_free> selectFreeByArea(Logistics_free e);
}
