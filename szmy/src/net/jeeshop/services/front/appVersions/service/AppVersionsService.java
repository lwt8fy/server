package net.jeeshop.services.front.appVersions.service;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.AppVersions;

public interface AppVersionsService  extends Services<AppVersions>{
	AppVersions selectNewest();
}
