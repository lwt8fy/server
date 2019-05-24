package net.jeeshop.services.front.signIn.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.signIn.dao.SignInDao;
import net.jeeshop.services.front.signIn.service.SignInService;
import net.jeeshop.services.common.SignIn;


public class SignInServiceImpl extends ServersManager <SignIn> implements SignInService {
	private SignInDao signInDao;

	public void setSignInDao(SignInDao signInDao) { 
		this.signInDao=signInDao;
	}
}
