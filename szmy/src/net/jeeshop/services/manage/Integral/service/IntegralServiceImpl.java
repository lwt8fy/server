package net.jeeshop.services.manage.Integral.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.Integral;
import net.jeeshop.services.manage.Integral.dao.IntegralDao;

public class IntegralServiceImpl  extends ServersManager<Integral> implements IntegralService{
	@SuppressWarnings("unused")
	private IntegralDao IntegralDao;

	public void setIntegralDao(IntegralDao integralDao) {
		IntegralDao = integralDao;
	}

	
	

	
}
