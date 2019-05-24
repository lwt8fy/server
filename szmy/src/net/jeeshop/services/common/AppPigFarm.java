package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class AppPigFarm  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * +----------+--------------+
| Field    | Type         |
+----------+--------------+
| id       | int(11)      |
| province | varchar(20)  |
| city     | varchar(20)  |
| area     | varchar(20)  |
| type     | varchar(2)   |
| name     | varchar(100) |
| amount   | int(11)      |
| sales    | int(11)      |
| pigType  | varchar(2)   |
| username | varchar(100) |
| address  | varchar(200) |
| phone    | varchar(20)  |
| email    | varchar(50)  |
| nyBank   | varchar(50)  |
+----------+--------------+
	 */
	
	/*
	 ---------

	 */
	
	private String id;
	
	private String province;//省
	private String city;//市
	private String area;//县
	 private String type;//猪场类型 1为散户  2为规模化养殖
	 private String name;//猪场名称
	 private Integer amount;//存栏数量
	 private Integer sales;//年出栏数量
	 private String pigType;//生猪类型 1:内三元 2:外三元 3.土杂猪
	 private String username;//联系人
	 private String address;//详细地址
	 private String phone;//手机号
	 private String email;//邮箱
	 private String nyBank;//农行卡号
	 private String status;//状态:1为申请,2为通过,3不通过
	 private String userID;//用户id
	 private String account;//用户名
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public String getPigType() {
		return pigType;
	}
	public void setPigType(String pigType) {
		this.pigType = pigType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNyBank() {
		return nyBank;
	}
	public void setNyBank(String nyBank) {
		this.nyBank = nyBank;
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
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
	
	
	
}
