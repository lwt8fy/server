package net.jeeshop.services.front.connectUser.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.connectUser.dao.ConnectUserDao;
import net.jeeshop.services.front.connectUser.service.ConnectUserService;
import net.jeeshop.services.common.ConnectUser;


public class ConnectUserServiceImpl extends ServersManager <ConnectUser> implements ConnectUserService {
	private ConnectUserDao connectUserDao;

	public void setConnectUserDao(ConnectUserDao connectUserDao) { 
		this.connectUserDao=connectUserDao;
	}
}
