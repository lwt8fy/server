package net.jeeshop.web.action.manage.logistics_free;

import java.util.HashMap;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.Logistics_free;
import net.jeeshop.services.manage.logistics_free.service.Logistics_freeService;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

public class Logistics_freeAction extends BaseAction <Logistics_free>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logistics_freeAction.class);
	private Logistics_freeService logistics_freeService;
	
	private String jsonStr;
	
	public String getJsonStr() {
		return jsonStr;
	}

	public Logistics_freeService getLogistics_freeService() {
		return logistics_freeService;
	}

	public void setLogistics_freeService(Logistics_freeService logisticsFreeService) {
		logistics_freeService = logisticsFreeService;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	@Override
	public void insertAfter(Logistics_free e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Logistics_free();
		}
	}

	@Override
	public void selectListAfter() {
		pager.setPagerUrl("logistics_free!selectList.action");
	}

	@Override
	public Logistics_free getE() {
		return this.e;
	}
	@Override
	public String insert() throws Exception{
		String logid=getRequest().getParameter("logid");
		String areas=getRequest().getParameter("areas");
		String areasid=getRequest().getParameter("areasid");
		String type=getRequest().getParameter("type");
		String conditiontype=getRequest().getParameter("conditiontype");
		String conditions=getRequest().getParameter("conditions");
		
		Logistics_free e=new Logistics_free();
		e.setLogisticsid(Integer.parseInt(logid));
		e.setAreas(areas);
		e.setAreasid(areasid);
		e.setConditiontype(Integer.parseInt(conditiontype));
		e.setConditions(Integer.parseInt(conditions));
		e.setType(Integer.parseInt(type));
		
		int result=this.logistics_freeService.insert(e);
		
		Map<String,Object> map=new HashMap<String,Object>();
		if (result>0) {
			map.put("type", "success");
		} else {
			map.put("type", "error");
		}
		
		jsonStr=JSONObject.fromObject(map).toString();
		
		return "toJson";
	}
	
	public String deleteByLogId() throws Exception{
		String logid=getRequest().getParameter("logid");
		int result=this.logistics_freeService.deleteByLogId(Integer.parseInt(logid));
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
