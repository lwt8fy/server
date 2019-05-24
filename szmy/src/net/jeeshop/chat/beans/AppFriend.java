package net.jeeshop.chat.beans;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class AppFriend  extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String masterId;
	private String friendId;
	private String friendName;
	private String friendAvatarId;//手机号码
	private String friendIsOnline;//头像
	
	
	
	private String success="1";//成功状态0,1
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendAvatarId() {
		return friendAvatarId;
	}
	public void setFriendAvatarId(String friendAvatarId) {
		this.friendAvatarId = friendAvatarId;
	}
	public String getFriendIsOnline() {
		return friendIsOnline;
	}
	public void setFriendIsOnline(String friendIsOnline) {
		this.friendIsOnline = friendIsOnline;
	}
	
	
	
}
