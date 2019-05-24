package net.jeeshop.services.front.appOrder.service;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppOrder;

public interface AppOrderService  extends Services<AppOrder>{

	void updatePayStatus(AppOrder e);
	
	Double countAppOrderJye(AppOrder e);

}
