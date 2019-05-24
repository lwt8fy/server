package net.jeeshop.services.common;

import java.io.Serializable;
import net.jeeshop.core.dao.QueryModel;

public class AppBroker  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String province;//省
	private String city;//市
	private String area;//县
	 private String business;//所属行业
	 private String companyName;//公司名称
	 private String realName;//真实姓名
	 private String QQ;//QQ号
	 private String intro;//业务介绍
	 private String idCard;//身份证
	 private String cardImg;//证件图片
	 private String phone;//手机号
	 private String status;//状态:1为申请,2为通过,3不通过
	 private String userID;//用户id
	 private String username;//关联用户名
	 private String createTime;//申请时间
	 private String reason;//不通过原因
	 private Double x;//纬度
     private Double y;//经度
	private String success="1";//成功状态0,1

	

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
	
	
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getCardImg() {
		return cardImg;
	}
	public void setCardImg(String cardImg) {
		this.cardImg = cardImg;
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
	
}
