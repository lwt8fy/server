package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class ProductSd extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private Integer num;
	private Integer status;
	private String productID;
	private String activityID;
	private String nextTime;
	private String whereSql;
	
	
	
	
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public String getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setNum(Integer num){
		this.num=num;
	}
	public Integer getNum(){
		return num;
	}
	
	public void setProductID(String productID){
		this.productID=productID;
	}
	public String getProductID(){
		return productID;
	}
	public void setActivityID(String activityID){
		this.activityID=activityID;
	}
	public String getActivityID(){
		return activityID;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

