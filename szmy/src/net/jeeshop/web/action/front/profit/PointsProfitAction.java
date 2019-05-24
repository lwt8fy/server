package net.jeeshop.web.action.front.profit;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.manage.profit.service.ProfitService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public class PointsProfitAction extends BaseAction<Profit> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ProfitAction.class);
	
	private ProfitService profitService;
	private AccountService accountService;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public String selectList() throws Exception {
		Account account = (Account)this.getSession().getAttribute("user_info");
		if(account==null){
			throw new Exception("用户还没有登录!");
		}
		e.setUserID(account.getId());

		if (StringUtils.isBlank(type)) {
			type="1";
		}
		
		this.initPageSelect();
		int offset = pager.getOffset();//分页偏移量
		if (offset < 0)
			offset = 0;
		getE().setOffset(offset);
		
		if("3".equals(type)){
			pager = profitService.getExpenditureList(e);
		}else if("2".equals(type)){
			pager = profitService.getIncomeList(e);
		}else if ("1".equals(type)) {
			pager = getServer().selectPageList(e);
		}
		
		if(pager==null)pager = new Profit();
		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		selectListAfter(type);

		getSession().setAttribute("jf_Menu", "zhanghu");
		getSession().setAttribute("account_menu", type);
		
		return "toList";
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

	protected void selectListAfter(String type) {
		pager.setPagerUrl("pointsProfit!selectList.action");
	}

	@Override
	public Profit getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("pointsProfit!selectList.action");
	}
	
	public String earn(){
		getSession().setAttribute("jf_Menu", "zhuanqu");
		return "toEarn";
	}
}