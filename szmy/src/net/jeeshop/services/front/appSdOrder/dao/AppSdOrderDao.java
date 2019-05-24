package net.jeeshop.services.front.appSdOrder.dao;

import java.util.List;

import net.jeeshop.services.common.AppSdOrder;
import net.jeeshop.core.DaoManager;


public interface AppSdOrderDao extends DaoManager <AppSdOrder> {
	List<String> selectSdUser();
}
