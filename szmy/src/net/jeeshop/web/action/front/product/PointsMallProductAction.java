package net.jeeshop.web.action.front.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.LRULinkedHashMap;
import net.jeeshop.services.common.ActivityCount;
import net.jeeshop.services.common.CatalogType;
import net.jeeshop.services.common.FavoriteShop;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.activityCount.service.ActivityCountService;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.attribute.bean.Attribute;
import net.jeeshop.services.front.attribute_link.Attribute_linkService;
import net.jeeshop.services.front.catalog.CatalogService;
import net.jeeshop.services.front.catalog.bean.Catalog;
import net.jeeshop.services.front.comment.CommentService;
import net.jeeshop.services.front.comment.bean.Comment;
import net.jeeshop.services.front.company.CompanyService;
import net.jeeshop.services.front.company.bean.Company;
import net.jeeshop.services.front.emailNotifyProduct.EmailNotifyProductService;
import net.jeeshop.services.front.emailNotifyProduct.bean.EmailNotifyProduct;
import net.jeeshop.services.front.favorite.FavoriteService;
import net.jeeshop.services.front.favorite.bean.Favorite;
import net.jeeshop.services.front.favoriteShop.service.FavoriteShopService;
import net.jeeshop.services.front.news.NewsService;
import net.jeeshop.services.front.news.bean.News;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.front.product.bean.ProductImageInfo;
import net.jeeshop.services.front.systemSetting.bean.SystemSetting;
import net.jeeshop.services.manage.activity.ActivityService;
import net.jeeshop.services.manage.activity.bean.Activity;
import net.jeeshop.services.manage.gift.GiftService;
import net.jeeshop.services.manage.gift.bean.Gift;
import net.jeeshop.services.manage.spec.SpecService;
import net.jeeshop.services.manage.spec.bean.Spec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
@SuppressWarnings("unchecked")
public class PointsMallProductAction extends BaseAction<Product>{
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductAction.class);
	private ProductService productService;//商品服务
	private CompanyService companyService;//商城服务
	private CommentService commentService;//评论服务
	private MyCommonDao myCommonDao;//
	private AddressService addressService;//收货人地址服务
	@SuppressWarnings("unused")
	private Attribute_linkService attribute_linkService;//商品属性链接表服务
	private NewsService newsService;//文章服务
	private FavoriteService favoriteService;//商品收藏夹服务
	private FavoriteShopService favoriteShopService;//店铺收藏
	private EmailNotifyProductService emailNotifyProductService;//商品到货通知
	private SpecService specService;
	private GiftService giftService;
	private ActivityService activityService;//活动
	private CatalogService catalogService;//分类
	private ActivityCountService activityCountService;//用户活动购买量
	
