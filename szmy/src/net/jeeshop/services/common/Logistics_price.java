package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class Logistics_price extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private int logisticsid;
	private int isdefault;//是否默认 1是 2否
	private int type;//运送方式  1快递 2物流
	private String firstnum;//首次数量
	private String firstprice;//首次价格
	private String extendnum;//续数
	private String extendprice;//续价
	private String areas;//地区
	private String areasid;//地区

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
	public void setIsdefault(int isdefault){
		this.isdefault=isdefault;
	}
	public int getIsdefault(){
		return isdefault;
	}
	public void setLogisticsid(int logisticsid){
		this.logisticsid=logisticsid;
	}
	public int getLogisticsid(){
		return logisticsid;
	}
	public void setFirstprice(String firstprice){
		this.firstprice=firstprice;
	}
	public String getFirstprice(){
		return firstprice;
	}
	public void setExtendnum(String extendnum){
		this.extendnum=extendnum;
	}
	public String getExtendnum(){
		return extendnum;
	}
	public void setAreas(String areas){
		this.areas=areas;
	}
	public String getAreas(){
		return areas;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setExtendprice(String extendprice){
		this.extendprice=extendprice;
	}
	public String getExtendprice(){
		return extendprice;
	}
	public void setFirstnum(String firstnum){
		this.firstnum=firstnum;
	}
	public String getFirstnum(){
		return firstnum;
	}
	public String getAreasid() {
		return areasid;
	}
	public void setAreasid(String areasid) {
		this.areasid = areasid;
	}
	
}

