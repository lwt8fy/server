package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class Logistics extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String deliverytime;//发货日期
	private String isfree;//是否包邮	1是 2否
	private String name;//运费模版名称
	private String type;//计价方式	1 按件数
	private String createtime;//创建/修改日期
	private String account;//创建人
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public void setCreatetime(String createtime){
		this.createtime=createtime;
	}
	public String getCreatetime(){
		return createtime;
	}
	public void setDeliverytime(String deliverytime){
		this.deliverytime=deliverytime;
	}
	public String getDeliverytime(){
		return deliverytime;
	}
	public void setIsfree(String isfree){
		this.isfree=isfree;
	}
	public String getIsfree(){
		return isfree;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}

