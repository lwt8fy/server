package net.jeeshop.chat.beans;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class AppUnsendmsgs  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String senderId;
	private String receiverId;
	private String msg;
	private String _datetime;//
	private String type;//
	private String success="1";//成功状态0,1
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String get_datetime() {
		return _datetime;
	}
	public void set_datetime(String datetime) {
		_datetime = datetime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}
