package net.jeeshop.services.front.appOrder.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.common.AppOrder;

public interface AppOrderDao  extends DaoManager<AppOrder> {

	void updatePayStatus(AppOrder e);
	
	 Double countAppOrderJye(AppOrder e);
	
}
