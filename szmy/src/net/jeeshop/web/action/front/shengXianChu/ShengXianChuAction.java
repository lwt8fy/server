package net.jeeshop.web.action.front.shengXianChu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.services.common.CatalogType;
import net.jeeshop.services.front.catalog.bean.Catalog;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
/**
 * 
 * 作者:王海洋
 * 时间:2016-1-21上午10:17:59
 * 描述: 生鲜畜产品控制类
 */
public class ShengXianChuAction extends BaseAction<Product> {

	private static final long serialVersionUID = 7051565499581541296L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ShengXianChuAction.class);
	private String catalogCode;//选择的目录code
	private ProductService productService;
	private int orderBy;//排序规则
	private int orderType;//排序顺序
	private List<Product> productList;//商品列表
	private String compType;//企业类型
	private String jsonStr;
	private Map<String, String> orderMap;//排序map
	@Override
	public Product getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	@Override
	public void insertAfter(Product e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-21上午10:23:11
	 * 描述: 首页
	 * @return
	 */
	public String toIndex(){
		this.getRequest().setAttribute("_selectMenu","chuchanpin");
		getSession().setAttribute("selectMenu",null);
		this.getRequest().setAttribute("meirijingxuan", SystemManager.shengXianChuJingXuan);
		this.getRequest().setAttribute("sxcLeiBie", SystemManager.sxcCatalogs);
		try {
				productList() ;
		} catch (Exception e) {
			logger.error(e.getMessage());
			productList = new ArrayList<Product>();
		}
		this.getRequest().setAttribute("productList", productList);
		return "toIndex";
	}
	public List<Product> productList() throws Exception{
		logger.error("orderBy="+orderBy + ",catalogCode="+catalogCode);//catalogCode存储的是ID
		List<Catalog> catalogs = new ArrayList<Catalog>();
		if(StringUtils.isBlank(catalogCode)){//     为选择任何类别 则查询所有
			List<CatalogType> list = SystemManager.sxcCatalogs;
			for(CatalogType type :list){
				Catalog catalog =  SystemManager.catalogsMap.get(type.getCatalogID());
				if(catalog!=null){
					catalogs.add(catalog);
				}
			}
		}else{
			Catalog item = SystemManager.catalogsMap.get(this.catalogCode);
			if(item!=null){
				catalogs.add(item);
				getSession().setAttribute("selectMenu", item.getId());//设置选择的商品目录
			}
		}
		for(Catalog catalog : catalogs){
			//添加可能是父类的类别ID到查询类别集合
			getE().getQueryCatalogIDs().add(Integer.valueOf(catalog.getId()));
			logger.error("getE().getQueryCatalogIDs()="+getE().getQueryCatalogIDs());
			
			if(catalog.getDeep()==1){//一级
				e.setMainCatalogName(catalog.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				Catalog clog=null;
				for(int j=0;j<catalog.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					clog=catalog.getChildren().get(j);
					getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getId()));
					for(int k=0;k<clog.getChildren().size();k++){
						//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
						getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getChildren().get(k).getId()));
					}
				}
			}else if(catalog.getDeep()==2){//二级
				e.setMainCatalogName(catalog.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				for(int j=0;j<catalog.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					getE().getQueryCatalogIDs().add(Integer.valueOf(catalog.getChildren().get(j).getId()));
				}
			}else if(catalog.getDeep()==3){//三级
				e.setChildrenCatalogName(catalog.getName());
				//catalog = SystemManager.catalogsMap.get(catalog.getPid());
//				getSession().setAttribute("selectMenu", catalog.getId());
				e.setMainCatalogName(catalog.getName());
			}
			
		}
		//加载商品
		productList = selectProductList0();
		orderMap = KeyValueHelper.getMap("product_orderBy_");
		return productList;
	}
	@SuppressWarnings("unchecked")
	private List<Product> selectProductList0() throws Exception {
		List<Integer> idlist = e.getQueryCatalogIDs();
		String ids="";
		for (int i = 0; i < idlist.size(); i++) {
			if(i==0){
				ids="(catalogID like '%,"+idlist.get(i)+",%'";
			}else{
				ids+=" or catalogID like '%,"+idlist.get(i)+",%'";
			}
		}
		ids+=")";
		if(idlist!=null&&idlist.size()>0){
			e.setCatalogIdStr(ids);
			}
		e.setQueryCatalogIDs(null);
		
		getE().setOrderBy(orderBy);//设置排序规则
		getE().setOrderType(orderType);//设置排序顺序
		getE().setCompanyType(compType);//企业类型
		List<Product> result = new LinkedList<Product>();
		String province = (String)this.getRequest().getSession().getAttribute("_province_");
		if(StringUtils.isNotBlank(province)){
			getE().setProvince(province);
		}
		super.selectList();//分页搜索数据库中的商品
		result = pager.getList();
		return result;
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-21下午04:05:29
	 * 描述:分页获取商品列表 
	 * @return
	 */

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new Product();
		}
		
		this.orderBy = 0;
		this.catalogCode = null;
		this.orderType=0;
		e.clear();
		
		if(productList!=null && productList.size()>0){
			for(int i=0;i<this.productList.size();i++){
				Product p = this.productList.get(i);
				p.clear();
				p = null;
			}
			this.productList.clear();
		}
		this.productList = null;
	}

	@Override
	protected void selectListAfter() {
		
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

}
