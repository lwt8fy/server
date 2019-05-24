package net.jeeshop.services.manage.logistics_price.dao;

import java.util.List;

import net.jeeshop.services.common.Logistics_price;
import net.jeeshop.core.DaoManager;


public interface Logistics_priceDao extends DaoManager <Logistics_price> {
	List<Logistics_price> selectListByLogid(Logistics_price e);
	List<Logistics_price> selectDefaultByLogid(Logistics_price e);
	int deleteByLogId(int logisticsid);
	List<Logistics_price> selectPriceByArea(Logistics_price e);
}
