package net.jeeshop.services.front.catalogType.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.catalogType.dao.CatalogTypeDao;
import net.jeeshop.services.front.catalogType.service.CatalogTypeService;
import net.jeeshop.services.common.CatalogType;


public class CatalogTypeServiceImpl extends ServersManager <CatalogType> implements CatalogTypeService {
	private CatalogTypeDao catalogTypeDao;

	public void setCatalogTypeDao(CatalogTypeDao catalogTypeDao) { 
		this.catalogTypeDao=catalogTypeDao;
	}
}
