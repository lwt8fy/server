package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class ActivityCount extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private Integer count;
	private String userName;
	private String productID;
	private String activityID;

	public ActivityCount() {
		// TODO Auto-generated constructor stub
	}
	public ActivityCount(String userName,String activityID,String productID) {
		// TODO Auto-generated constructor stub
		this.userName=userName;
		this.activityID=activityID;
		this.productID=productID;
	}
	
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setCount(Integer count){
		this.count=count;
	}
	public Integer getCount(){
		return count;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
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
}

