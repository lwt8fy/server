package net.jeeshop.services.front.appPicture.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.AppPicture;
import net.jeeshop.services.front.appPicture.dao.AppPictureDao;

public class AppPictureServiceImpl  extends ServersManager<AppPicture> implements AppPictureService{
	private AppPictureDao appPictureDao;

	public void setAppPictureDao(AppPictureDao appPictureDao) {
		this.appPictureDao = appPictureDao;
	}

	@Override
	public List<AppPicture> selectByOid(String oid) {
		// TODO Auto-generated method stub
		return appPictureDao.selectByOid(oid);
	}

	@Override
	public void deleteByOid(String oid) {
		// TODO Auto-generated method stub
		 appPictureDao.deleteByOid(oid);
	}

	@Override
	public void deleteByVid(String vid) {
		appPictureDao.deleteByVid(vid);
		
	}

	
	
}
