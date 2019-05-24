package net.jeeshop.services.manage.logistics.dao;

import net.jeeshop.services.manage.logistics.bean.Logistics;
import net.jeeshop.core.DaoManager;


public interface LogisticsDao extends DaoManager <Logistics> {
	Logistics selectLogisticsByProductId(String productid);
}
