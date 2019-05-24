package net.jeeshop.services.manage.profit.service;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Profit;

public interface ProfitService  extends Services<Profit>{

	public String getProfitSum(Profit e);
	
	public PagerModel getExpenditureList(Profit e);
	
	public PagerModel getIncomeList(Profit e);
}
