package net.jeeshop.services.common;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;


public class AppSdOrder extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String nextTime;
	private String orderid;
	private String name1;
	private String name2;
	private String name2id;
	private String name2phone;
	
	
	
	
	public String getName2phone() {
		return name2phone;
	}
	public void setName2phone(String name2phone) {
		this.name2phone = name2phone;
	}
	public String getName2id() {
		return name2id;
	}
	public void setName2id(String name2id) {
		this.name2id = name2id;
	}
	private List<String> ids;

	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public void setOrderid(String orderid){
		this.orderid=orderid;
	}
	public String getOrderid(){
		return orderid;
	}
	public void setName1(String name1){
		this.name1=name1;
	}
	public String getName1(){
		return name1;
	}
	public void setName2(String name2){
		this.name2=name2;
	}
	public String getName2(){
		return name2;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	
	
}

