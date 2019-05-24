package net.jeeshop.services.manage.tuiGuang.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.TuiGuang;
import net.jeeshop.services.manage.tuiGuang.dao.TuiGuangDao;

public class TuiGuangServiceImpl  extends ServersManager<TuiGuang> implements TuiGuangService{
	private TuiGuangDao tuiGuangDao;

	public void setTuiGuangDao(TuiGuangDao tuiGuangDao) {
		this.tuiGuangDao = tuiGuangDao;
	}

	

	
}
