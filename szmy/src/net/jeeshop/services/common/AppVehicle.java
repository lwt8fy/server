package net.jeeshop.services.common;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;

public class AppVehicle  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String price ;//运费
	private String startTime;//限租开始时间
	private String endTime;//限租结束时间
	private String type;//车型
	private String carNum;//车牌号
	private String capacity;//容量
	private String companyId;//公司ID 
	private String companyTel;//公司联系方式
	private String name;//公司name;
	private String address ;//地址
	private String linkman;//联系人
	private String phone;//联系方式
	private String icon ;//图标
	private String status ="1";//1.发布  2.锁定
	private String orderBy;//排序字段
	private String remark;//备注
	
	private String createTime ;//创建时间
	private String createAccount ;//创建人
	private Double finalPrice;//最终成交价格
    private Double dingjin;//定金
    
	private String province;//省代码
	private String city;//市代码
	private String area;//县代码
	
	private double startPrice;//开始价格
	private double endPrice;//结束价格
	private String  startCapacity;//开始容量
	private String endCapacity;//结束容量
	
	private double x=0;//维度
	private double y=0;//经度
	private String success ="1"; //返回成功
	
	private String purchaserID;//买方的ID
	private String purchaserName;//买方的账户
	private String purchaserPhone;//买方电话
	private String isShow;//是否显示
	
	public static final String  fb="1";//发布
	public static final String  xd="2";//下单
	public static final String  qr="3";//确认订单
	public static final String  yswc="4";//运输完成
	public static final String  zfwc="5";//支付完成
	
	
	
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
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
	private List<AppPicture> pictureList;//图片集合
	
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}

	public List<AppPicture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<AppPicture> pictureList) {
		this.pictureList = pictureList;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateAccount() {
		return createAccount;
	}
	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
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
	public String getStartCapacity() {
		return startCapacity;
	}
	public void setStartCapacity(String startCapacity) {
		this.startCapacity = startCapacity;
	}
	public String getEndCapacity() {
		return endCapacity;
	}
	public void setEndCapacity(String endCapacity) {
		this.endCapacity = endCapacity;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Double getDingjin() {
		return dingjin;
	}
	public void setDingjin(Double dingjin) {
		this.dingjin = dingjin;
	}
	
}
