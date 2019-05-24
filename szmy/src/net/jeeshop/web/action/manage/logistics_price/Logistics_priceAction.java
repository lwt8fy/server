package net.jeeshop.web.action.manage.logistics_price;

import java.util.HashMap;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.Logistics_price;
import net.jeeshop.services.manage.logistics_price.service.Logistics_priceService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public class Logistics_priceAction extends BaseAction <Logistics_price>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logistics_priceAction.class);
	private Logistics_priceService logistics_priceService;
	private String jsonStr;
	
	public String getJsonStr() {
		return jsonStr;
	}
	
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public void setLogistics_priceService(Logistics_priceService logistics_priceService) {
		this.logistics_priceService=logistics_priceService;
	}

	@Override
	public void insertAfter(Logistics_price e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Logistics_price();
		}
	}

	@Override
	public void selectListAfter() {
		pager.setPagerUrl("logistics_price!selectList.action");
	}

	@Override
	public Logistics_price getE() {
		return this.e;
	}
	@Override
	public String insert() throws Exception{
		String logisticsid=getRequest().getParameter("logid");
		String type=getRequest().getParameter("type");
		String isdefault=getRequest().getParameter("isdefault");
		String firstnum=getRequest().getParameter("firstnum");
		String firstprice=getRequest().getParameter("firstprice");
		String extendnum=getRequest().getParameter("extendnum");
		String extendprice=getRequest().getParameter("extendprice");
		String areas=getRequest().getParameter("areas");
		String areasid=getRequest().getParameter("areasid");

		Logistics_price e=new Logistics_price();
		e.setLogisticsid(Integer.parseInt(logisticsid));
		e.setType(Integer.parseInt(type));
		
		if(StringUtils.isNotBlank(isdefault)){
			e.setIsdefault(Integer.parseInt(isdefault));
		}
		if(StringUtils.isNotBlank(firstnum)){
			e.setFirstnum(firstnum);
		}
		if(StringUtils.isNotBlank(firstprice)){
			e.setFirstprice(firstprice);
		}
		if(StringUtils.isNotBlank(extendprice)){
			e.setExtendprice(extendprice);
		}
		if(StringUtils.isNotBlank(extendnum)){
			e.setExtendnum(extendnum);
		}
		if(StringUtils.isNotBlank(areas)){
			e.setAreas(areas);
		}
		if(StringUtils.isNotBlank(areasid)){
			e.setAreasid(areasid);
		}
		
		this.logistics_priceService.insert(e);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", "success");
		
		jsonStr=JSONObject.fromObject(map).toString();
		return "toJson";
	}
	@Override
	public String update() throws Exception {
		return super.update();
	}
	public String deleteByLogId() throws Exception{
		String logid=getRequest().getParameter("logid");
		int result=this.logistics_priceService.deleteByLogId(Integer.parseInt(logid));
		Map<String,Object> map=new HashMap<String,Object>();
		if (result>0) {
			map.put("type", "success");
		} else {
			map.put("type", "error");
		}
		jsonStr=JSONObject.fromObject(map).toString();
		return "toJson";
	}
}
