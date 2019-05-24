package net.jeeshop.services.manage.logistics_free.dao;

import java.util.List;

import net.jeeshop.services.common.Logistics_free;
import net.jeeshop.core.DaoManager;


public interface Logistics_freeDao extends DaoManager <Logistics_free> {
	List<Logistics_free> selectListByLogid(String logisticsid);
	int deleteByLogId(int logisticsid);
	List<Logistics_free> selectFreeByArea(Logistics_free e);
}
