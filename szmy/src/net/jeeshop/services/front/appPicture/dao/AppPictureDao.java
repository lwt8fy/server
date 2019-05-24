package net.jeeshop.services.front.appPicture.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.common.AppPicture;

public interface AppPictureDao  extends DaoManager<AppPicture> {

	List<AppPicture> selectByOid(String oid);

	void deleteByOid(String oid);

	void deleteByVid(String vid);
	
}
