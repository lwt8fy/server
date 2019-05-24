package net.jeeshop.services.front.appVehicle.service;


import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppVehicle;

public interface AppVehicleService  extends Services<AppVehicle>{
	
	public void deleteById(String id) throws Exception;

}
