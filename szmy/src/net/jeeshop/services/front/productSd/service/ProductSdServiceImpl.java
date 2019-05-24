package net.jeeshop.services.front.productSd.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.productSd.dao.ProductSdDao;
import net.jeeshop.services.front.productSd.service.ProductSdService;
import net.jeeshop.services.common.ProductSd;


public class ProductSdServiceImpl extends ServersManager <ProductSd> implements ProductSdService {
	private ProductSdDao productSdDao;

	public void setProductSdDao(ProductSdDao productSdDao) { 
		this.productSdDao=productSdDao;
	}
}
