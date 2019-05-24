package net.jeeshop.services.front.appSdOrder.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppSdOrder;


public interface AppSdOrderService extends Services <AppSdOrder> {
	 List<String> selectSdUser();
}