//	private int catalogID;//选择的产品目录ID
	private String catalogCode;//选择的目录code
	private int attributeID;//产品属性ID
	private String special;//促销活动
	private Map<String, String> orderMap;//排序map
	private List<Product> productList;//商品列表
	private int orderBy;//排序规则
	private int orderType;//排序顺序
	private Company company;//排序规则
	private String myPrice;//上次竞拍价格
	private String compType;//企业类型
	
	private String catalogType;//目录类型
	
	
	public FavoriteShopService getFavoriteShopService() {
		return favoriteShopService;
	}
	public void setFavoriteShopService(FavoriteShopService favoriteShopService) {
		this.favoriteShopService = favoriteShopService;
	}
	public void setActivityCountService(ActivityCountService activityCountService) {
		this.activityCountService = activityCountService;
	}
	public String getCatalogType() {
		return catalogType;
	}
	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public GiftService getGiftService() {
		return giftService;
	}

	public void setGiftService(GiftService giftService) {
		this.giftService = giftService;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public SpecService getSpecService() {
		return specService;
	}

	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}

	public void setEmailNotifyProductService(
			EmailNotifyProductService emailNotifyProductService) {
		this.emailNotifyProductService = emailNotifyProductService;
	}

	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
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

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void setAttribute_linkService(Attribute_linkService attribute_linkService) {
		this.attribute_linkService = attribute_linkService;
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

	@Override
	protected void selectListAfter() {
		String s = getRequest().getRequestURL().toString();
		if(s.contains("product!search.action")){
			String key = getRequest().getParameter("key");//搜索关键字
			key=(key==null)?"":key;
			pager.setPagerUrl("search.html?key="+key);
		}else if(s.contains("showCompanyProduct")){
			String id = getRequest().getParameter("id");
			pager.setPagerUrl(id+".html");
		}else{
			pager.setPagerUrl(this.catalogCode+".html");
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
//		getSession().setAttribute("selectMenu", "");

		String key = getRequest().getParameter("key");//搜索关键字
		String province = this.getRequest().getParameter("province");
		String city = this.getRequest().getParameter("city");
		String area = this.getRequest().getParameter("area");
		String type = this.getRequest().getParameter("type");
		this.getRequest().setAttribute("province", province);
		this.getRequest().setAttribute("city", city);
		this.getRequest().setAttribute("area", area);
		this.getRequest().setAttribute("type", type);
		//key = new String(key.getBytes("GBK"), "UTF-8"); 
		if(key==null || StringUtils.isBlank(key)){
			if(StringUtils.isBlank(city)){
				return "productList";
			}
		}
		
		String pm=getRequest().getQueryString();
		
		if(pm.contains("?")){
			pm=pm.replace("?", "&");
			getResponse().sendRedirect(getRequest().getContextPath()+"/search.html?"+pm);
		}
		
//		logger.error("search?key="+key);
		getRequest().setAttribute("key", key);
		
		getE().setName(key);
		getE().setProvince(province);
		getE().setCity(city);
		getE().setArea(area);
		productList = selectProductList0();
		getE().clear(); 
		 
		if(productList==null||productList.size()==0){
			Catalog cl=new Catalog();
			cl.setName(key);
			List<Catalog> list = catalogService.selectList(cl);
			if(list!=null&&list.size()>0){
			catalogCode=list.get(0).getCode();
			//getResponse().sendRedirect(getRequest().getContextPath()+"/catalog/"+catalogCode+".html");
			return productList();
			}
		}else{
			logger.error("productList="+productList.size());
		}
		return "productList";
	}
	/**
	 * teng
	 * 2015.05.19
	 * 旗舰店显示商品
	 * @throws Exception 
	 */
	public String showCompanyProduct() throws Exception{
		String id = getRequest().getParameter("id");
		if(id==null||id==""){
			throw new NullPointerException("参数错误!");
		}
		company=new Company();
		company.setId(id);
		company= companyService.selectOne(company);
		 if(company==null){
			 throw new NullPointerException("参数错误!");
		 }
		 e.setCompID(id);
		 productList = selectProductList0();
		 
		return "companyProductList";
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
	public String productList() throws Exception{
		String province = this.getRequest().getParameter("province");
		String city = this.getRequest().getParameter("city");
		String area = this.getRequest().getParameter("area");
		String type = this.getRequest().getParameter("type");
		this.getRequest().setAttribute("province", province);
		this.getRequest().setAttribute("city", city);
		this.getRequest().setAttribute("area", area);
		this.getRequest().setAttribute("type", type);
		try {
			if(StringUtils.isNotBlank(area)){
				getE().setArea(area);
			}else if(StringUtils.isNotBlank(city)){
				getE().setCity(city);
			}else if(StringUtils.isNotBlank(province)){
				getE().setProvince(province);
			}
			if(this.attributeID>0){
				getE().setAttrID(this.attributeID);
				Attribute attr = SystemManager.attrsMap.get(String.valueOf(this.attributeID));
				this.catalogCode = SystemManager.catalogsMap.get(String.valueOf(attr.getCatalogID())).getCode();
			}
			logger.error("special="+special + ",orderBy="+orderBy + ",catalogCode="+catalogCode);
			
			Catalog item = SystemManager.catalogsCodeMap.get(this.catalogCode);
			if(item==null){
				throw new NullPointerException("目录为空！");
			}
			logger.error("item.getId()="+item.getId());
			
			
			getSession().setAttribute("selectMenu", item.getId());//设置选择的商品目录
			
			logger.error("item.getId()="+item.getId());
			//添加可能是父类的类别ID到查询类别集合
			getE().getQueryCatalogIDs().add(Integer.valueOf(item.getId()));
			logger.error("getE().getQueryCatalogIDs()="+getE().getQueryCatalogIDs());
//			if(item.getPid().equals("0")){//主类别
//				e.setMainCatalogName(item.getName());
//				//大类ID
//				getE().setQueryCatalogIDs(new LinkedList<Integer>());
//				for(int j=0;j<item.getChildren().size();j++){
//					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
//					getE().getQueryCatalogIDs().add(Integer.valueOf(item.getChildren().get(j).getId()));
//				}
//			}else{//子类别
//				e.setChildrenCatalogName(item.getName());
//				item = SystemManager.catalogsMap.get(item.getPid());
//				getSession().setAttribute("selectMenu", item.getId());
//				e.setMainCatalogName(item.getName());
//				getE().setQueryCatalogIDs(new LinkedList<Integer>());
//				for(int j=0;j<item.getChildren().size();j++){
//					//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
//					getE().getQueryCatalogIDs().add(Integer.valueOf(item.getChildren().get(j).getId()));
//				}
//			}
			
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
		
		return "productList";
	}
	
	/**
	 * 加载热门、特价、最新的商品列表信息
	 * @return
	 * @throws Exception
	 */
	public String specialProductList() throws Exception{
		logger.error("special="+special);
//		getSession().setAttribute("selectMenu", -1);//不选择任何的主菜单
		e.setSpecial(special);
		//加载商品
		productList = selectProductList0();
		
		orderMap = KeyValueHelper.getMap("product_orderBy_");
		
		//指定分页请求的地址
		pager.setPagerUrl(special+".html");
		return "specialProductList";
	}
	
	/**
	 * 处理图片，后台上传的图片地址是这样的/myshop/attached/image/20130928/20130928233856_374.jpg
	 * 系统设置的图片服务器的地址是http://127.0.0.1:8082/myshop ，需要合并成正确的可以显示图片的地址
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void doImage(List<Product> productList){
		SystemSetting ssInfo = SystemManager.systemSetting;//(SystemSetting) CacheSingle.getInstance().get(FrontContainer.SystemSetting);
		if(productList==null || productList.size()==0){
			return ;
		}
		
		for(int i=0;i<productList.size();i++){
			Product p = productList.get(i);
			if(StringUtils.isNotEmpty(p.getPicture())){
				String picture = p.getPicture();
				picture = picture.substring(1);
				int firstChar = picture.indexOf("/");
				picture = picture.substring(firstChar);
				p.setPicture(ssInfo.getImageRootPath() + picture);
			}
		}
	}
	
	/**
	 * 用户浏览的商品信息存储在session中
	 * 由于存储的数量有限，每一个sessin中只存储最近的10个商品，并且只存储一些基本的信息,如：商品ID、商品名称、现价、原价。
	 * 这里需要用到数量固定的缓存策略，最后浏览的商品在第一个位置
	 */
	private void saveHistoryProductToSession() {
//		List<Product> history_product_list = (List<Product>) getSession().getAttribute(FrontContainer.history_product_list);
		LinkedHashMap<String, Product> history_product_map = (LinkedHashMap<String, Product>) getSession().getAttribute(FrontContainer.history_product_map);
//		LinkedHashMap<String, String> map = new LRULinkedHashMap<String, String>(16, 0.75f, true);
		if(history_product_map==null){
			history_product_map = new LRULinkedHashMap<String, Product>(16, 0.75f, true);
			getSession().setAttribute(FrontContainer.history_product_map,history_product_map);
		}
		
		//添加浏览过的商品信息到集合
		Product pro = new Product();
		pro.setId(e.getId());
		pro.setName(e.getName());
		pro.setPrice(e.getPrice());
		pro.setNowPrice(e.getNowPrice());
		pro.setPicture(e.getPicture());
		history_product_map.put(e.getId(),pro);
		
		//分离数据，方便页面显示
//		Collection<Product> historyList = history_product_map.values();
//		for(int i=historyList.size()-1;i>=0;i--){
//			historyList.
//		}
	}

	/**
	 * 根据商品ID检查商品信息
	 * @return
	 */
	private Catalog checkProduct() {
		if(StringUtils.isBlank(e.getId())){
			throw new NullPointerException("商品ID不能为空！");
		}
		
		e = productService.selectById(getE().getId());
		if(e==null){
			throw new NullPointerException("根据商品ID查询不到指定的商品信息！");
		}
		if(StringUtils.isBlank(e.getCatalogID())){
			throw new NullPointerException("商品无类别！");
		}
		
		/**
		 * 如果商品绑定了赠品，则读取绑定的赠品信息
		 */
		if(StringUtils.isNotBlank(e.getGiftID())){
			Gift gift = giftService.selectById(e.getGiftID());
			e.setGift(gift);
		}
		
		/**
		 * 根据商品信息去查询它的分类
		 * 商品分类以修改为多个分类
		 */
		Catalog item = SystemManager.catalogsMap.get(e.getCatalogID());
		if(item==null){
			throw new NullPointerException("商品数据异常！");
		}
		return item;
	}

	/**
	 * 商品详情页面，图片列表的处理
	 */
	private void productImagesBiz(String imagesStr) {
		if(StringUtils.isBlank(imagesStr)){
			return;
		}
		System.out.println("================="+imagesStr);
		String[] images = imagesStr.split(FrontContainer.product_images_spider);
		logger.error("e.getImages()="+e.getImages());
		if(e.getProductImageList()==null){
			e.setProductImageList(new LinkedList<ProductImageInfo>());
		}else{
			e.getProductImageList().clear();
		}
		for(int i=0;i<images.length;i++){
			String img = images[i].trim();
			int lastIndex = img.lastIndexOf("_");
			String left = img.substring(0, lastIndex+1);
			String right = img.substring(lastIndex+2);
			logger.error("left = "+left+",right="+right);
			e.getProductImageList().add(new ProductImageInfo(left+"1"+right,left+"2"+right,left+"3"+right));
		}
	}
	/**
	 * 分页加载产品列表，每4个一行的显示
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private List<Product> selectProductList0() throws Exception {
//		getE().setStatus(2);//加载已经上架的商品
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
//		doImage(result);
		return result;
//		return convert4(result);
	}
	
	public boolean isEmpty(String value){
		if(value==null || value.trim().length()==0){
			return true;
		}
		return false;
	}

	
	/**
	 * 获取新闻列表
	 * @return
	 * @throws Exception 
	 */
	public String newsList() throws Exception{
		News newsInfo = new News();
		newsInfo.setType(net.jeeshop.services.common.News.news_type_notice);
		
		setPager(super.selectPagerModelByServices(newsService, newsInfo));
		return "newsList";
	}
	
	
	/**
	 * 添加商品到收藏夹
	 * @return
	 * @throws IOException 
	 */
	public String addToFavorite() throws IOException{
		String productID = getRequest().getParameter("productID");
		if(StringUtils.isBlank(productID)){
			throw new NullPointerException(FrontContainer.request_illegal_error);
		}
		Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);
		if(acc==null || StringUtils.isBlank(acc.getAccount())){
			getResponse().getWriter().write("-1");
			return null;
		}
		Favorite favorite = new Favorite();
		favorite.setAccount(acc.getAccount());
		favorite.setProductID(productID);
		
		String result = null;
		synchronized (FrontContainer.insert_favorite_lock) {
			if(favoriteService.selectCount(favorite) == 0){
				favoriteService.insert(favorite);
				result = "0";//添加成功
			}else{
				result = "1";//已经添加过了
			}
		}
		getResponse().getWriter().write(result);
		return null;
	}
	//添加到收藏店铺
	public String addToFavoriteShop() throws IOException{
		String companyID = getRequest().getParameter("companyID");
		if(StringUtils.isBlank(companyID)){
			throw new NullPointerException(FrontContainer.request_illegal_error);
		}
		Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);
		if(acc==null || StringUtils.isBlank(acc.getAccount())){
			getResponse().getWriter().write("-1");
			return null;
		}
		FavoriteShop favoriteShop = new FavoriteShop();
		favoriteShop.setAccount(acc.getAccount());
		favoriteShop.setcompanyID(companyID);
		
		String result = null;
			if(favoriteShopService.selectList(favoriteShop).size()==0){
				favoriteShopService.insert(favoriteShop);
				result = "0";//添加成功
			}else{
				result = "1";//已经添加过了
			}
		getResponse().getWriter().write(result);
		return null;
	}
	
	/**
	 * 商品到货通知-ajax
	 * @return
	 * @throws IOException 
	 */
	public String insertEmailNotifyProductService() throws IOException{
		String productID = getRequest().getParameter("productID");
		String receiveEmail = getRequest().getParameter("receiveEmail");
		String productName = getRequest().getParameter("productName");
		if(StringUtils.isBlank(productID) || StringUtils.isBlank(receiveEmail)){
			throw new NullPointerException(FrontContainer.request_illegal_error);
		}
		
		Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);
		if(acc==null){
			getResponse().getWriter().write("-1");//用户需要登录
			return null;
		}
		
		EmailNotifyProduct info = new EmailNotifyProduct();
		info.setAccount(acc.getAccount());
		info.setReceiveEmail(receiveEmail);
		info.setProductID(productID);
		info.setProductName(productName);
		info.setStatus(net.jeeshop.services.common.EmailNotifyProduct.emailNotifyProduct_status_n);
		emailNotifyProductService.insert(info);
		
		getResponse().getWriter().write("0");//成功
		return null;
	}
	
	/**
	 * 加载促销活动的商品列表
	 * @return
	 * @throws Exception 
	 */
//	public String activityProductList() throws Exception{
//		logger.error("activityProductList...");
//		
//		//加载商品
////		productList = selectProductList0();
//		
//		//指定分页请求的地址
////		pager.setPagerUrl(special+".html");
//		return "activityProductList";
//	}
	
	/**
	 * test
	 * 内存库存查询
	 * @return
	 */
	public String selectMemoryStock(){
		
		return "selectMemoryStock";
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
	
	
	/**
	 * 根据类别目录加载商品
	 * @return
	 * @throws Exception
	 */
	public String listByType() throws Exception{
		try { 
			getSession().setAttribute("selectMenu", catalogType);//设置选择的商品目录
			
			List<CatalogType> list = SystemManager.catalogTypes;
			List<CatalogType> list2 = new ArrayList<CatalogType>();
			
			Set<String> set=new HashSet<String>();
			for (CatalogType ct : list) {
				if(ct.getType().equals(catalogType)){
				list2.add(ct);
				set.add(ct.getCatalogPName());
				if(StringUtils.isBlank(catalogCode)){
				getE().getQueryCatalogIDs().add(Integer.valueOf(ct.getCatalogID()));
				}
				}
			}
			if(StringUtils.isNotBlank(catalogCode)){
				getE().getQueryCatalogIDs().add(Integer.valueOf(catalogCode));
			}
			//加载商品
			productList = selectProductList0();
			
			getRequest().setAttribute("ctlist", list2);
			getRequest().setAttribute("ctset", set);
			
			orderMap = KeyValueHelper.getMap("product_orderBy_");
		} catch (Exception e) {
			logger.error("productList()异常："+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		pager.setPagerUrl(getRequest().getContextPath()+"/listByType/"+catalogType+".html");
		return "productListByType";
	}

	/**
	 * 查询指定的产品明细
	 * @return
	 * @throws Exception
	 */
	public String pointsProduct() throws Exception{
		e = productService.selectById(getE().getId());
		/*
		 * 商品分类已改为多分类
		 * 2016.01.06
		 * teng
		 Catalog item = checkProduct();//检查商品信息
		if(item.getPid().equals("0")){//主类别
			e.setMainCatalogName(item.getName());
		}else{//子类别
			e.setChildrenCatalogName(item.getName());
			e.setChildrenCatalogCode(item.getCode());
			item = SystemManager.catalogsMap.get(item.getPid());
			getSession().setAttribute("selectMenu", item.getId());//商品属于的大类别就是菜单
			e.setMainCatalogName(item.getName());
			
			getRequest().setAttribute("childrenCatalogCode", item.getCode());
		}
		*/
		
		saveHistoryProductToSession();
		
		//加载商品规格，以JSON字符串的形式隐藏在页面上，然后页面将其转换成对象集合，通过脚本控制规格的颜色和尺寸的双向关系。
		Spec spec = new Spec();
		spec.setProductID(e.getId());
		spec.setSpecStatus(Spec.spec_specStatus_y);
		List<Spec> specList = specService.selectList(spec);
		if(specList!=null && specList.size()>0){
			e.setSpecJsonString(JSON.toJSONString(specList));
			logger.error("e.setSpecJsonString = " + e.getSpecJsonString());
			
//			Set<String> specColor = new HashSet<String>(); 
//			Set<String> specSize = new HashSet<String>();
			
			if(e.getSpecColor()==null){
				e.setSpecColor(new ArrayList<String>());
			}
			if(e.getSpecSize()==null){
				e.setSpecSize(new ArrayList<String>());
			}
			
			//分离商品的尺寸和颜色
			for(int i=0;i<specList.size();i++){
				Spec specItem = specList.get(i);
				if(StringUtils.isNotBlank(specItem.getSpecColor())){
					e.getSpecColor().add(specItem.getSpecColor());
				}
				if(StringUtils.isNotBlank(specItem.getSpecSize())){
					e.getSpecSize().add(specItem.getSpecSize());
				}
			}
		}
		
		
		
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
		productImagesBiz(imagesBuff.toString());
		//getRequest().setAttribute("isDel", e.getisDel());
		
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
		
		/**
		 * 是否需要显示到货通知的按钮
		 */
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if(e.getStock()<=0){
			if(acc!=null){
				//如果用户之前没有填写过到货通知的申请，则可以提示用户填写。
				EmailNotifyProduct ep = new EmailNotifyProduct();
				ep.setAccount(acc.getAccount());
				ep.setProductID(e.getId());
				if(emailNotifyProductService.selectCount(ep)<=0){
					e.setShowEmailNotifyProductInput(true);
				}
			}
		}
		
		
		
		/*
		 * 检查，如果此商品是活动商品，则加载相应的活动信息。
		 */
		logger.error("e.getActivityID() = "+e.getActivityID());
		getRequest().setAttribute("isStart","n");
		e.setMaxSellCount(e.getStock());//最大购买数量
		if(StringUtils.isNotBlank(e.getActivityID())){
			logger.error(">>>计算或拷贝此商品关联的活动的信息到此商品对象上。展示页面用==");
			Activity activity = activityService.selectById(e.getActivityID());
			
			
			/**
			 * 计算或拷贝此商品关联的活动的信息到此商品对象上。展示页面用
			 */
			
			getRequest().setAttribute("nowTime",DateUtil.dateToStr(new Date(), "yyyy/MM/dd HH:mm:ss"));
			if(DateUtil.strToDatehhmmss(activity.getStartDate().substring(0,19)).after(new Date())){
				//除秒杀外不进行时间处理
				getRequest().setAttribute("isStart","n");
					e.setActivityType(activity.getActivityType());
					e.setStartDate(activity.getStartDate().substring(0,19).replace("-", "/"));
					e.setEndDate(activity.getEndDate().substring(0,19).replace("-", "/"));
			}else if(DateUtil.strToDatehhmmss(activity.getEndDate().substring(0,19)).before(new Date())){
				e.setActivityID(null);
				e.setActivityType(null);
				myCommonDao.executeSql(2, "update t_product set activityID=null,activityType=null where activityID="+activity.getId());
			}else{
				
			if(specList!=null && specList.size()>0){
				for (int i = 0; i < specList.size(); i++) {
					if(specList.get(i).getActivityPrice()!=null &&specList.get(i).getActivityPrice()>0){
						specList.get(i).setSpecPrice(specList.get(i).getActivityPrice()+"");
					}
				}
				e.setSpecJsonString(JSON.toJSONString(specList));
			}
				
				
			getRequest().setAttribute("isStart","y");
			if(e.getActivityPrice()!=null&&e.getActivityPrice()>0){
			e.setNowPrice(e.getActivityPrice()+"");//设置活动价格
			}
			e.setFinalPrice(String.valueOf(e.caclFinalPrice()));
			e.setExpire(activity.isExpire());
			e.setActivityEndDateTime(activity.getEndDate().substring(0,19).replace("-", "/"));
			e.setActivityType(activity.getActivityType());
			e.setDiscountType(activity.getDiscountType());
			e.setDiscountFormat(activity.getDiscountFormat());
			
			int count=0;
			if(activity.getMaxSellCount()>0&&acc!=null){ 
				ActivityCount ac=new ActivityCount(acc.getAccount(),activity.getId(),e.getId());
				ActivityCount ac2 = activityCountService.selectOne(ac);
				if(ac2!=null){
					count=ac2.getCount();
				}else{
					ac.setCount(0);
					activityCountService.insert(ac);
				}
				e.setMaxSellCount(activity.getMaxSellCount()-count);//活动最大购买数量
			}
			
			e.setAccountRange(activity.getAccountRange());
			e.setExchangeScore(activity.getExchangeScore());
			e.setTuanPrice(activity.getTuanPrice());
			e.setMinprice(activity.getMinprice());
			e.setActivityName(activity.getName());
			
			logger.error("finalPrice = " + e.getFinalPrice()+",expire = " + e.isExpire()+",activityEndDateTime="+e.getActivityEndDateTime()+",score="+e.getScore());
		
			
			}
		}
		
		Company com = companyService.selectById(e.getCompID());
		
		getRequest().setAttribute("comPic", com.getIcon());
		String u=getRequest().getParameter("u");
		if(u!=null&&!u.equals("")){
		Cookie  cookie = new Cookie("presenter",u);
		cookie.setPath(getRequest().getContextPath());
		cookie.setMaxAge(365*24*60*60);
		getResponse().addCookie(cookie);
		}
		//浏览记录
		List<Product> hisList=(List<Product>) getSession().getAttribute("historyProducts");
		if(hisList==null){
			hisList=new ArrayList<Product>();
		}
		hisList.add(0, e);
		if(hisList.size()>5){
			hisList=hisList.subList(0, 5);
		}
		 getSession().setAttribute("historyProducts", hisList);
		 
		 getRequest().setAttribute("isDel", e.getisDel());
		if(e.getCatalogID().equals(KeyValueHelper.get("vehicle_catalogID"))){
			return "vehicle";
		}else{
			return "pointsProduct";
		}
	}

	protected void selectPointsProductListAfter() {
		pager.setPagerUrl("points!pointsProductList.action");
	}
	public String pointsProductList() throws Exception{
		Account account = (Account)this.getSession().getAttribute("user_info");
		if (account==null) {
			return "toLogin";
		}
		e.setCatalogIdStr(account.getId());
		
		this.initPageSelect();
		int offset = pager.getOffset();//分页偏移量
		if (offset < 0)
			offset = 0;
		getE().setOffset(offset);
		
		pager=productService.getPointsProductByMoney(e);
		
		if(pager==null)pager = new Product();
		
		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		
		selectPointsProductListAfter();
		
		getSession().setAttribute("jf_Menu", "liebiao");
		return "productList";
	}
	public String index(){
		getSession().setAttribute("selectMenu", "jifen");
		getSession().setAttribute("jf_Menu", "shouye");
		return "toIndex";
	}
}
