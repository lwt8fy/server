package net.jeeshop.services.front.catalogType.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.CatalogType;


public class CatalogTypeDaoImpl implements CatalogTypeDao {
	private BaseDao dao; 

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(CatalogType e) {
		return dao.selectPageList("front.catalogType.selectPageList","front.catalogType.selectPageCount",e);
	}
	@Override
	public List selectList(CatalogType e) {
		return dao.selectList("front.catalogType.selectList",e);
	}
	@Override
	public CatalogType selectOne(CatalogType e) {
		return (CatalogType)dao.selectOne("front.catalogType.selectOne",e);
	}
	@Override
	public int delete(CatalogType e) {
		return dao.delete("front.catalogType.selectList",e);
	}
	@Override
	public int update(CatalogType e) {
		return dao.update("front.catalogType.update",e);
	}
	@Override
	public int insert(CatalogType e) {
		return dao.insert("front.catalogType.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.catalogType.deleteById",id);
	}
	@Override
	public CatalogType selectById(String id) {
		return (CatalogType) dao.selectOne("front.catalogType.selectById",id);
	}
}

