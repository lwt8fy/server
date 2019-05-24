package net.jeeshop.services.common;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;
import net.jeeshop.services.front.area.bean.Area;

/**
 * 商户信息
 * 
 * @author penguin
 * @date 2015-2-9
 * 
 */
public class Company extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String createAccount;// 用户ID
	private String name;// 商户名称
	private String legalPerson;// 法人代表
	private String address;// 地址
	private String businessLicense;
	private String contactor;// 联系人
	private String contactorTelephone;// 联系人手机号码
	private String contactorPhone;// 联系人电话
	private String contactorEmail;// 邮箱
	private String contactorZipcode;// 邮编
	private String contactorFax;// 传真
	private String note;// 公司简介
	private String createtime;// 录入时间
	private String updatetime;// 最后修改时间
	private String accountBank;// 开户银行
	private String accountNumber;// 银行帐号
	private String icon;// 图片
	private Integer compNum=10;//企业排序 
	private String status;//1为未审核，2为通过，3为未通过
	private String reason;
	
	
	private String province;
	private String city;
	private String area;
	private String pcadetail;
	private String sfz1;
	private String sfz2;
	private String gsyyzz;
	private String swdjz;
	private String zzjgdmz;
	private String scxkz;
	private String gmpzs;
	private String gspzs;

	

	private String type;// 类型 1为普通 2为深度合作  3县级运营商  4 自营
	
	
	private List<Area> areaList;//区域列表
	
	@Override
	public void clear() {
		super.clear();
		id = null;
		createAccount = "";
		name = null;
		legalPerson = "";
		address = "";
		businessLicense = "";
		contactor = "";
		contactorTelephone = "";
		contactorPhone = "";
		contactorEmail = "";
		contactorZipcode = "";
		contactorFax = "";
		note = "";
		createtime = null;
		updatetime = null;
		accountBank = null;
		accountNumber = null;
		compNum=10;
		status="1";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAddress() {
		return address;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactorTelephone() {
		return contactorTelephone;
	}

	public void setContactorTelephone(String contactorTelephone) {
		this.contactorTelephone = contactorTelephone;
	}

	public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}

	public String getContactorEmail() {
		return contactorEmail;
	}

	public void setContactorEmail(String contactorEmail) {
		this.contactorEmail = contactorEmail;
	}

	public String getContactorZipcode() {
		return contactorZipcode;
	}

	public void setContactorZipcode(String contactorZipcode) {
		this.contactorZipcode = contactorZipcode;
	}

	public String getContactorFax() {
		return contactorFax;
	}

	public void setContactorFax(String contactorFax) {
		this.contactorFax = contactorFax;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getCompNum() {
		return compNum;
	}

	public void setCompNum(Integer compNum) {
		this.compNum = compNum;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getPcadetail() {
		return pcadetail;
	}

	public void setPcadetail(String pcadetail) {
		this.pcadetail = pcadetail;
	}

	public String getSfz1() {
		return sfz1;
	}

	public void setSfz1(String sfz1) {
		this.sfz1 = sfz1;
	}

	public String getSfz2() {
		return sfz2;
	}

	public void setSfz2(String sfz2) {
		this.sfz2 = sfz2;
	}

	public String getGsyyzz() {
		return gsyyzz;
	}

	public void setGsyyzz(String gsyyzz) {
		this.gsyyzz = gsyyzz;
	}

	public String getSwdjz() {
		return swdjz;
	}

	public void setSwdjz(String swdjz) {
		this.swdjz = swdjz;
	}

	public String getZzjgdmz() {
		return zzjgdmz;
	}

	public void setZzjgdmz(String zzjgdmz) {
		this.zzjgdmz = zzjgdmz;
	}

	public String getScxkz() {
		return scxkz;
	}

	public void setScxkz(String scxkz) {
		this.scxkz = scxkz;
	}

	public String getGmpzs() {
		return gmpzs;
	}

	public void setGmpzs(String gmpzs) {
		this.gmpzs = gmpzs;
	}

	public String getGspzs() {
		return gspzs;
	}

	public void setGspzs(String gspzs) {
		this.gspzs = gspzs;
	}
	
}
