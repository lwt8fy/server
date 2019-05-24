package net.jeeshop.web.action.front.profit;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.manage.profit.service.ProfitService;

import org.slf4j.LoggerFactory;

public class ProfitAction extends BaseAction<Profit> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ProfitAction.class);
	
	private ProfitService profitService;
	private AccountService accountService;
	

	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/***
	 * 作者：王海洋
	 * 时间：2015年10月21日15:59:06
	 * 内容: 查询用户收益列表
	 */
	@Override
	public String selectList() throws Exception {
		Account account = (Account)this.getSession().getAttribute("user_info");
		if(account==null){
			throw new Exception("用户还没有登录!");
		}
		e.setUserID(account.getId());
		
		Account acc = accountService.selectById(account.getId());
		getSession().setAttribute("user_info", acc);
		//我的收益页面 -》我的收益样式
		this.getRequest().setAttribute("selectLeftMenu", FrontContainer.user_leftMenu_selectList);
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