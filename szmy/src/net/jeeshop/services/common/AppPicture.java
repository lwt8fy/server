package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class AppPicture  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String orderID;
	private String vehicleID;
	private String picture;
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	
	
	
}
