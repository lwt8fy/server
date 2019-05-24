package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/**
 * 关联用户表
 * 用来关联其他系统用户
 * @author teng
 */
public class ConnectUser extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String createTime;//关联日期
	private String source;//数据来源(1:平顶山动检)(2:河南动检系统)
	private String account;//本地平台用户名
	private String userName;//用户名
	private String password;//密码
	private String area1;//区域1(动检接口用)
	private String area2;//区域2(动检接口用)
	private String area3;//区域3(动检接口用)
	private String area4;//区域4(动检接口用)
	private String success ="1";

	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setSource(String source){
		this.source=source;
	}
	public String getSource(){
		return source;
	}
	public void setAccount(String account){
		this.account=account;
	}
	public String getAccount(){
		return account;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea2() {
		return area2;
	}
	public void setArea2(String area2) {
		this.area2 = area2;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public String getArea4() {
		return area4;
	}
	public void setArea4(String area4) {
		this.area4 = area4;
	}
	
	
}

