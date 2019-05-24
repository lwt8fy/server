package net.jeeshop.core.system.bean;

import java.io.Serializable;
import java.util.Map;

import net.jeeshop.core.dao.page.PagerModel;


/**
 * 用户
 * 
 * @author huangf
 * 
 */
public class User extends PagerModel implements Serializable {
	private String id;
	private String username;
	private String password;
	private String createtime;
	private String status;
	private String rid;
	private String email;

	private String newpassword;
	private String newpassword2;
	private String nickname;
	private String nxbUserName;//农信宝帐号
	private String nxbPassword;//农信宝密码
	private String source;//用户来源
	
	
	private String freeze;//是否冻结
	private String freezeStartdate;//冻结开始时间
	private String freezeEnddate;//冻结结束时间
	
	private String mobile;//手机号码
	private String presenter;//推荐人
	public String getPresenter() {
		return presenter;
	}



	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}



	public String getPastDue() {
		return pastDue;
	}



	public void setPastDue(String pastDue) {
		this.pastDue = pastDue;
	}

	private String pastDue;

	private String role_dbPrivilege;
	private Map<String, String> dbPrivilegeMap;// 用户数据库权限

	public static final String user_status_y = "y";// 启用
	public static final String user_status_n = "n";// 禁用

	@Override
	public void clear() {
		this.id = null;
		this.status = null;
		this.createtime = null;
		
		this.rid = null;
		this.username = null;
		this.password = null;
		newpassword = null;
		newpassword2 = null;
		nickname = null;
		email = null;
		role_dbPrivilege = null;

		if (dbPrivilegeMap != null) {
			dbPrivilegeMap.clear();
			dbPrivilegeMap = null;
		}
	}

	
	
	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getFreeze() {
		return freeze;
	}



	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}



	public String getFreezeStartdate() {
		return freezeStartdate;
	}



	public void setFreezeStartdate(String freezeStartdate) {
		this.freezeStartdate = freezeStartdate;
	}



	public String getFreezeEnddate() {
		return freezeEnddate;
	}



	public void setFreezeEnddate(String freezeEnddate) {
		this.freezeEnddate = freezeEnddate;
	}



	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getNewpassword2() {
		return newpassword2;
	}

	public void setNewpassword2(String newpassword2) {
		this.newpassword2 = newpassword2;
	}

	public Map<String, String> getDbPrivilegeMap() {
		return dbPrivilegeMap;
	}

	public void setDbPrivilegeMap(Map<String, String> dbPrivilegeMap) {
		this.dbPrivilegeMap = dbPrivilegeMap;
	}

	public String getRole_dbPrivilege() {
		return role_dbPrivilege;
	}

	public void setRole_dbPrivilege(String role_dbPrivilege) {
		this.role_dbPrivilege = role_dbPrivilege;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNxbUserName() {
		return nxbUserName;
	}

	public void setNxbUserName(String nxbUserName) {
		this.nxbUserName = nxbUserName;
	}

	public String getNxbPassword() {
		return nxbPassword;
	}

	public void setNxbPassword(String nxbPassword) {
		this.nxbPassword = nxbPassword;
	}

}
