package net.jeeshop.services.front.appVersions.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.common.AppVersions;

public interface AppVersionsDao  extends DaoManager<AppVersions> {

	AppVersions selectNewest();
	
}
