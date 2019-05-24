package net.jeeshop.web.action.manage.profit;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.manage.profit.service.ProfitService;

import org.slf4j.LoggerFactory;

public class ProfitAction extends BaseAction<Profit> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ProfitAction.class);
	
	private ProfitService profitService;
	

	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}
	@Override
	public String selectList() throws Exception {
		Account account = (Account)this.getSession().getAttribute("user_info");
		if(!"1".equals(account.getRid())){ 
			e.setUserID(account.getId());
		}
		this.getRequest().setAttribute("sum", this.profitService.getProfitSum(e));
		return super.selectList();
	}

	@Override
	public void insertAfter(Profit e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new Profit();
		}
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub

	}




	@Override
	public Profit getE() {
		// TODO Auto-generated method stub
		return this.e;
	}


	
}