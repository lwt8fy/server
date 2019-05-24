package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/**
 * 防疫员
 * @author test
 */
public class AppFyy extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;

private String id;
private String province;//省
private String city;//市
private String area;//县
private String address;//详细地址
 private String companyName;//公司名称
 private String realName;//联系人姓名
 private String QQ;//QQ号
 private String phone;//手机号
 private String status;//状态:1为申请,2为通过,3不通过
 private String userID;//用户id
 private String username;//关联用户名
 private String createTime;//申请时间
 private String reason;//不通过原因
 private Double x;//纬度

 	private Double y;//经度
	private String bjd;//报检点
	private String picture;//证件图片
	private String email;//邮箱
	private String idCard;//身份证号
	private String compPhone;//公司电话
	public String getCompPhone() {
		return compPhone;
	}
	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	
	private String success="1";//成功状态0,1  返回json用到
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public void setBjd(String bjd){
		this.bjd=bjd;
	}
	public String getBjd(){
		return bjd;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
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
	public void setCompanyName(String companyName){
		this.companyName=companyName;
	}
	public String getCompanyName(){
		return companyName;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return city;
	}
	public void setPicture(String picture){
		this.picture=picture;
	}
	public String getPicture(){
		return picture;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setUserID(String userID){
		this.userID=userID;
	}
	public String getUserID(){
		return userID;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return area;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getAddress(){
		return address;
	}
	public void setQQ(String QQ){
		this.QQ=QQ;
	}
	public String getQQ(){
		return QQ;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}

