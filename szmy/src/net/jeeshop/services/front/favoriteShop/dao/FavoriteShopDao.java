package net.jeeshop.services.front.favoriteShop.dao;

import java.util.List;

import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;
import net.jeeshop.core.DaoManager;


public interface FavoriteShopDao extends DaoManager <FavoriteShop> {
	List<Company> companyList(FavoriteShop e);
	Object selectFavoriteShopCount(FavoriteShop e);
}
