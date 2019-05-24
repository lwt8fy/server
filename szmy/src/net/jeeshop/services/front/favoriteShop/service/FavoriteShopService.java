package net.jeeshop.services.front.favoriteShop.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;


public interface FavoriteShopService extends Services <FavoriteShop> {
	List<Company> companyList(FavoriteShop favoriteShop);
	Object selectFavoriteShopCount(FavoriteShop e);
}
