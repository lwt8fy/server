package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class AppLocation extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String userName;//用户名
	private String province;//省
	private String city;//市
	private String area;//区
	private String address;//详细地址
	private String createTime;//日期
	
	private Double x;
	private Double y;
	

	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return area;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getAddress(){
		return address;
	}
	public void setProvince(String province){
		this.province=province;
	}
	public String getProvince(){
		return province;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setY(Double y){
		this.y=y;
	}
	public Double getY(){
		return y;
	}
	public void setX(Double x){
		this.x=x;
	}
	public Double getX(){
		return x;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return city;
	}
}

