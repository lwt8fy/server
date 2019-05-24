package net.jeeshop.web.action.front.company;

import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.company.CompanyService;
import net.jeeshop.services.front.favoriteShop.service.FavoriteShopService;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class FavoriteShopAction extends BaseAction <FavoriteShop>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FavoriteShopAction.class);
	private FavoriteShopService favoriteShopService;
	private AppUserService appUserService;
	private CompanyService companyservice;
	

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

	
	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	public void setAppUserService(AppUserService appUserService) {
	}

	public void setCompanyservice(CompanyService companyservice) {
		this.companyservice = companyservice;
	}

	public void setFavoriteShopService(FavoriteShopService favoriteShopService) {
		this.favoriteShopService=favoriteShopService;
	}
//获得Company列表
	@Override
	public String selectList() throws Exception {
		AppUser user=appUserService.selectByUuid(uuid);
		if(user==null){
			json=ERROR_MSG;
		}else{
			e.setAccount(user.getUsername());
			List<Company> companyList=favoriteShopService.companyList(e);
			JSONObject jsonobject=new JSONObject();
			jsonobject.put("list",companyList);
			json=jsonobject.toString();
		}
		return "json";
		
	}
	//删除收藏店铺
	public String deleteById(){
		AppUser user=appUserService.selectByUuid(uuid);
		if(user==null){
			json=ERROR_MSG;
		}else{
			e.setAccount(user.getUsername());
			favoriteShopService.delete(e);
		}
		return "json";
		
	}

	@Override
	public void insertAfter(FavoriteShop e) {
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new FavoriteShop();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public FavoriteShop getE() {
		return this.e;
	}
}
