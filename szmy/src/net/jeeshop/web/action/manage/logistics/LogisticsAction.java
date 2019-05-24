package net.jeeshop.web.action.manage.logistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.common.Logistics_free;
import net.jeeshop.services.common.Logistics_price;
import net.jeeshop.services.manage.area.AreaService;
import net.jeeshop.services.manage.logistics.bean.Logistics;
import net.jeeshop.services.manage.logistics.service.LogisticsService;
import net.jeeshop.services.manage.logistics_free.service.Logistics_freeService;
import net.jeeshop.services.manage.logistics_price.service.Logistics_priceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

public class LogisticsAction extends BaseAction <Logistics>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogisticsAction.class);
	private LogisticsService logisticsService;
	private Logistics_priceService logistics_priceService;
	private Logistics_freeService logistics_freeService;
	private AreaService areaService;
	private String jsonStr;
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public Logistics_freeService getLogistics_freeService() {
		return logistics_freeService;
	}

	public void setLogistics_freeService(Logistics_freeService logisticsFreeService) {
		logistics_freeService = logisticsFreeService;
	}

	public Logistics_priceService getLogistics_priceService() {
		return logistics_priceService;
	}

	public void setLogistics_priceService(
			Logistics_priceService logisticsPriceService) {
		logistics_priceService = logisticsPriceService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public LogisticsService getLogisticsService() {
		return logisticsService;
	}

	public void setLogisticsService(LogisticsService logisticsService) {
		this.logisticsService=logisticsService;
	}

	@Override
	public void insertAfter(Logistics e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Logistics();
		}
	}

	@Override
	public void selectListAfter() {
		pager.setPagerUrl("logistics!selectList.action");
	}

	@Override
	public Logistics getE() {
		return this.e;
	}
	@Override
	public String selectList() throws Exception{
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if("2".equals(u.getRid())){
			e.setAccount(u.getId());
		}
		this.initPageSelect();
		int offset = pager.getOffset();//分页偏移量
		if (offset < 0)
			offset = 0;
		getE().setOffset(offset);
		pager = getServer().selectPageList(getE());
		if(pager==null)pager = new Logistics();
		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1)
				/ pager.getPageSize());
		selectListAfter();
		return "toList";
	}
	
	@Override
	public String insert() throws Exception{
		String name=getRequest().getParameter("name");
		String deliverytime=getRequest().getParameter("deliverytime");
		String isfree=getRequest().getParameter("isfree");
		String type=getRequest().getParameter("type");
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		Logistics e=new Logistics();
		
		e.setAccount(u.getId());
		e.setName(name);
		e.setDeliverytime(deliverytime);
		e.setIsfree(isfree);
		e.setType(type);
		
		int logid=this.logisticsService.insert(e);
		
		Map<String,Object> map=new HashMap<String,Object>();
		if(logid>0){
			map.put("type", "success");
			map.put("logid", logid);
		}else{
			map.put("type", "error");
		}
		jsonStr=JSONObject.fromObject(map).toString();
		
		return "toJson";
	}
	
	@Override
	public String update() throws Exception {
		String id=getRequest().getParameter("id");
		String name=getRequest().getParameter("name");
		String deliverytime=getRequest().getParameter("deliverytime");
		String isfree=getRequest().getParameter("isfree");
		String type=getRequest().getParameter("type");
		
		Logistics e=new Logistics();
		e.setId(id);
		e.setName(name);
		e.setDeliverytime(deliverytime);
		e.setIsfree(isfree);
		e.setType(type);
		
		int result=this.logisticsService.update(e);
		Map<String,Object> map=new HashMap<String,Object>();
		if(result>0){
			map.put("type", "success");
		}else{
			map.put("type", "error");
		}
		jsonStr=JSONObject.fromObject(map).toString();
		return "toJson";
	}

	@Override
	public String toEdit() throws Exception{
		e = getServer().selectOne(getE());
		Logistics_price kd=new Logistics_price();
		kd.setLogisticsid(Integer.parseInt(e.getId()));
		kd.setType(1);
		kd.setIsdefault(1);
		List<Logistics_price> kddefaultList=this.logistics_priceService.selectDefaultByLogid(kd);
		if (kddefaultList.size()>0&&kddefaultList!=null) {
			kd.setIsdefault(2);
			List<Logistics_price> kdpriceList=this.logistics_priceService.selectDefaultByLogid(kd);
			e.setKdpriceList(kdpriceList);
			e.setKddefaultList(kddefaultList);
		}
		Logistics_price wl=new Logistics_price();
		wl.setLogisticsid(Integer.parseInt(e.getId()));
		wl.setType(2);
		wl.setIsdefault(1);
		List<Logistics_price> wldefaultList=this.logistics_priceService.selectDefaultByLogid(wl);
		if (wldefaultList.size()>0&&wldefaultList!=null) {
			wl.setIsdefault(2);
			List<Logistics_price> wlpriceList=this.logistics_priceService.selectDefaultByLogid(wl);
			e.setWlpriceList(wlpriceList);
			e.setWldefaultList(wldefaultList);
		}
		List<Logistics_free> freeList=this.logistics_freeService.selectListByLogid(e.getId());
		if (freeList.size()>0&&freeList!=null) {
			e.setFreeList(freeList);
		}
		return "toEdit";
	}
	
	public String selectListForSelect() throws Exception{
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if("2".equals(u.getRid())){
			e.setAccount(u.getId());
		}
		List<Logistics> lists=this.logisticsService.selectList(e);
		List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
		if (lists.size()>0&&lists!=null) {
			for (int i = 0; i < lists.size(); i++) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("text", lists.get(i).getName());
				map.put("value", lists.get(i).getId());
				listmap.add(map);
			}
		}
		JSONArray json = JSONArray.fromObject(lists);
		String jsonStr = json.toString();
		super.write(jsonStr);
		return null;
	}
}
