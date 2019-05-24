package net.jeeshop.web.action.manage.appPigFarm;

import java.util.Date;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppPigFarm;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.appPigFarm.service.AppPigFarmService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.manage.profit.service.ProfitService;

import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AppPigFarmAction extends BaseAction<AppPigFarm> {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppPigFarmAction.class);
	private AppUserService appUserService;
	private ProfitService profitService;
	private AppPigFarmService appPigFarmService;
	private String json;
	private String uuid;
	
	
	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}


	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	
	public void setAppPigFarmService(AppPigFarmService appPigFarmService) {
		this.appPigFarmService = appPigFarmService;
	}
	/**
	 * 修改为
	 * （1）完善个人信息、认证的用户获得100积分；
		（2）认证猪场：如果只有一级推荐人，推荐人获得2000积分，如果有二级推荐人，则1级2级推荐人各获得1000积分；（获得积分按照认证成功为准）
		
	 * @author 滕武超
	 * @date 2016-2-23 下午01:11:10 
	 * @Description:
	 */
	 @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String check() throws Exception {
		appPigFarmService.update(e);
		e=appPigFarmService.selectOne(e);
		String msg="";
		AppUser appUser = new AppUser();
		appUser.setId(e.getUserID());
		if(e.getStatus().equals("2")){
			
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
			
			if(appUser.getPresenter()!=null&&!appUser.getPresenter().equals("admin")&&!appUser.getPresenter().equals("0")){
				AppUser au= new AppUser();
				au.setUsername(appUser.getPresenter());
				au = appUserService.selectOne(au);
				
				if(au.getPresenter()!=null){
					if(au.getPresenter().equals("0")){
						au.setMoney((au.getMoney()==null?0:au.getMoney())+Profit.authentication_zc);
						appUserService.updateMoney(au);
						profit=new Profit();
						profit.setAccount(au.getUsername());
						profit.setUserID(au.getId());
						profit.setFinalProfit(Double.valueOf(Profit.authentication_zc));//认证
						profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
						profit.setSource("8");//认证
						profit.setPresentee(appUser.getUsername());
						profitService.insert(profit);
					}else{
						AppUser au2= new AppUser();
						au2.setUsername(au.getPresenter());
						au2 = appUserService.selectOne(au2);
						if(au2.getPresenter()!=null&&au2.getPresenter().equals("0")){
							au.setMoney((au.getMoney()==null?0:au.getMoney())+(Profit.authentication_zc/2));
							appUserService.updateMoney(au);
							profit=new Profit();
							profit.setAccount(au.getUsername());
							profit.setUserID(au.getId());
							profit.setFinalProfit(Double.valueOf(Profit.authentication_zc/2));//认证
							profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
							profit.setSource("8");//认证
							profit.setPresentee(appUser.getUsername());
							profitService.insert(profit);
							
							
							au2.setMoney((au2.getMoney()==null?0:au2.getMoney())+(Profit.authentication_zc/2));
							appUserService.updateMoney(au2);
							profit=new Profit();
							profit.setAccount(au2.getUsername());
							profit.setUserID(au2.getId());
							profit.setFinalProfit(Double.valueOf(Profit.authentication_zc/2));//认证
							profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
							profit.setSource("8");//认证
							profit.setPresentee(appUser.getUsername());
							profitService.insert(profit);
						}
					}
				}
				
			}
			
			msg="猪场已审核为【通过】！";
		}else if(e.getStatus().equals("3")){
			msg="猪场已审核为【不通过】！";
			appUser.setAuthentication("3");
			appUserService.update(appUser);
		}
		getRequest().setAttribute("message", msg);
		
		return toEdit;
	}

	@Override
	public void insertAfter(AppPigFarm e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppPigFarm();
		}
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub

	}


	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public AppPigFarm getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
}
