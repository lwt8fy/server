package net.jeeshop.services.manage.scrollPic.impl;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.scrollPic.ScrollPicService;
import net.jeeshop.services.manage.scrollPic.dao.ScrollPicDao;
import net.jeeshop.services.common.ScrollPic;


public class ScrollPicServiceImpl extends ServersManager <ScrollPic> implements ScrollPicService {
	private ScrollPicDao scrollPicDao;

	public void setScrollPicDao(ScrollPicDao scrollPicDao) { 
		this.scrollPicDao=scrollPicDao;
	}
}
