package net.jeeshop.services.manage.logistics.bean;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.services.common.Logistics_free;
import net.jeeshop.services.common.Logistics_price;

public class Logistics extends net.jeeshop.services.common.Logistics implements Serializable {
	private static final long serialVersionUID = 1L;
		
	private List<Logistics_price> kdpriceList;
	private List<Logistics_price> kddefaultList;
	private List<Logistics_price> wlpriceList;
	private List<Logistics_price> wldefaultList;
	private List<Logistics_free> freeList;
	
	public List<Logistics_price> getKdpriceList() {
		return kdpriceList;
	}
	public void setKdpriceList(List<Logistics_price> kdpriceList) {
		this.kdpriceList = kdpriceList;
	}
	public List<Logistics_price> getWlpriceList() {
		return wlpriceList;
	}
	public void setWlpriceList(List<Logistics_price> wlpriceList) {
		this.wlpriceList = wlpriceList;
	}
	public List<Logistics_free> getFreeList() {
		return freeList;
	}
	public void setFreeList(List<Logistics_free> freeList) {
		this.freeList = freeList;
	}
	public List<Logistics_price> getKddefaultList() {
		return kddefaultList;
	}
	public void setKddefaultList(List<Logistics_price> kddefaultList) {
		this.kddefaultList = kddefaultList;
	}
	public List<Logistics_price> getWldefaultList() {
		return wldefaultList;
	}
	public void setWldefaultList(List<Logistics_price> wldefaultList) {
		this.wldefaultList = wldefaultList;
	}
}

