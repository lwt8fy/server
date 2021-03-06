package net.jeeshop.web.action.manage.appSlaughter;

import java.util.Date;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppSlaughter;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.appSlaughter.service.AppSlaughterService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.manage.profit.service.ProfitService;

import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AppSlaughterAction extends BaseAction<AppSlaughter> {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppSlaughterAction.class);
	private AppUserService appUserService;
private ProfitService profitService;
	
	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}
	private String json;
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	private String uuid;
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	public void setAppSlaughterService(AppSlaughterService appSlaughterService) {
		this.appSlaughterService = appSlaughterService;
	}
	private AppSlaughterService appSlaughterService;
	
	/**
	 * 完善个人信息、认证的用户获得100积分；
	 * @author 滕武超
	 * @date 2016-2-23 下午01:12:43 
	 * @Description:
	 */
	 @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String check() throws Exception {
		appSlaughterService.update(e);
		e=appSlaughterService.selectOne(e);
		String msg="";
		AppUser appUser = new AppUser();
		appUser.setId(e.getUserID());
		if(e.getStatus().equals("2")){
			msg="屠宰场已审核为【通过】！";
			appUser.setMoney((appUser.getMoney()==null?0:appUser.getMoney())+Profit.authentication);
			appUser.setAuthentication("2");
			appUserService.updateMoney(appUser);//修改用户总金额,update不能修改
			Profit profit=new Profit();
			profit.setAccount(appUser.getUsername());
			profit.setUserID(appUser.getId());
			profit.setFinalProfit(Double.valueOf(Profit.authentication));//认证
			profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
			profit.setSource("5");//认证
			profitService.insert(profit);
		}else if(e.getStatus().equals("3")){
			msg="屠宰场已审核为【不通过】！";
			appUser.setAuthentication("3");
			appUserService.update(appUser);
		}
		getRequest().setAttribute("message", msg);
		return toEdit;
	}
	@Override
	public AppSlaughter getE() {
		// TODO Auto-generated method stub
		return this.e;
	}
	@Override
	public void insertAfter(AppSlaughter e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppSlaughter();
		}
	}
	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}

}
