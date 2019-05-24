package net.jeeshop.web.action.front.quote;

import java.util.Date;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Quote;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.quote.service.QuoteService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public class AppQuoteAction extends BaseAction <Quote>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppQuoteAction.class);
	private QuoteService quoteService;
	public void setQuoteService(QuoteService quoteService) {
		this.quoteService=quoteService;
	}
	private AppUserService appUserService;
	private String json;
	private String uuid;
	
	
	private String province;
	private String city;
	private String area;
	private String nsy;
	private String wsy;
	private String tzz;
	private String ym;
	private String dp;

	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";


	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	@Override
	public String insert() {
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				Quote q=new Quote();
				q.setUserName(u.getUsername());
				q.setCreateTime(DateUtil.dateToStr(new Date()));
				List<Quote> list = quoteService.selectList(q);
				for (Quote quote : list) {
					quote.setProvince(province);
					quote.setCity(city);
					quote.setArea(area);
					if(StringUtils.isNotBlank(nsy)&&quote.getProductType().equals("内三元")){
						quote.setPrice(Double.valueOf(nsy));
						nsy=null;
						quoteService.update(quote);
					}
					if(StringUtils.isNotBlank(wsy)&&quote.getProductType().equals("外三元")){
						quote.setPrice(Double.valueOf(wsy));
						wsy=null;
						quoteService.update(quote);
					}
					if(StringUtils.isNotBlank(tzz)&&quote.getProductType().equals("土杂猪")){
						quote.setPrice(Double.valueOf(tzz));
						tzz=null;
						quoteService.update(quote);
					}
					if(StringUtils.isNotBlank(ym)&&quote.getProductType().equals("玉米")){
						quote.setPrice(Double.valueOf(ym));
						ym=null;
						quoteService.update(quote);
					}
					if(StringUtils.isNotBlank(dp)&&quote.getProductType().equals("豆粕")){
						quote.setPrice(Double.valueOf(dp));
						dp=null;
						quoteService.update(quote);
					}
				}
				q.setProvince(province);
				q.setCity(city);
				q.setArea(area);
				q.setSource("猪贸通");
				if(StringUtils.isNotBlank(nsy)){
					q.setPrice(Double.valueOf(nsy));
					q.setType("生猪");
					q.setProductType("内三元");
					q.setMobile(u.getPhone());
					quoteService.insert(q);
				}
				if(StringUtils.isNotBlank(wsy)){
					q.setPrice(Double.valueOf(wsy));
					q.setType("生猪");
					q.setProductType("外三元");
					q.setMobile(u.getPhone());
					quoteService.insert(q);
				}
				
				if(StringUtils.isNotBlank(tzz)){
					q.setPrice(Double.valueOf(tzz));
					q.setType("生猪");
					q.setProductType("土杂猪");
					q.setMobile(u.getPhone());
					quoteService.insert(q);
				}
				if(StringUtils.isNotBlank(ym)){
					q.setPrice(Double.valueOf(ym));
					q.setType("玉米");
					q.setProductType("15%水分");
					q.setMobile(u.getPhone());
					quoteService.insert(q);
				}
				if(StringUtils.isNotBlank(dp)){
					q.setPrice(Double.valueOf(dp));
					q.setType("豆粕");
					q.setProductType("43%蛋白");
					q.setMobile(u.getPhone());
					quoteService.insert(q);
				}
			}
			json=SUCCESS_MSG;
			}catch (Exception e) {
				e.printStackTrace();
				json = Exception_MSG;
			}
			return "json";
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
					e.setUserName(u.getUsername());
					e.setCreateTime(DateUtil.dateToStr(new Date()));
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
	/**
	 * 获取附近报价信息
	 * @author 滕武超
	 * @date 2016-1-19 下午02:31:50 
	 * @Description:
	 */
	public String getNearbyList() throws Exception {
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
				super.selectList();
				pager.setOffset(os);
				JSONObject jsonObject = JSONObject.fromObject(pager);
				jsonObject.put("success", "1");
				json=jsonObject.toString();
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}

	@Override
	public void insertAfter(Quote e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Quote();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public Quote getE() {
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

	public String getNsy() {
		return nsy;
	}

	public void setNsy(String nsy) {
		this.nsy = nsy;
	}

	public String getWsy() {
		return wsy;
	}

	public void setWsy(String wsy) {
		this.wsy = wsy;
	}

	public String getTzz() {
		return tzz;
	}

	public void setTzz(String tzz) {
		this.tzz = tzz;
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	public String getDp() {
		return dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
