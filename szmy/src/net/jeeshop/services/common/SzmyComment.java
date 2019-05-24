package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class SzmyComment extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String content;//评论内容
	private Double star;//评价星级
	private String createTime;//评论时间
	private String orderType;//订单类型(出售:1,收购:2)(物流:3 )(平台:4)
	private String orderID;//订单id
	private String productID;//商品Id
	private String productName;//商品名称
	private String userName;//用户名
	private String bpjrType;//被评价人用户类型(买方:1)(卖方:2)
	private String parentID;//父id(一级为0)
private String bpjr;//被评价人

	private String orderdetailID;//订单详情id(平台用)
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setParentID(String parentID){
		this.parentID=parentID;
	}
	public String getParentID(){
		return parentID;
	}
	public void setOrderID(String orderID){
		this.orderID=orderID;
	}
	public String getOrderID(){
		return orderID;
	}
	public void setOrderType(String orderType){
		this.orderType=orderType;
	}
	public String getOrderType(){
		return orderType;
	}
	public void setOrderdetailID(String orderdetailID){
		this.orderdetailID=orderdetailID;
	}
	public String getOrderdetailID(){
		return orderdetailID;
	}
	public void setStar(Double star){
		this.star=star;
	}
	public Double getStar(){
		return star;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setProductName(String productName){
		this.productName=productName;
	}
	public String getProductName(){
		return productName;
	}
	public void setProductID(String productID){
		this.productID=productID;
	}
	public String getProductID(){
		return productID;
	}
	
	public String getBpjrType() {
		return bpjrType;
	}
	public void setBpjrType(String bpjrType) {
		this.bpjrType = bpjrType;
	}
	public String getBpjr() {
		return bpjr;
	}
	public void setBpjr(String bpjr) {
		this.bpjr = bpjr;
	}
	
}

