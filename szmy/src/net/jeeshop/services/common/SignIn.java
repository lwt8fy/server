package net.jeeshop.services.common;

import java.io.Serializable;
import net.jeeshop.core.dao.QueryModel;


public class SignIn extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String createTime;
	private Integer num;
	private String userName;

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
	public void setNum(Integer num){
		this.num=num;
	}
	public Integer getNum(){
		return num;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
}

