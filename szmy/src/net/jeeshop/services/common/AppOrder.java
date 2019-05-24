package net.jeeshop.services.common;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;

public class AppOrder  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;//标题
	private Double price;//价格
	private String marketingTime;//出栏时间
	private String createTime;//创建发布时间
	private String lockTime;//下单锁定时间
	private String finishTime;//成交时间
	private String paymentTime;//付款时间
	private Integer number;//数量
	private Double weight;//重量
	private String orderStatus;//订单状态:1为新创建,2为已下单,3为已成交,4 确认订单 5 已支付 6 已发货 
	private String address;//地址区域
	private String remark;//备注
	private String userID;//发布人id
	private String userName;//发布人用户名
	private String phone;//发布人手机号
	private Integer picNumber;//图片数量
	private String coverPicture;//封面图片
	private Double finalPrice;//最终成交价格
	private String orderType;//订单类型  1为出售,2为收购
	private String success="1";//成功状态0,1
	private String pigType;//生猪种类1  内三元、2 外三元、3 土杂猪  （1/2/3 是状态值） 
	private Double x;//纬度
   
	private Double y;//经度
	private String orderBy;//排序传值:price desc 无数据库字段
	private String color;//毛色

	
	private String purchaserID;
	private String purchaserName;//买方的姓名
	private String purchaserPhone;
	 
	
	private String province;//省
	private String city;//市
	private String area;//县
	
	
	private Double subscription ;//卖方定金
	private Double purchaserSubscription ;//买方定金

	
	
	private String qingKong;//清空值   无作用字段,用来清空一些值,数据库无字段
	private String startDate;//开始时间
	private String endDate;//结束时间
	private double startPrice;//开始价格
	private double endPrice;//结束价格
	private double startWeight;//开始重量
	private double endWeight;//结束重量
	private int startNumber;//开始数量
	private int endNumber;//结束数量
	
	private String compName;//企业名称
	private String compID;//企业id
	private String compType;//企业类型
	private String isShow;//是否显示:y/n
	private String sendType;//配送方式
	private String payType;//支付方式: 1,线上支付  2:线下支付
	
	
	
	//sql备用字段(无数据库字段)
	
	private String whereSql;
	
	///////////////////
	
	public String getIsShow() {
		return isShow;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompID() {
		return compID;
	}
	public void setCompID(String compID) {
		this.compID = compID;
	}
	public String getCompType() {
		return compType;
	}
	public void setCompType(String compType) {
		this.compType = compType;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	
	
	
	public String getQingKong() {
		return qingKong;
	}
	public void setQingKong(String qingKong) {
		this.qingKong = qingKong;
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
	public String getPigType() {
		return pigType;
	}
	public void setPigType(String pigType) {
		this.pigType = pigType;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	private List<AppPicture> pictureList;//图片集合
	
	 
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
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
	
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
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
	public Integer getPicNumber() {
		return picNumber;
	}
	public void setPicNumber(Integer picNumber) {
		this.picNumber = picNumber;
	}
	public String getCoverPicture() {
		return coverPicture;
	}
	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
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
	public String getMarketingTime() {
		return marketingTime;
	}
	public void setMarketingTime(String marketingTime) {
		this.marketingTime = marketingTime;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<AppPicture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<AppPicture> pictureList) {
		this.pictureList = pictureList;
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
	public double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}
	public double getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(double endPrice) {
		this.endPrice = endPrice;
	}
	public double getStartWeight() {
		return startWeight;
	}
	public void setStartWeight(double startWeight) {
		this.startWeight = startWeight;
	}
	public double getEndWeight() {
		return endWeight;
	}
	public void setEndWeight(double endWeight) {
		this.endWeight = endWeight;
	}
	public int getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	public int getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(int endNumber) {
		this.endNumber = endNumber;
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
		public String getWhereSql() {
			return whereSql;
		}
		public void setWhereSql(String whereSql) {
			this.whereSql = whereSql;
		}
		
		

}
