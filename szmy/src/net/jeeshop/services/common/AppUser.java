package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class AppUser  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String phone;//手机号码mobile
	private String picture;//头像////////////////////
	private String uuid;//登录时生成的唯一标识////////////////
	private String address;//详细地址 
	private String province;//省代码
	private String city;//市代码
	private String area ;//县代码////////////
	private String type;//用户类型  1为猪场,2为屠宰场,3为经纪人4物流公司,5自然人,6防疫员,7检疫员,8监督员
	private String authentication ; //0.未认证  1.已提交,审核中 2.已通过认证 3.认证失败
	private Double x;//纬度
   
	private Double y;//经度
	private String presenter ;//推荐人
	private Double money;//收益资金
	
	private String userStatus;//用户状态    (0:新申请)(1:已完成首次登录)
	private Integer tjrsl;//推荐人数量
	private String source;//用户来源   1:'前台注册',2:'后台注册',3:'app注册',4平顶山猪贸通注册,5河南电子出证
	
	/**
	 * 用户来源   1:'前台注册',2:'后台注册',3:'app注册',4平顶山猪贸通注册,5河南电子出证
	 */
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	
	
	
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String success="1";//成功状态0,1
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * 用户类型  1为猪场,2为屠宰场,3为经纪人4物流公司,5自然人,6防疫员,7检疫员,8监督员
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getPresenter() {
		return presenter;
	}
	public void setPresenter(String presenter) {
		this.presenter = presenter;
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
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public Integer getTjrsl() {
		return tjrsl;
	}
	public void setTjrsl(Integer tjrsl) {
		this.tjrsl = tjrsl;
	}
	
	
}
