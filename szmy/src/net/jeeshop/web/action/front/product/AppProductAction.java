package net.jeeshop.web.action.front.product;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.attribute.bean.Attribute;
import net.jeeshop.services.front.catalog.CatalogService;
import net.jeeshop.services.front.catalog.bean.Catalog;
import net.jeeshop.services.front.comment.CommentService;
import net.jeeshop.services.front.comment.bean.Comment;
import net.jeeshop.services.front.company.CompanyService;
import net.jeeshop.services.front.company.bean.Company;
import net.jeeshop.services.front.favorite.FavoriteService;
import net.jeeshop.services.front.favorite.bean.Favorite;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.manage.activity.bean.Activity;
import net.jeeshop.services.manage.spec.SpecService;
import net.jeeshop.services.manage.spec.bean.Spec;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;



/**
 * 商品信息管理
 * 
 * @author jqsl2012@163.com
 * 
 */
public class AppProductAction extends BaseAction<Product> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppProductAction.class);
	private ProductService productService;//商品服务
	private AddressService addressService;//收货人地址服务
	private CatalogService catalogService;//分类
	private String catalogCode;//选择的目录code
	private int attributeID;//产品属性ID
	private String special;//促销活动
	private Map<String, String> orderMap;//排序map
	private List<Product> productList;//商品列表
	private int orderBy;//排序规则
	private int orderType;//排序顺序
	private Company company;//排序规则
	private String myPrice;//上次竞拍价格
	private String username;//用户名
	
	private CommentService commentService;//评论服务
	private FavoriteService favoriteService;
	private SpecService specService;
	private CompanyService companyService;
	
	private String json;
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	

	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}


	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	public String test() {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("1", "11");
	    map.put("2", "21");
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	    list.add(map);
	    this.getResponse().setContentType("text/html;charset=utf-8");  
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();  
			//JSON在传递过程中是普通字符串形式传递的，这里简单拼接一个做测试  
			String str = JSONArray.fromObject(list).toString();
			out.println(str);  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();  
				out.close();  
			}
		}
		return null;
	}
	@Override
	protected void selectListAfter() {
		String s = getRequest().getRequestURL().toString();
		if(s.contains("appProduct!search.action")){
			String key = getRequest().getParameter("key");//搜索关键字
			key=(key==null)?"":key;
			pager.setPagerUrl("appProduct!search.action?key="+key);
		}
	}

	@Override
	public Product getE() {
		return this.e;
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new Product();
		}
		
		this.orderBy = 0;
		this.attributeID = 0;
		this.special = null;
		this.catalogCode = null;
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
		
		super.setSelectMenu(FrontContainer.not_select_menu);//设置主菜单为不选中
	}
	
	@Override
	public void insertAfter(Product e) {
		e.clear();
	}
	
	/**
	 * 根据商品关键字搜索商品列表
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception{
		String key = getRequest().getParameter("key");//搜索关键字
		getE().setName(key);
		
		if(StringUtils.isNotBlank(catalogCode)){
			Catalog item = SystemManager.catalogsCodeMap.get(catalogCode);
			if(item==null){
				JSONObject jsonObject = JSONObject.fromObject(pager);
				jsonObject.put("success", "1");
			    json=jsonObject.toString();
				logger.error("目录为空！");
			    return "json";
			}
			//添加可能是父类的类别ID到查询类别集合
			getE().getQueryCatalogIDs().add(Integer.valueOf(item.getId()));
			logger.error("getE().getQueryCatalogIDs()="+getE().getQueryCatalogIDs());
			if(item.getDeep()==1){//一级
				e.setMainCatalogName(item.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				Catalog clog=null;
				for(int j=0;j<item.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					clog=item.getChildren().get(j);
					getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getId()));
					for(int k=0;k<clog.getChildren().size();k++){
						//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
						getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getChildren().get(k).getId()));
					}
				}
			}else if(item.getDeep()==2){//二级
				e.setMainCatalogName(item.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				for(int j=0;j<item.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					getE().getQueryCatalogIDs().add(Integer.valueOf(item.getChildren().get(j).getId()));
				}
			}else if(item.getDeep()==3){//三级
				e.setChildrenCatalogName(item.getName());
				item = SystemManager.catalogsMap.get(item.getPid());
				getSession().setAttribute("selectMenu", item.getId());
				e.setMainCatalogName(item.getName());
			}
		}
		
		
		productList = selectProductList0();
		if(productList==null||productList.size()==0){
			JSONObject jsonObject = JSONObject.fromObject(pager);
			jsonObject.put("success", "1");
			json=jsonObject.toString();
			return "json";
		}else{
			logger.error("productList="+productList.size());
			JSONObject jsonObject = JSONObject.fromObject(pager);
			jsonObject.put("success", "1");
		    json=jsonObject.toString();
		}
		return "json";
	}
	/**
	 * 根据商品属性加载商品列表
	 * @return
	 * @throws Exception 
	 */
	public String productListByAttrID() throws Exception{
		try {
//			logger.error("attributeID="+attributeID);
//			getE().setAttrID(this.attributeID);
//			Attribute attr = SystemManager.attrsMap.get(String.valueOf(this.attributeID));
//			this.catalogID = attr.getCatalogID();
//			logger.error("productListByAttrID  catalogID = "+catalogID);
//			productList = selectProductList0();
			productList();
		} catch (Exception e) {
			logger.error("productListByAttrID()异常："+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return "productList";
	}
	
	/**
	 * 根据商品目录、子目录、属性、排序等规则分页加载商品列表信息。此方法为商品加载的通用方法。
	 * @return
	 * @throws Exception
	 */
	public String  productList() throws Exception{
		try {
			if(this.attributeID>0){
				getE().setAttrID(this.attributeID);
				Attribute attr = SystemManager.attrsMap.get(String.valueOf(this.attributeID));
				this.catalogCode = SystemManager.catalogsMap.get(String.valueOf(attr.getCatalogID())).getCode();
			}
			logger.error("special="+special + ",orderBy="+orderBy + ",catalogCode="+catalogCode);
			
			Catalog item = SystemManager.catalogsCodeMap.get(this.catalogCode);
			if(item==null){
				JSONObject jsonObject = JSONObject.fromObject(pager);
				jsonObject.put("success", "1");
			    json=jsonObject.toString();
				logger.error("目录为空！");
			    return "json";
			}
			logger.error("item.getId()="+item.getId());
			
			getSession().setAttribute("selectMenu", item.getId());//设置选择的商品目录
			logger.error("item.getId()="+item.getId());
			//添加可能是父类的类别ID到查询类别集合
			getE().getQueryCatalogIDs().add(Integer.valueOf(item.getId()));
			logger.error("getE().getQueryCatalogIDs()="+getE().getQueryCatalogIDs());
			if(item.getDeep()==1){//一级
				e.setMainCatalogName(item.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				Catalog clog=null;
				for(int j=0;j<item.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					clog=item.getChildren().get(j);
					getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getId()));
					for(int k=0;k<clog.getChildren().size();k++){
						//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
						getE().getQueryCatalogIDs().add(Integer.valueOf(clog.getChildren().get(k).getId()));
					}
				}
			}else if(item.getDeep()==2){//二级
				e.setMainCatalogName(item.getName());
				//大类ID
				getE().setQueryCatalogIDs(new LinkedList<Integer>());
				for(int j=0;j<item.getChildren().size();j++){
					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
					getE().getQueryCatalogIDs().add(Integer.valueOf(item.getChildren().get(j).getId()));
				}
			}else if(item.getDeep()==3){//三级
				e.setChildrenCatalogName(item.getName());
				item = SystemManager.catalogsMap.get(item.getPid());
				getSession().setAttribute("selectMenu", item.getId());
				e.setMainCatalogName(item.getName());
			}
			
			//加载商品
			productList = selectProductList0();
			
			orderMap = KeyValueHelper.getMap("product_orderBy_");
		} catch (Exception e) {
			logger.error("productList()异常："+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		JSONObject jsonObject = JSONObject.fromObject(pager);
		jsonObject.put("success", "1");
	    json=jsonObject.toString();
		return "json";
	}
	

	
	/**
	 * 分页加载产品列表，每4个一行的显示
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Product> selectProductList0() throws Exception {
		
		/*
		 *修改目录查询方式,改为多个like
		 *2016.01.06
		 *teng 
		 */
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
		
//		getE().setStatus(2);//加载已经上架的商品
		getE().setOrderBy(4);//设置排序规则   根据id
		getE().setOrderType(orderType);//设置排序顺序
//		super.selectList();//分页搜索数据库中的商品
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize());
		super.selectList();//分页搜索数据库中的商品
		pager.setOffset(os);
		
		List<Product> result = pager.getList();
		
//		doImage(result);
		return result;
//		return convert4(result);
	}
	
	
	
	/**
	 * 查询指定的产品明细
	 * @return
	 * @throws Exception
	 */
	public String product() throws Exception{
		//Catalog item = checkProduct();//检查商品信息
		e = productService.selectById(getE().getId());
		
		//取key-value
		String unitValue = KeyValueHelper.get("product_unit_"+e.getUnit());
		e.setUnit(unitValue);
		
		//如果商品已上架并且商品的库存数小于等于0，则更新商品为已下架
		if(e.getStatus()==net.jeeshop.services.common.Product.Product_status_y){
			if(e.getStock()<=0){
				e.product_sorry_str = "抱歉，商品已售卖完了。";
				
				//更新商品为已下架
//				Product p = new Product();
//				p.setId(e.getId());
//				p.setStatus(Product.Product_status_n);
//				productService.update(p);
			}
		}
		
		StringBuilder imagesBuff = new StringBuilder(e.getPicture() + FrontContainer.product_images_spider);
		//组装商品详情页的图片地址
		if(StringUtils.isNotBlank(e.getImages())){
			imagesBuff.append(e.getImages());
		}
		System.out.println("---------------"+imagesBuff.toString());
		//productImagesBiz(imagesBuff.toString());
		//封装商品图片
		e.setImages(imagesBuff.toString().replace("_1.", "_3."));
		
		//加载商品参数
		e.setParameterList(productService.selectParameterList(e.getId()));
		
		//更新商品浏览次数
		Product p = new Product();
		p.setId(e.getId());
//		p.setHit(e.getHit()+1);//浏览次数++
		productService.updateHit(p);
		
		//加载指定商品的评论
		Comment comment = new Comment();
		comment.setProductID(e.getId());
		PagerModel commentPager = super.selectPagerModelByServices(commentService, comment);
		getRequest().setAttribute("commentPager", commentPager);
		super.pager = commentPager;//公用分页控件需要这么写。
		
		/*
		 * 加载和这个商品有关联的畅销商品和特价商品，显示到商品明细页面的左侧。
		 * 有关联的商品的选择方法是：加载该商品所在的子目录下的特定商品。考虑到性能问题，
		 * 这个必须借助缓存，事先我们将一些子目录下的畅销商品、特价商品 的前10来个加载到内存，然后用户访问这个页面的时候直接取内存即可。
		 */
//		e.setLeftProducts(loadProducts(1));
		
		
		String url = "/jsp/product/"+e.getId()+".jsp";
		logger.error("url = " + url);
		getRequest().setAttribute("productHTMLUrl",url);

		
		/*
		 * 检查，如果此商品是活动商品，则加载相应的活动信息。
		 */
		logger.error("e.getActivityID() = "+e.getActivityID());
		if(StringUtils.isNotBlank(e.getActivityID())){
			logger.error(">>>计算或拷贝此商品关联的活动的信息到此商品对象上。展示页面用==");
			Activity activity = SystemManager.activityMap.get(e.getActivityID());
			
			/**
			 * 计算或拷贝此商品关联的活动的信息到此商品对象上。展示页面用
			 */
			
			if(DateUtil.strToDatehhmmss(activity.getEndDate().substring(0,19)).before(new Date())){
				//myCommonDao.executeSql(2, "update t_product set activityID=null where activityID="+activity.getId());
				e.setActivityID(null);
			}else{
			
			e.setFinalPrice(String.valueOf(e.caclFinalPrice()));
			e.setExpire(activity.isExpire());
			e.setActivityEndDateTime(activity.getEndDate().substring(0,19).replace("-", "/"));
			e.setActivityType(activity.getActivityType());
			e.setDiscountType(activity.getDiscountType());
			e.setDiscountFormat(activity.getDiscountFormat());
			e.setMaxSellCount(activity.getMaxSellCount());
			e.setAccountRange(activity.getAccountRange());
			e.setExchangeScore(activity.getExchangeScore());
			e.setTuanPrice(activity.getTuanPrice());
			e.setMinprice(activity.getMinprice());
			e.setActivityName(activity.getName());
			
			logger.error("finalPrice = " + e.getFinalPrice()+",expire = " + e.isExpire()+",activityEndDateTime="+e.getActivityEndDateTime()+",score="+e.getScore());
		
			/*
			 * 如果商品是活动商品，则查看商品明细页的时候自动选择导航菜单li
			 */
			if(activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_c)){
				getSession().setAttribute("selectMenu","activity");
			}else if(activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_j)){
				getSession().setAttribute("selectMenu","score");
			}else if(activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_t)){
				getSession().setAttribute("selectMenu","tuan");
			}
			}
		}
		e.setProductHTML("");
		JSONObject jsonObject = JSONObject.fromObject(e);
		jsonObject.put("success", "1");
		jsonObject.put("sfsc", "0");
		if(StringUtils.isNotEmpty(username)){
		Favorite favorite=new Favorite();
		favorite.setAccount(username);
		favorite.setProductID(e.getId());
		List<Favorite> list = favoriteService.selectList(favorite);
			if(list!=null&&list.size()>0){
				jsonObject.put("sfsc", "1");
			}
		}
		//加载商品规格，以JSON字符串的形式隐藏在页面上，然后页面将其转换成对象集合，通过脚本控制规格的颜色和尺寸的双向关系。
		Spec spec = new Spec();
		spec.setProductID(e.getId());
		spec.setSpecStatus(Spec.spec_specStatus_y);
		List<Spec> specList = specService.selectList(spec);
		jsonObject.put("ggList", specList);
		
	    json=jsonObject.toString();
		return "json";
	
	}
	

	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-12下午02:29:34
	 * 描述: 获取类别 
	 * @return
	 */
	public String getCatalogs(){
		String pid = this.getRequest().getParameter("pid");
		//pid = StringUtils.isNotBlank(pid)?pid :"0";
		Catalog catalog  = new Catalog();
		catalog.setPid(pid);
		catalog.setType("p");
		catalog.setShowInNav(net.jeeshop.services.common.Catalog.catalog_showInNav_y);
		List<Catalog> list = this.catalogService.selectList(catalog);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("success", "1");
		JSONObject jsonObject = JSONObject.fromObject(map);
	    json=jsonObject.toString();
		return "json";
	}
	/**
	 * 获取附近的企业(运营中心)
	 * @author 滕武超
	 * @date 2016-1-7 下午04:03:07 
	 * @Description:
	 */
	public String getNearbyCompany(){
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
		String city = getRequest().getParameter("city");
		String area = getRequest().getParameter("area");
		Company com=new Company();
		com.setType("3");
		if(StringUtils.isBlank(city)){
			city="无数据";
		}
		com.setPcadetail(city+" "+area);
		List<Company> list = companyService.selectList(com);
		if(list!=null&&list.size()>0){
		com = list.get(0);	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", "1");
		jsonObject.put("companyID", com.getId());
		jsonObject.put("companyName", com.getName());
		json=jsonObject.toString();
		}else{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", "1");
			jsonObject.put("companyID", "");
			jsonObject.put("companyName", "");
			json=jsonObject.toString();
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}



	public String getMyPrice() {
		return myPrice;
	}

	public void setMyPrice(String myPrice) {
		this.myPrice = myPrice;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
}
