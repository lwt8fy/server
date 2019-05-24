package net.jeeshop.web.action.manage.appVehicleCompany;

import org.slf4j.LoggerFactory;
import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppVehicleCompany;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.appVehicleCompany.service.AppVehicleCompanyService;

public class AppVehicleCompanyAction extends BaseAction<AppVehicleCompany>{
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppVehicleCompanyAction.class);
	private AppUserService appUserService;
	
	private AppVehicleCompanyService appVehicleCompanyService;
	public void setAppVehicleCompanyService(
			AppVehicleCompanyService appVehicleCompanyService) {
		this.appVehicleCompanyService = appVehicleCompanyService;
	}
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	private String json;
	private String uuid;
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
	public String check() throws Exception {
		appVehicleCompanyService.update(e);
		e=appVehicleCompanyService.selectOne(e);
//		String msg="";
//		if(e.getStatus().equals("1")){
//			msg="已审核为【通过】！";
//		}else if(e.getStatus().equals("2")){
//			msg="已审核为【不通过】！";
//		}
//		getRequest().setAttribute("message", msg);
		
		e=new AppVehicleCompany();
		
		return selectList();
	}


	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppVehicleCompany();
		}
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertAfter(AppVehicleCompany e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AppVehicleCompany getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

}
