package net.jeeshop.services.front.productSd.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.ProductSd;


public class ProductSdDaoImpl implements ProductSdDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(ProductSd e) {
		return dao.selectPageList("front.productSd.selectPageList","front.productSd.selectPageCount",e);
	}
	@Override
	public List selectList(ProductSd e) {
		return dao.selectList("front.productSd.selectList",e);
	}
	@Override
	public ProductSd selectOne(ProductSd e) {
		return (ProductSd)dao.selectOne("front.productSd.selectOne",e);
	}
	@Override
	public int delete(ProductSd e) {
		return dao.delete("front.productSd.selectList",e);
	}
	@Override
	public int update(ProductSd e) {
		return dao.update("front.productSd.update",e);
	}
	@Override
	public int insert(ProductSd e) {
		return dao.insert("front.productSd.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.productSd.deleteById",id);
	}
	@Override
	public ProductSd selectById(String id) {
		return (ProductSd) dao.selectOne("front.productSd.selectById",id);
	}
}

