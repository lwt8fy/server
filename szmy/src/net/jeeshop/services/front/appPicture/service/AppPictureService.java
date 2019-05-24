package net.jeeshop.services.front.appPicture.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppPicture;

public interface AppPictureService  extends Services<AppPicture>{

	List<AppPicture> selectByOid(String oid);
	void deleteByOid(String oid);
	void deleteByVid(String vid);

}
