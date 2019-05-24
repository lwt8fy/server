package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class AppNatural extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String province;//省
	private String city;//市
	private String area;//县
	 private String realName;//真实姓名
	 private String idCard;//身份证
	 private String cardImg;//证件图片
	 private String phone;//手机号
	 private String status;//状态:1为申请,2为通过,3不通过
	 private String username;//关联用户名
	 private String createTime;//申请时间
	 private String reason;//不通过原因
	 private String remark;//备注

	 
	 private String success="1";//成功状态0,1
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
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setIdCard(String idCard){
		this.idCard=idCard;
	}
	public String getIdCard(){
		return idCard;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return area;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setReason(String reason){
		this.reason=reason;
	}
	public String getReason(){
		return reason;
	}
	public void setProvince(String province){
		this.province=province;
	}
	public String getProvince(){
		return province;
	}
	public void setRealName(String realName){
		this.realName=realName;
	}
	public String getRealName(){
		return realName;
	}
	public void setCardImg(String cardImg){
		this.cardImg=cardImg;
	}
	public String getCardImg(){
		return cardImg;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return city;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

