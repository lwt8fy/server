package net.jeeshop.services.front.favoriteShop.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;


public class FavoriteShopDaoImpl implements FavoriteShopDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(FavoriteShop e) {
		return dao.selectPageList("front.favoriteShop.selectPageList","front.favoriteShop.selectPageCount",e);
	}
	@Override
	public List selectList(FavoriteShop e) {
		return dao.selectList("front.favoriteShop.selectList",e);
	}
	@Override
	public FavoriteShop selectOne(FavoriteShop e) {
		return (FavoriteShop)dao.selectOne("front.favoriteShop.selectOne",e);
	}
	@Override
	public int delete(FavoriteShop e) {
		return dao.delete("front.favoriteShop.delete",e);
	}
	@Override
	public int update(FavoriteShop e) {
		return dao.update("front.favoriteShop.update",e);
	}
	@Override
	public int insert(FavoriteShop e) {
		return dao.insert("front.favoriteShop.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.favoriteShop.deleteById",id);
	}
	@Override
	public FavoriteShop selectById(String id) {
		return (FavoriteShop) dao.selectOne("front.favoriteShop.selectById",id);
	}
	@Override
	public List<Company> companyList(FavoriteShop e) {
		return dao.selectList("front.FavoriteShop.connect", e);
	}
	
	@Override
	public Object selectFavoriteShopCount(FavoriteShop e){
		return dao.selectOne("front.favoriteShop.selectFavoriteShopCount",e);
	}
}

