package net.jeeshop.services.common;

import java.io.Serializable;
/**
 * 王海洋
 * 2016年2月1日09:20:14
 * 暂时没用
 */
import net.jeeshop.core.dao.QueryModel;

public class AppVehicleOrder  extends QueryModel implements Serializable  {

	private static final long serialVersionUID = -7950349729249960923L;
	private String id;
	private String vehicleId;//物流ID
	private String title;//标题
	private Double price;//价格
	private String createFaBuTime;//创建发布时间
	private String createTime;//创建时间
	private String lockTime;//下单锁定时间
	private String finishTime;//成交时间
	private String paymentTime;//付款时间
	private String capacity;//容量
	private String orderStatus;//订单状态:1为新创建,2为已下单,3为已成交,4 确认订单 5 已支付 6 已发货 
	private String type;//dj定金 fk付款
	public static final String  fb="1";//发布
	public static final String  xd="2";//下单
	public static final String  qr="3";//确认订单
	public static final String  yswc="4";//运输完成
	public static final String  zfwc="5";//支付
	private String address;//地址区域
	private String remark;//备注
	private String userID;//发布人id
	private String userName;//发布人用户名
	private String phone;//发布人手机号
	private Integer picNumber;//图片数量
	private String icon;//封面图片
	private Double finalPrice;//最终成交价格
	private String orderType;//订单类型  1为出租,2为招租
	
	private String purchaserID;//买方的ID
	private String purchaserName;//买方的账户
	private String purchaserPhone;//买方电话
	
	private String success="1";//成功状态0,1
	private Double x;//纬度
	private Double y;//经度
	private String orderBy;//排序传值:price desc
	
	private String province;//省
	private String city;//市
	private String area;//县
	
	private Double subscription ;//卖方定金
	private Double purchaserSubscription ;//买方定金
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	
	
	
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCreateFaBuTime() {
		return createFaBuTime;
	}
	public void setCreateFaBuTime(String createFaBuTime) {
		this.createFaBuTime = createFaBuTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLockTime() {
		return lockTime;
	}
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPicNumber() {
		return picNumber;
	}
	public void setPicNumber(Integer picNumber) {
		this.picNumber = picNumber;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getPurchaserID() {
		return purchaserID;
	}
	public void setPurchaserID(String purchaserID) {
		this.purchaserID = purchaserID;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getPurchaserPhone() {
		return purchaserPhone;
	}
	public void setPurchaserPhone(String purchaserPhone) {
		this.purchaserPhone = purchaserPhone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Double getSubscription() {
		return subscription;
	}
	public void setSubscription(Double subscription) {
		this.subscription = subscription;
	}
	public Double getPurchaserSubscription() {
		return purchaserSubscription;
	}
	public void setPurchaserSubscription(Double purchaserSubscription) {
		this.purchaserSubscription = purchaserSubscription;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
