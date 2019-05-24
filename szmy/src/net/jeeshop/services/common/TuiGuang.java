package net.jeeshop.services.common;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;

/**
 * 
 * @author teng
 *
 */
public class TuiGuang  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;//用户名
	private String userID;//用户id
	private String userPhone;//用户手机号
	private String pID;//推荐人id
	private String pName;//推荐人用户名
	private String pPhone;//推荐人电话
	private String isLogin;//是否登录
	private String createTime;//创建日期
	private String loginTime;//登录时间
	
	private String startDate;
	private String endDate;
	
	private String phoneModel;//手机型号
	private String equipmentNumber;//设备号
	private Integer integral;//登录收益积分
	private String remark;//备注
	
	private String pnames;//逗号分割父类id字符串
	
	
	private String orderBySql;
	
	private String authentication ;//0.未认证  1.已提交,审核中 2.已通过认证 3.认证失败
	private String scount;//物流、出售、收购信息数量
	private String isJiLian;//是否查询级联
	private List<String> bPnames;//推广人管理二级三级用户名集合
	private String type;//用户类型  1为猪场,2为屠宰场,3为经纪人4物流公司,5自然人
	
	
	public String getOrderBySql() {
		return orderBySql;
	}
	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}
	public String getPnames() {
		return pnames;
	}
	public void setPnames(String pnames) {
		this.pnames = pnames;
	}
	@Override
	public String getStartDate() {
		return startDate;
	}
	@Override
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Override
	public String getEndDate() {
		return endDate;
	}
	@Override
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getPID() {
		return pID;
	}
	public void setPID(String pID) {
		this.pID = pID;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String pName) {
		this.pName = pName;
	}
	public String getPPhone() {
		return pPhone;
	}
	public void setPPhone(String pPhone) {
		this.pPhone = pPhone;
	}
	public String getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public String getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public String getScount() {
		return scount;
	}
	public void setScount(String scount) {
		this.scount = scount;
	}
	public String getIsJiLian() {
		return isJiLian;
	}
	public void setIsJiLian(String isJiLian) {
		this.isJiLian = isJiLian;
	}
	public List<String> getbPnames() {
		return bPnames;
	}
	public void setbPnames(List<String> bPnames) {
		this.bPnames = bPnames;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
