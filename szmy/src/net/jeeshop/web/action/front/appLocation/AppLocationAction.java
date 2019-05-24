package net.jeeshop.web.action.front.appLocation;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppLocation;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.front.appLocation.service.AppLocationService;
import net.jeeshop.services.front.appUser.service.AppUserService;

import org.slf4j.LoggerFactory;

public class AppLocationAction extends BaseAction <AppLocation>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppLocationAction.class);
	private AppLocationService appLocationService;
	private AppUserService appUserService;
	
	private String json;
	private String uuid;
	
	public void setAppLocationService(AppLocationService appLocationService) {
		this.appLocationService=appLocationService;
	}
	
	
	
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}



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


	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	@Override
	public String insert() throws Exception {
		// TODO Auto-generated method stub
		
		try{
			if(uuid==null||uuid.equals("")){
				json = ERROR_MSG;
			}else{
				AppUser u = appUserService.selectByUuid(uuid);
				if(u==null){
				json = ERROR_MSG;
				}else{
			e.setCreateTime(DateUtil.dateToStrSS());
			appLocationService.insert(e);
			json =SUCCESS_MSG;
				}
			}
		}catch (Exception e) {
			System.out.println(e);
			json = Exception_MSG;
		}
		return "json";
	}
	
	@Override
	public void insertAfter(AppLocation e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new AppLocation();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public AppLocation getE() {
		return this.e;
	}
}
