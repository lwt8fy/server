
package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class Profit  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userID;//收益人id
	private String account;//收益人帐号
	
	private String presentee;//被推荐人帐号
	private String productID;//商品id
	private String productName;//商品名称
	private Integer number;//商品数量
	private Double price;//商品单价
	private Double profit;//单个收益
	private Double ratio ;//收益比例
	private Double finalProfit;//最终总收益
	private String createTime;//收益时间
	private String compName;//公司名称
	private String compID;//公司Id
	
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String type;//类型,1为系统 2为商家 3为普通用户
	
	
	
	
	private String source ;
	/**
	 * 数据来源（1：被推荐人购买返利）（2：平台购买支付）(3:猪贸通推广注册收益)(4:猪贸通签到收益)
	 * (5:猪贸通认证)(6:猪贸通发布信息)(7:猪贸通成功交易)(8:被推广人认证猪场)(9:被推广人认证经纪人)(10:被推广人认证屠宰场)
	 * (11:被推广人每天发前两条消息)(12:提现)
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	
	
	private String remark ;//备注
	
	
	
	/**
	 * 认证猪场积分_当前用户获得
	 */
	public static final int authentication=100;
	/**
	 * 认证猪场积分_推广人获得
	 */
	public static final int authentication_zc=2000;
	/**
	 * 认证经纪人首次发消息积分_推广人获得
	 */
	public static final int authentication_jjr=8000;
	/**
	 * 认证屠宰场首次发消息积分_推广人获得
	 */
	public static final int authentication_tzc=10000;
	
	/**
	 * 发布生猪信息,每天前两次交易有效
	 */
	public static final int issue=20;
	/**
	 * 被推荐人发布生猪信息,每天一次交易有效
	 */
	public static final int issue_tgr=500;
	
	/**
	 * 猪毛通线上交易成功
	 */
	public static final int ZMT_XS =1000;
	
	/**
	 * 猪贸通线下交易成功
	 */
	public static final int ZMT_XX=20;
	

	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPresentee() {
		return presentee;
	}
	public void setPresentee(String presentee) {
		this.presentee = presentee;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	public Double getFinalProfit() {
		return finalProfit;
	}
	public void setFinalProfit(Double finalProfit) {
		this.finalProfit = finalProfit;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
