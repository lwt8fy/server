package net.jeeshop.services.manage.profit.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Profit;

public interface ProfitDao  extends DaoManager<Profit> {
	public String getProfitSum(Profit e);
	public PagerModel getExpenditureList(Profit e);
	public PagerModel getIncomeList(Profit e);
}
