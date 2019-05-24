package net.jeeshop.services.front.favoriteShop.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.favoriteShop.dao.FavoriteShopDao;
import net.jeeshop.services.front.favoriteShop.service.FavoriteShopService;
import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;


public class FavoriteShopServiceImpl extends ServersManager <FavoriteShop> implements FavoriteShopService {
	private FavoriteShopDao favoriteShopDao;

	public void setFavoriteShopDao(FavoriteShopDao favoriteShopDao) { 
		this.favoriteShopDao=favoriteShopDao;
	}

	@Override
	public List<Company> companyList(FavoriteShop e) {
		// TODO Auto-generated method stub
		return favoriteShopDao.companyList(e);
	}
	
	@Override
	public Object selectFavoriteShopCount(FavoriteShop e){
		return favoriteShopDao.selectFavoriteShopCount(e);
	}
}
