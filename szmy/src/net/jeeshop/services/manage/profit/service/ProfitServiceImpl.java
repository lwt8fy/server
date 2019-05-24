package net.jeeshop.services.manage.profit.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.manage.profit.dao.ProfitDao;

public class ProfitServiceImpl  extends ServersManager<Profit> implements ProfitService{
	
	private ProfitDao profitDao;

	public void setProfitDao(ProfitDao profitDao) {
		this.profitDao = profitDao;
	}

	@Override
	public String getProfitSum(Profit e) {
		return this.profitDao.getProfitSum(e);
	}
	@Override
	public PagerModel getExpenditureList(Profit e){
		return this.profitDao.getExpenditureList(e);
	}
	@Override
	public PagerModel getIncomeList(Profit e){
		return this.profitDao.getIncomeList(e);
	}
}
