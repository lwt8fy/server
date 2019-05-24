package net.jeeshop.web.action.front.profit;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

public class AppProfitAction extends BaseAction <Profit>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppProfitAction.class);
	private ProfitService profitService;
	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}

	private AppUserService appUserService;
	private String json;
	private String uuid;
	

	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";


	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	
	@Override
	public String selectList() throws Exception {
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
					e.setUserID(u.getId());
					super.selectList();
					pager.setOffset(os);
					JSONObject jsonObject = JSONObject.fromObject(pager);
					jsonObject.put("success", "1");
					json=jsonObject.toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}

	@Override
	public void insertAfter(Profit e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Profit();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public Profit getE() {
		return this.e;
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

	
}
