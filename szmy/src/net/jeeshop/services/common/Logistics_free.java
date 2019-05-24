package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class Logistics_free extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private int logisticsid;
	private int conditiontype;//条件
	private int conditions;//数量
	private int type;//运送方式
	private String areas;//地区
	private String areasid;
	
	private String province;
	private String city;
	private String area;

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
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setLogisticsid(int logisticsid){
		this.logisticsid=logisticsid;
	}
	public int getLogisticsid(){
		return logisticsid;
	}
	public void setConditions(int conditions){
		this.conditions=conditions;
	}
	public int getConditions(){
		return conditions;
	}
	public void setAreas(String areas){
		this.areas=areas;
	}
	public String getAreas(){
		return areas;
	}
	public void setConditiontype(int conditiontype){
		this.conditiontype=conditiontype;
	}
	public int getConditiontype(){
		return conditiontype;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public String getAreasid() {
		return areasid;
	}
	public void setAreasid(String areasid) {
		this.areasid = areasid;
	}
	
}

