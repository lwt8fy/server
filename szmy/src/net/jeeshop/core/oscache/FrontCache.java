package net.jeeshop.core.oscache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.pay.alipay.alipayescow.config.AlipayConfig;
import net.jeeshop.core.util.DateTimeUtil;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppOrder;
import net.jeeshop.services.common.AppSdOrder;
import net.jeeshop.services.common.CatalogType;
import net.jeeshop.services.common.Price;
import net.jeeshop.services.common.ProductSd;
import net.jeeshop.services.common.ScrollPic;
import net.jeeshop.services.front.advert.AdvertService;
import net.jeeshop.services.front.advert.bean.Advert;
import net.jeeshop.services.front.appOrder.service.AppOrderService;
import net.jeeshop.services.front.appSdOrder.service.AppSdOrderService;
import net.jeeshop.services.front.area.AreaService;
import net.jeeshop.services.front.area.bean.Area;
import net.jeeshop.services.front.attribute.AttributeService;
import net.jeeshop.services.front.attribute.bean.Attribute;
import net.jeeshop.services.front.catalog.CatalogService;
import net.jeeshop.services.front.catalog.bean.Catalog;
import net.jeeshop.services.front.catalogType.service.CatalogTypeService;
import net.jeeshop.services.front.comment.CommentService;
import net.jeeshop.services.front.commentType.CommentTypeService;
import net.jeeshop.services.front.commentType.bean.CommentType;
import net.jeeshop.services.front.express.ExpressService;
import net.jeeshop.services.front.express.bean.Express;
import net.jeeshop.services.front.indexImg.IndexImgService;
import net.jeeshop.services.front.indexImg.bean.IndexImg;
import net.jeeshop.services.front.keyvalue.KeyvalueService;
import net.jeeshop.services.front.keyvalue.bean.Keyvalue;
import net.jeeshop.services.front.navigation.NavigationService;
import net.jeeshop.services.front.navigation.bean.Navigation;
import net.jeeshop.services.front.news.NewsService;
import net.jeeshop.services.front.news.bean.News;
import net.jeeshop.services.front.notifyTemplate.NotifyTemplateService;
import net.jeeshop.services.front.notifyTemplate.bean.NotifyTemplate;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.services.front.pay.PayService;
import net.jeeshop.services.front.pay.bean.Pay;
import net.jeeshop.services.front.price.service.PriceService;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.front.product.bean.ProductStockInfo;
import net.jeeshop.services.front.productSd.service.ProductSdService;
import net.jeeshop.services.front.systemSetting.SystemSettingService;
import net.jeeshop.services.front.systemSetting.bean.SystemSetting;
import net.jeeshop.services.manage.accountRank.AccountRankService;
import net.jeeshop.services.manage.accountRank.bean.AccountRank;
import net.jeeshop.services.manage.activity.ActivityService;
import net.jeeshop.services.manage.activity.bean.Activity;
import net.jeeshop.services.manage.hotquery.HotqueryService;
import net.jeeshop.services.manage.hotquery.bean.Hotquery;
import net.jeeshop.services.manage.scrollPic.ScrollPicService;
import net.jeeshop.services.manage.spec.SpecService;
import net.jeeshop.services.manage.spec.bean.Spec;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 * 
 * @author huangf
 * 
 */
public class FrontCache {
	private static final Logger logger = LoggerFactory.getLogger(FrontCache.class);
	
	/**
	 * manage后台
	 */
	private KeyvalueService keyvalueService;
	private SystemSettingService systemSettingService;
	private NewsService newsService;
	private CatalogService catalogService;
	private IndexImgService indexImgService;
	private NavigationService navigationService;
	private AttributeService attributeService;
	private PayService payService;
	private CommentTypeService commentTypeService;
	@SuppressWarnings("unused")
	private AreaService areaService;
	private ExpressService expressService;
	private AdvertService advertService;
	private NotifyTemplateService notifyTemplateService;
//	private OssService ossService;
	private OrderService orderService;
	@SuppressWarnings("unused")
	private CommentService commentService;
	private AccountRankService accountRankService;
	private ActivityService activityService;
	private HotqueryService hotqueryService;
	private AppSdOrderService appSdOrderService;
	private AppOrderService appOrderService;
	private SpecService specService;
	private PriceService priceService;
	private ProductSdService productSdService;
	private MyCommonDao	myCommonDao;
	
	private ScrollPicService scrollPicService;
	
	/**
	 * front前台
	 */
	private ProductService productService;

	
	private CatalogTypeService catalogTypeService;
	
	
	public void setProductSdService(ProductSdService productSdService) {
		this.productSdService = productSdService;
	}
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}

	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}

	public void setCatalogTypeService(CatalogTypeService catalogTypeService) {
		this.catalogTypeService = catalogTypeService;
	}

	public ScrollPicService getScrollPicService() {
		return scrollPicService;
	}

	public void setScrollPicService(ScrollPicService scrollPicService) {
		this.scrollPicService = scrollPicService;
	}

	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}

	public void setAppOrderService(AppOrderService appOrderService) {
		this.appOrderService = appOrderService;
	}

	public void setAppSdOrderService(AppSdOrderService appSdOrderService) {
		this.appSdOrderService = appSdOrderService;
	}

	public HotqueryService getHotqueryService() {
		return hotqueryService;
	}

	public void setHotqueryService(HotqueryService hotqueryService) {
		this.hotqueryService = hotqueryService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	public AccountRankService getAccountRankService() {
		return accountRankService;
	}

	public void setAccountRankService(AccountRankService accountRankService) {
		this.accountRankService = accountRankService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setNotifyTemplateService(NotifyTemplateService notifyTemplateService) {
		this.notifyTemplateService = notifyTemplateService;
	}

	public void setAdvertService(AdvertService advertService) {
		this.advertService = advertService;
	}

	public void setExpressService(ExpressService expressService) {
		this.expressService = expressService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	public void setCommentTypeService(CommentTypeService commentTypeService) {
		this.commentTypeService = commentTypeService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public void setIndexImgService(IndexImgService indexImgService) {
		this.indexImgService = indexImgService;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setSystemSettingService(
			SystemSettingService systemSettingService) {
		this.systemSettingService = systemSettingService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	public void setKeyvalueService(KeyvalueService keyvalueService) {
		this.keyvalueService = keyvalueService;
	}

	/**
	 * 加载key-value键值对
	 */
	public void loadKeyValue() {
		logger.info("load...");
		KeyValueHelper.load(keyvalueService.selectList(new Keyvalue()));
	}

	/**
	 * 加载系统配置信息
	 */
	public void loadSystemSetting() {
		SystemManager.systemSetting = systemSettingService.selectOne(new SystemSetting());
		if(SystemManager.systemSetting==null){
			throw new NullPointerException("未设置本地环境变量，请管理员在后台进行设置");
		}
		
		//从环境变量中分解出图集来。
		if(StringUtils.isNotBlank(SystemManager.systemSetting.getImages())){
			String[] images = SystemManager.systemSetting.getImages().split(ManageContainer.product_images_spider);
			if(SystemManager.systemSetting.getImagesList()==null){
				SystemManager.systemSetting.setImagesList(new LinkedList<String>());
			}else{
				SystemManager.systemSetting.getImagesList().clear();
			}
			
			for(int i=0;i<images.length;i++){
				SystemManager.systemSetting.getImagesList().add(images[i]);
			}
		}
		
		//分解信任登陆
//		if(StringUtils.isNotBlank(SystemManager.systemSetting.getBelieveLoginConfig())){
////			SystemManager.systemSetting.setBelieveLoginInfo(JSON.parseObject(SystemManager.systemSetting.getBelieveLoginConfig(), BelieveLoginInfo.class));
//		}
	}

	/**
	 * 加载插件配置
	 */
	public void loadPlugConfig() {
		
		/**
		 * 加载支付宝配置
		 */
		Pay pay = new Pay();
		pay.setCode(net.jeeshop.services.common.Pay.pay_code_alipayescow);
		pay = payService.selectOne(pay);
		SystemManager.alipayConfig = pay.getSeller();
		
		AlipayConfig.partner = pay.getPartner();
		AlipayConfig.key = pay.getKey1();
		
//		logger.error("SystemManager.alipayConfig="+SystemManager.alipayConfig);
		
		/**
		 * 加载评论配置
		 */
		CommentType commentType = new CommentType();
		commentType.setStatus(net.jeeshop.services.common.CommentType.commentType_status_y);
		commentType = commentTypeService.selectOne(commentType);
		SystemManager.commentTypeCode = commentType.getCode();
		logger.error("SystemManager.commentTypeCode="+SystemManager.commentTypeCode);
	}

	/**
	 * 加载所有的属性列表
	 */
	public void loadAttributeList() {
		List<Attribute> attrs = attributeService.selectList(new Attribute());
		SystemManager.attrs = attrs;
		
		/**
		 * 转换成map的形式存储
		 */
		if(attrs!=null && attrs.size()>0){
			Map<String, Integer> map = new HashMap<String, Integer>();
			SystemManager.attrsMap.clear();
			
			for(int i=0;i<attrs.size();i++){
				Attribute mainAttr = attrs.get(i);
				if(mainAttr.getPid()==0){
					map.put(mainAttr.getId(), mainAttr.getCatalogID());
				}else{
					Attribute newAttr = new Attribute();
					newAttr.setPid(mainAttr.getPid());
					SystemManager.attrsMap.put(mainAttr.getId(), newAttr);
				}
			}
			if(map.size()>0){
				for(Iterator<Entry<String, Attribute>> it = SystemManager.attrsMap.entrySet().iterator();it.hasNext();){
					Entry<String, Attribute> entry = it.next();
					String id = String.valueOf(entry.getValue().getPid());
//					logger.error("id="+id);
					if(map.get(id)!=null){
						entry.getValue().setCatalogID(map.get(id));
					}
				}
				
//				logger.error("SystemManager.attrsMap="+SystemManager.attrsMap);
//				logger.error("SystemManager.attrsMap(63) = "+SystemManager.attrsMap.get("63"));
			}
		}
	}

	/**
	 * 加载文章目录
	 */
	public void loadNewCatalogs() {
		Catalog c = new Catalog();
		c.setType("a");
		c.setPid("0");
		List<Catalog> newCatalogs = catalogService.selectList(c);
		if(newCatalogs!=null && newCatalogs.size()>0){
			for(int i=0;i<newCatalogs.size();i++){
				Catalog item = newCatalogs.get(i);
				
				//加载此目录下的所有文章列表
				News news = new News();
				news.setCatalogID(item.getId());
				List<News> newsList = newsService.selectList(news);
				item.setNews(newsList);
			}
		}
		SystemManager.newCatalogs = newCatalogs;
	}
	
	/**
	 * 加载文章列表
	 */
	@SuppressWarnings("deprecation")
	public void loadNews() {
		List<News> news = newsService.selectList(new News());
		if(news!=null && news.size()>0){
			SystemManager.newsMap.clear();
			for(int i=0;i<news.size();i++){
				News newsObj = news.get(i);
				SystemManager.newsMap.put(newsObj.getCode(),newsObj);
			}
		}
	}

	/**
	 * 如果类别ID是是主类别，则返回该主类别的下面所有子类别
	 * @param catalogID
	 * @return
	 */
	public static List<Catalog> loadCatalogChildren(String catalogID) {
		try {
			logger.error(">>catalogID="+catalogID);
			if(StringUtils.isBlank(catalogID)){
				throw new NullPointerException();
			}
			
			Catalog catalog = SystemManager.catalogsMap.get(catalogID);
			if(catalog==null){
				throw new NullPointerException();
			}
			
			if(catalog.getPid().equals("0")){
				if(catalog.getChildren()==null){
					logger.error(">>主类别catalog.getChildren()=0");
				}else{
					logger.error(">>主类别catalog.getChildren()="+catalog.getChildren().size());
				}
				//主类别
				return catalog.getChildren();
			}else{
				//子类别
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPid(String catalogID){
		if(StringUtils.isBlank(catalogID)){
			throw new NullPointerException();
		}
		Catalog catalog = SystemManager.catalogsMap.get(catalogID);
		if(catalog==null){
			throw new NullPointerException();
		}
		if(catalog.getPid().equals("0")){
			return catalog.getId();
		}else{
			catalog = SystemManager.catalogsMap.get(catalog.getPid());
			return catalog.getId();
		}
	}
	
	/**
	 * 根据商品目录ID加载属性列表
	 * 
	 * @param catalogID
	 */
	public static List<Attribute> loadAttrByCatalogID(int catalogID) {
		if (SystemManager.attrs == null || SystemManager.attrs.size() == 0) {
			return null;
		}

		List<Attribute> attrs = new LinkedList<Attribute>();
		for (int i = 0; i < SystemManager.attrs.size(); i++) {
			Attribute attr = SystemManager.attrs.get(i);
			if (attr.getPid() == -1) {
				continue;
			}

			if (attr.getCatalogID() == catalogID) {
				attrs.add(attr);// 添加主属性
				attr.getAttrList().clear();
				int id = Integer.valueOf(attr.getId());
				// 添加子属性列表
				for (int j = 0; j < SystemManager.attrs.size(); j++) {
					Attribute attr2 = SystemManager.attrs.get(j);
					if (attr2.getPid() == id) {
						attr.getAttrList().add(attr2);// 添加子属性到主属性的内部集合
					}
				}
			}
		}

		logger.error("attrs=" + attrs);
		return attrs;
	}

	/**
	 * 加载目录列表,树形结构
	 * @param loadProduct 是否加载商品数据。true：加载，false:不加载
	 * @throws Exception
	 */
	public void loadCatalogs(boolean loadProduct) throws Exception {
		logger.info("load...");
//		Catalog c = new Catalog();
//		c.setType("p");
//		c.setPid("0");
//		List<Catalog> catalogs = catalogService.loadRoot(c);
//		
//		System.out.println("result:" + catalogs);
//		
//		SystemManager.catalogs = catalogs;
		
//		loadCatalogs2();
		
		loadCatalogs2("p",SystemManager.catalogs);
		loadCatalogs2("a",SystemManager.catalogsArticle);
		
		logger.error("SystemManager.catalogs="+SystemManager.catalogs.size());
		logger.error("SystemManager.catalogsArticle="+SystemManager.catalogsArticle.size());
		
		SystemManager.catalogsMap.clear();
		SystemManager.catalogsCodeMap.clear();
		putToMap(SystemManager.catalogs,loadProduct);
	}
	
	/**
	 * 将商品目录结构转化为map的形式。
	 * @param catalogs
	 * @throws Exception
	 */
	public void putToMap(List<Catalog> catalogs,boolean loadProduct) throws Exception{
		if(catalogs==null || catalogs.size()==0){
			return;
		}
		
		for(int i=0;i<catalogs.size();i++){
			Catalog item = catalogs.get(i);
			
			if(loadProduct){
				//超级菜单里面的推荐商品
				loadsuperMenuProducts(item);
				
				//加载每个目录下的热门推荐商品列表
				loadHotProductByCatalog(item);
			}
			
			SystemManager.catalogsMap.put(item.getId(),item);
			
			if(SystemManager.catalogsCodeMap.get(item.getCode())!=null){
				logger.error("item.code = " + item.getCode());
				throw new Exception("错误：商品类别code重复!");
			}
			
			SystemManager.catalogsCodeMap.put(item.getCode(),item);
			if(item.getChildren()!=null && item.getChildren().size()>0){
				
				//递归调用
				putToMap(item.getChildren(),loadProduct);
			}
		}
	}
	
	/**
	 * 加载超级菜单的位置显示的热门推荐商品列表
	 * @param item
	 */
	private void loadsuperMenuProducts(Catalog item){
		if(!item.getPid().equals("0")){
			//子目录则不加载任何数据
			return;
		}
		
		if(item.getChildren()==null || item.getChildren().size()==0){
			return;
		}
		List<String> ids = new LinkedList<String>();
		for(int j=0;j<item.getChildren().size();j++){
			ids.add(item.getChildren().get(j).getId());
		}
		
		Product product = new Product();
		product.setTop(3);//显示的最大个数
		product.setProductIds(ids);//目录集合
		List<Product> superMenuProducts = productService.loadHotProductShowInSuperMenu(product);
		
//		if(superMenuProducts==null || superMenuProducts.size()==0){
//			logger.error("superMenuProducts = 0" + ",catalogCode = " + item.getCode());
//		}else{
//			logger.error("superMenuProducts = " + superMenuProducts.size()  + ",catalogCode = " + item.getCode());
//		}
		if(superMenuProducts!=null && superMenuProducts.size()>0){
			item.setSuperMenuProducts(superMenuProducts);
		}
	}
	
	/**
	 * 加载每个目录下的热门推荐商品列表（包括子目录）
	 * @param item
	 */
	private void loadHotProductByCatalog(Catalog item){
		if(item.getPid().equals("0") && (item.getChildren()==null || item.getChildren().size()==0)){
			return;
		}
		
		Product p = new Product();
		p.setTop(FrontContainer.default_page_left_product_size);
		
		if(item.getPid().equals("0")){
			List<Integer> ids = new LinkedList<Integer>();
			for(int j=0;j<item.getChildren().size();j++){
				ids.add(Integer.valueOf(item.getChildren().get(j).getId()));
			}
			p.setQueryCatalogIDs(ids);
		}else{
			p.setCatalogID(item.getId());
		}
		List<Product> hotProducts = productService.selectPageLeftHotProducts(p);
		
		if(hotProducts==null || hotProducts.size()==0){
			logger.error("loadHotProductByCatalog.hotProducts = 0" + ",catalogCode = " + item.getCode());
		}else{
			logger.error("loadHotProductByCatalog.hotProducts = " + hotProducts.size()  + ",catalogCode = " + item.getCode());
		}
		
		if(hotProducts!=null && hotProducts.size()>0){
			item.setHotProducts(hotProducts);
		}
	}

	/**
	 * 原来递归的方式修改为非递归方式。
	 * 非递归方法查询商品/文章目录结构，并且自动排序。
	 * @param type
	 * @param catalogs
	 */
	private void loadCatalogs2(String type,List<Catalog> catalogs){
		Catalog cc = new Catalog();
		cc.setType(type);
		List<Catalog> catalogsList = catalogService.selectList(cc);
		if(catalogsList!=null){
			Map<String, Catalog> map = new HashMap<String, Catalog>();
			Map<String, Catalog> map2 = new HashMap<String, Catalog>();
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				//------------加载一级目录
				if(StringUtils.isNotBlank(item.getPid()) && item.getPid().equals("0")){
					//是否在导航栏显示中文化
					if(item.getShowInNav().equals(net.jeeshop.services.common.Catalog.catalog_showInNav_y)){
						item.setShowInNavStr("是");
					}
					
					map.put(item.getId(), item);
					it.remove();
				}
			}
			//---------加载二级目录
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				if(item.getDeep()==2){
					Catalog rootItem = map.get(item.getPid());
					if(rootItem!=null){
						if(rootItem.getChildren()==null){
							rootItem.setChildren(new LinkedList<Catalog>());
						}
						rootItem.getChildren().add(item);
					}
					map2.put(item.getId(), item);
					it.remove();
				}
			}
			//--------加载三级目录
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				if(item.getDeep()==3){
					Catalog rootItem = map2.get(item.getPid());
					if(rootItem!=null){
						if(rootItem.getChildren()==null){
							rootItem.setChildren(new LinkedList<Catalog>());
						}
						rootItem.getChildren().add(item);
					}
					it.remove();
				}
			}
			if(catalogs==null){
				catalogs = new LinkedList<Catalog>();
			}else{
				catalogs.clear();
			}
			int j=1;
			for(Iterator<Entry<String, Catalog>> it = map.entrySet().iterator();it.hasNext();){
				catalogs.add(it.next().getValue());
				j++;
			}
			//对主类别和子类别进行排序
			Collections.sort(catalogs, new Comparator<Catalog>() {
				@Override
				public int compare(Catalog o1, Catalog o2) {
					if (o1.getOrder1() > o2.getOrder1()) {
						return 1;
					} else if (o1.getOrder1() < o2.getOrder1()) {
						return -1;
					}
					return 0;
				}
			});
			
			for(int i=0;i<catalogs.size();i++){
				if(catalogs.get(i).getChildren()==null){
					continue;
				}
				Collections.sort(catalogs.get(i).getChildren(), new Comparator<Catalog>() {
					@Override
					public int compare(Catalog o1, Catalog o2) {
						if (o1.getOrder1() > o2.getOrder1()) {
							return 1;
						} else if (o1.getOrder1() < o2.getOrder1()) {
							return -1;
						}
						return 0;
					}
				});
			}
		}
	}
	
	/**
	 * 加载门户滚动图片列表
	 */
	public void loadIndexImgs() {
		logger.info("loadIndexImgs...");
		IndexImg c = new IndexImg();
		SystemManager.indexImages = indexImgService.selectList(c);
	}

	/**
	 * 加载促销的商品
	 */
	public void selectTopProducts() {
		Product e = new Product();
		e.setStatus(1);
		e.setTop(8);

		List<Product> result = productService.selectList(e);
		if (result == null || result.size() == 0) {
			return;
		}

		System.out.println("result=" + result);

		List<List<Product>> list = new LinkedList<List<Product>>();
		int nn = 0;
		int maxLen = 4;
		int i = 0;
		List<Product> row = new LinkedList<Product>();
		for (Iterator<Product> it = result.iterator(); it.hasNext();) {
			Product ee = it.next();
			row.add(ee);
			nn++;
			i++;
			if (nn == maxLen) {
				list.add(row);
				nn = 0;
				row = new LinkedList<Product>();
			}

			if ((result.size() == 1 + i)) {
				if (row.size() == 0) {
					row.add(ee);
				}
				list.add(row);
				break;
			}
		}
		SystemManager.goodsTopList = list;// productTypeService.selectList(e);

		System.out.println("list====" + list);
	}

	/**
	 * 加载门户导航菜单
	 */
	public void loadNavigations() {
		logger.info("load...");
		Navigation nav = new Navigation();
		nav.setPosition("bottom");
		List<Navigation> navigations = navigationService.selectList(nav);
		SystemManager.navigations = navigations;
	}

	/**
	 * 加载特价商品、热门商品、新品商品  显示到首页的中下方位置
	 */
	public void loadProductsShowInIndex() {
		SystemManager.newProducts = loadProducts(1);
		SystemManager.saleProducts = loadProducts(2);
		SystemManager.hotProducts = loadProducts(3);
	}

	/**
	 * 加载随机推荐的商品
	 */
//	public void loadSuijiProducts() {
////		SystemManager.suijiProducts = loadProducts(2);
//		Product p = new Product();
//		p.setTop(10);
//		SystemManager.suijiProducts = productService.selectList(p);
//	}

	// 加载商品
	private List<Product> loadProducts(int type) {
		Product p = new Product();
		p.setTop(6);
		if (type == 1) {
			p.setIsnew(net.jeeshop.services.common.Product.Product_isnew_y);//最新
		} else if (type == 2) {
			p.setActivityType(net.jeeshop.services.common.Activity.activity_activityType_x);//限时特价
		} else if (type == 3) {
			p.setHot(true);//热门
		}
		return productService.selectList(p);
	}
	
	/**
	 * 加载系统通知
	 */
	private void loadNotices() {
//		Notice notice = new Notice(7);
//		notice.setOffset(0);
//		notice.setPageSize(7);
//		SystemManager.noticeList = noticeService.selectList(notice);
		
		News news = new News();
		news.setOffset(0);
		news.setPageSize(8);
		SystemManager.noticeList = newsService.selectNoticeList(news);
	}

	/**
	 * 加载商品内存库存
	 */
	public void loadProductStock() {
		List<ProductStockInfo> list = productService.selectStockList(new Product());
		List<Spec> list2 = specService.selectList(new Spec());
		synchronized (SystemManager.product_stock_lock) {
			if(SystemManager.productStockMap==null){
				SystemManager.productStockMap = new ConcurrentHashMap<String, ProductStockInfo>(list.size());
			}else{
				SystemManager.productStockMap.clear();
			}
			
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					ProductStockInfo p = list.get(i);
					for (Spec sp : list2) {
						if(sp.getProductID().equals(p.getId())){
							p.getSpecMap().put(sp.getSpecSize(), Integer.valueOf(sp.getSpecStock()));
						}
					}
					SystemManager.productStockMap.put(p.getId(), p);
				}
			}
		}
	}

	/**
	 * 加载指定商品的库存到内存
	 */
	public void loadProductStockByID(String productID) {
		if(StringUtils.isNotBlank(productID)){
			throw new NullPointerException("商品ID不能为空！");
		}
		
		Product p = new Product();
		p.setId(productID);
		List<ProductStockInfo> list = productService.selectStockList(p);
		if(list!=null && list.size()>0){
			synchronized (SystemManager.product_stock_lock) {
				SystemManager.productStockMap.put(productID,list.get(0));
			}
		}
	}
	
	/**
	 * 加载物流列表
	 */
	private void loadExpress(){
		List<Express> expressList = expressService.selectList(new Express());
		if(SystemManager.expressMap==null){
			SystemManager.expressMap = new HashMap<String, Express>(); 
		}else{
			SystemManager.expressMap.clear();
		}
		if(expressList!=null && expressList.size()>0){
			for(int i=0;i<expressList.size();i++){
				Express item = expressList.get(i);
				SystemManager.expressMap.put(item.getCode(), item);
			}
		}
	}

	/**
	 * 加载广告列表
	 */
	public void loadAdvertList(){
		Advert advert = new Advert();
		advert.setStatus(net.jeeshop.services.common.Advert.advert_status_y);
		List<Advert> advertList = advertService.selectList(advert);
		if(SystemManager.advertMap==null){
			SystemManager.advertMap = new HashMap<String, Advert>(); 
		}else{
			SystemManager.advertMap.clear();
		}
		if(advertList!=null && advertList.size()>0){
			for(int i=0;i<advertList.size();i++){
				Advert item = advertList.get(i);
				SystemManager.advertMap.put(item.getCode(), item);
			}
		}
	}
	
	/**
	 * 加载热门搜索列表
	 */
//	private void loadHotSearch(){
//		String productIDs = SystemManager.systemSetting.getProductIDs();
//		if(StringUtils.isNotBlank(productIDs)){
//			String[] arr = productIDs.split("\\|");
//			Product p = new Product();
//			if(p.getProductIds()==null){
//				p.setProductIds(new LinkedList<String>());
//			}else{
//				p.getProductIds().clear();
//			}
//
//			for(int i=0;i<arr.length;i++){
//				p.getProductIds().add(arr[i]);
//			}
//			SystemManager.hotSearchProductList = productService.selectHotSearch(p);
//		}
//	}
	
	/**
	 * 读取本地区域数据
	 */
	public void readJsonArea(){
		long start = System.currentTimeMillis();
		try {
			String path = FrontCache.class.getResource("/").getPath();
			logger.error("path = " + path);
			path = path.replace("%20", " ");
			File file = new File(path + "__area.json");
			logger.error(file.getAbsolutePath());
			List<String> list = FileUtils.readLines(file, "utf-8");
			logger.error("list.size()="+list.size());
			
			SystemManager.areaMap = JSON.parseObject(list.get(0),new TypeReference<Map<String,Area>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.error("readJsonArea time = " + (System.currentTimeMillis() - start));
	}
	
	/**
	 * 加载邮件模板列表
	 */
	public void loadNotifyTemplate(){
		List<NotifyTemplate> list = notifyTemplateService.selectList(null);
		if(list!=null && list.size()>0){
			SystemManager.notifyTemplateMap = new HashMap<String, NotifyTemplate>();
			for(int i=0;i<list.size();i++){
				NotifyTemplate item = list.get(i);
				SystemManager.notifyTemplateMap.put(item.getCode(), item);
			}
		}
	}

	/**
	 * 加载会员等级列表
	 */
	private void loadAccountRank(){
		List<AccountRank> list = accountRankService.selectList(new AccountRank());
		if(list!=null && list.size()>0){
			SystemManager.notifyTemplateMap = new HashMap<String, NotifyTemplate>();
			for(int i=0;i<list.size();i++){
				AccountRank item = list.get(i);
				SystemManager.accountRankMap.put(item.getCode(), item);
			}
		}
	}
	
	/**
	 * 加载热卖商品列表，此数据将会在门户的超级菜单上显示出来。 
	 */
//	private void loadHotProductShowInSuperMenu(){
//		for(int i=0;i<SystemManager.catalogs.size();i++){
//			Catalog item = SystemManager.catalogs.get(i);
//			
//			if(item.getChildren()==null || item.getChildren().size()==0){
//				continue;
//			}
//			List<String> ids = new LinkedList<String>();
//			for(int j=0;j<item.getChildren().size();j++){
//				ids.add(item.getChildren().get(j).getId());
//			}
//			
//			Product product = new Product();
//			product.setTop(3);//显示的最大个数
////			product.setCatalogID(item.getId());
//			product.setProductIds(ids);
//			List<Product> superMenuProducts = productService.loadHotProductShowInSuperMenu(product);
//			
//			if(superMenuProducts==null || superMenuProducts.size()==0){
//				logger.error("superMenuProducts = 0");
//			}else{
//				logger.error("superMenuProducts = " + superMenuProducts.size());
//			}
//			if(superMenuProducts!=null && superMenuProducts.size()>0){
//				item.setSuperMenuProducts(superMenuProducts);
//			}
//		}
//	}
	
	/**
	 * 加载首页左侧的商品列表，此位置的商品从全局加载
	 */
	private void loadIndexLeftProduct(){
		Product p = new Product();
		p.setTop(FrontContainer.default_page_left_product_size);
		SystemManager.indexLeftProduct = productService.selectPageLeftHotProducts(p);
	}
	
	/**
	 * 加载促销活动的商品
	 */
	public void loadActivityProductList(){
		SystemManager.activityProductMap.clear();
		if(SystemManager.activityMap.size()==0){
			return;
		}
		List<Product> list = productService.selectActivityProductList(null);
		if(list==null){
			logger.error("loadActivityProductList.list=0");
		}else{
			logger.error("loadActivityProductList.list="+list.size());
			for(int i=0;i<list.size();i++){
				Product p = list.get(i);
				Activity activity = SystemManager.activityMap.get(p.getActivityID());
				if(activity==null){
					logger.error(" p = " + p.getId());
					continue;
				}
				
				if(activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_c)){
					if(activity.getDiscountType().equals(net.jeeshop.services.common.Activity.activity_discountType_r)){
						addProductByDiscountType(p,activity);
					}else if(activity.getDiscountType().equals(net.jeeshop.services.common.Activity.activity_discountType_d)){
						addProductByDiscountType(p,activity);
					}else if(activity.getDiscountType().equals(net.jeeshop.services.common.Activity.activity_discountType_s)){
						addProductByDiscountType(p,activity);
					}
				}
			}
		}
	}
	
	//根据此商品的优惠类型,进行分组
	private void addProductByDiscountType(Product p,Activity activity) {
		List<Product> valueList = SystemManager.activityProductMap.get(activity.getDiscountType());
		if(valueList == null){
			valueList = new LinkedList<Product>();
			SystemManager.activityProductMap.put(activity.getDiscountType(), valueList);
		}
		
		//复制活动的属性过来
		p.setFinalPrice(String.valueOf(p.caclFinalPrice()));
		p.setExpire(activity.isExpire());
		p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
		p.setDiscountFormat(activity.getDiscountFormat());
		p.setMaxSellCount(activity.getMaxSellCount());
		valueList.add(p);
	}
	
	/**
	 * 加载积分商城商品列表
	 */
	public void loadActivityScoreProductList(){
		logger.error("loadActivityScoreProductList...");
		if(SystemManager.activityMap.size()==0){
			return;
		}
		
		List<String> productIds = new LinkedList<String>();
		for(Iterator<Entry<String, Activity>> it = SystemManager.activityMap.entrySet().iterator();it.hasNext();){
			Entry<String, Activity> entry = it.next();
			if(entry.getValue().getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_j) && 
					StringUtils.isNotBlank(entry.getValue().getProductID())){
				String[] arr = entry.getValue().getProductID().split("\\|");
				for(int i=0;i<arr.length;i++){
					productIds.add(arr[i]);
				}
			}
		}
		logger.error("loadActivityScoreProductList...productIds="+productIds.toString());
		if(productIds.size()>0){
			Product queryProduct = new Product();
			queryProduct.setProductIds(productIds);
			SystemManager.activityScoreProductList = productService.selectActivityProductList(queryProduct);
			
			//从活动中拷贝属性过去
			for(int i=0;i<SystemManager.activityScoreProductList.size();i++){
				Product p = SystemManager.activityScoreProductList.get(i);
				Activity activity = SystemManager.activityMap.get(p.getActivityID());
				if(activity==null){
					logger.error(" p = " + p.getId());
					continue;
				}
				
				if(!activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_c)){
					p.setExchangeScore(activity.getExchangeScore());
					p.setExpire(activity.isExpire());
					p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
//					p.setDiscountFormat(activity.getDiscountFormat());
					p.setMaxSellCount(activity.getMaxSellCount());
				}
			}
		}
	}
	/**
	 * 加载团购活动商品列表
	 */
	public void loadActivityTuanProductList(){
		logger.error("loadActivityTuanProductList...");
		if(SystemManager.activityMap.size()==0){
			return;
		}
		
		List<String> productIds = new LinkedList<String>();
		for(Iterator<Entry<String, Activity>> it = SystemManager.activityMap.entrySet().iterator();it.hasNext();){
			Entry<String, Activity> entry = it.next();
			if(entry.getValue().getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_t) && 
					StringUtils.isNotBlank(entry.getValue().getProductID())){
				String[] arr = entry.getValue().getProductID().split("\\|");
				for(int i=0;i<arr.length;i++){
					productIds.add(arr[i]);
				}
			}
		}
		logger.error("loadActivityScoreProductList...productIds="+productIds.toString());
		if(productIds.size()>0){
			Product queryProduct = new Product();
			queryProduct.setProductIds(productIds);
			SystemManager.activityTuanProductList = productService.selectActivityProductList(queryProduct);
			
			//从活动中拷贝属性过去
			for(int i=0;i<SystemManager.activityTuanProductList.size();i++){
				Product p = SystemManager.activityTuanProductList.get(i);
				Activity activity = SystemManager.activityMap.get(p.getActivityID());
				if(activity==null){
					logger.error(" p = " + p.getId());
					continue;
				}
				
				if(activity.getActivityType().equals(net.jeeshop.services.common.Activity.activity_activityType_t)){
					p.setExchangeScore(activity.getExchangeScore());
					p.setExpire(activity.isExpire());
					p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
//					p.setDiscountFormat(activity.getDiscountFormat());
					p.setMaxSellCount(activity.getMaxSellCount());
					p.setMinGroupCount(activity.getMinGroupCount());
					p.setTuanPrice(activity.getTuanPrice());
					p.setHasBuyGroupPerson(3212);//activity.getHasBuyGroupPerson());
				}
			}
		}
	}
	
	/**
	 * 加载所有的活动列表
	 */
	public void loadActivityMap(){
		SystemManager.activityMap.clear();
		List<Activity> list = activityService.selectList(new Activity());
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Activity activity = list.get(i);
				
				activity.setExpire(activity.checkActivity());
				
				if(!activity.isExpire()){
					//计算活动多久结束，是否已结束
					activity.setActivityEndDateTime(DateTimeUtil.getActivityEndDateTimeString(activity.getEndDate()));					
				}
				
//				//折扣类型 ，则进行折扣计算
//				if(activity.getDiscountType().equals(Activity.activity_discountType_d)){
//					activity.setDiscountFormat(String.valueOf(Double.valueOf(activity.getDiscount()) / 10D));
//				}
				
				SystemManager.activityMap.put(activity.getId(), activity);
			}
		}
	}
	
	/**
	 * 加载热门查询列表
	 */
	public void loadHotquery(){
		Hotquery h=new Hotquery();
		h.setType("sy");
		SystemManager.hotqueryList = hotqueryService.selectList(h);
	}
	public void loadJFHotquery(){
		Hotquery h=new Hotquery();
		h.setType("jf");
		SystemManager.hotjfqueryList = hotqueryService.selectList(h);
	}
	
	
	/**
	 * 
	 * @author 滕武超
	 * @date 2015-11-18 下午05:49:41 
	 * @Description:
	 * 加载新版本首页1,2,3,4,5,6楼商品列表,品牌
	 
	 */
	public void loadNewIndexAll(){
		Map<String, Catalog> map = SystemManager.catalogsCodeMap;
			SystemManager.indexFL8=new HashMap<String, List<Product>>();
			SystemManager.indexFL4=new HashMap<String, List<Product>>();
		for (String key : map.keySet()) {
			Catalog cl = map.get(key);
			   if(cl.getPid().equals("0")){
					Product p = new Product();
					p.setTop(8);
					p.setOrderBy(1);//根据orderNum和点击量排序
					for(Catalog ca:cl.getChildren()){
						for(Catalog ca2:ca.getChildren()){
							//如果存在多级目录的话，则此处可以把所有的目录ID全部循环出来，反正SQL语句使用in查询就可以了
							p.getQueryCatalogIDs().add(Integer.valueOf(ca2.getId()));
						}
					}
					
					/*
					 *修改目录查询方式,改为多个like
					 *2016.01.06
					 *teng 
					 */
					List<Integer> idlist = p.getQueryCatalogIDs();
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
						p.setCatalogIdStr(ids);
						}
					p.setQueryCatalogIDs(null);
					
					
					SystemManager.indexFL8.put(cl.getCode(),  productService.selectList(p));
					
					p.setTop(4);
					p.setOrderBy(2);//根据出售量排序
					SystemManager.indexFL4.put(cl.getCode(),  productService.selectList(p));
			   }
		}
		
	}
	
	/**
	 * 自动刷单
	 * @author 滕武超
	 * @date 2015-12-2 上午10:55:59 
	 * @Description:
	 */
	public void loadZdSd(){
		
		AppSdOrder asd=new AppSdOrder();
		asd.setNextTime(DateUtil.dateToStrSS());
		List<AppSdOrder> list = appSdOrderService.selectList(asd);
		if(list!=null&&list.size()>0){
			for (AppSdOrder ao : list) {
				
				if(ao.getStatus().equals("3")){
					AppOrder ap = appOrderService.selectById(ao.getOrderid());
					ap.setId(null);
					ap.setCreateTime(DateUtil.dateToStrSS());
					ap.setMarketingTime(DateUtil.dateToStr(new Date()));
					int id = appOrderService.insert(ap);
					ao.setOrderid(id+"");
					ao.setStatus("2");
				}else{
					
				AppOrder ap=new AppOrder();
				ap.setId(ao.getOrderid());
					
				if(ao.getStatus().equals("2"))ao.setStatus("4");
				else if(ao.getStatus().equals("4"))ao.setStatus("5");
				else if(ao.getStatus().equals("5"))ao.setStatus("6");
				else if(ao.getStatus().equals("6")){
				ao.setStatus("3");
				ap.setFinishTime(DateUtil.dateToStrSS());
				ao.setNextTime(DateUtil.dateToStr(DateUtil.addDay(new Date(), 2),"yyyy-MM-dd HH:mm:ss"));
				}
				ap.setOrderStatus(ao.getStatus());
				appOrderService.update(ap);
				}
				String s = KeyValueHelper.get("APP_ZDSD");
				if(s!=null&&!ao.getStatus().equals("3")){
					String[] ss = s.split(",");
					if(ss[0].equals("h")){
						ao.setNextTime(DateUtil.dateToStr((DateUtil.addHour(new Date(), Integer.valueOf(ss[1]))),"yyyy-MM-dd HH:mm:ss"));
					}else if(ss[0].equals("m")){
						ao.setNextTime(DateUtil.dateToStr((DateUtil.addMinute(new Date(), Integer.valueOf(ss[1]))),"yyyy-MM-dd HH:mm:ss"));
					}
				}
				appSdOrderService.update(ao);
			}
			
		}
	}
	
	/**
	 * 
	 * 加载分类商品滚动商标
	 * @author 靳友斌
	 * @date 2015年12月7日 16:44:35
	 */
	public void loadScrollPic(){
		SystemManager.scrollPicList=scrollPicService.selectList(new ScrollPic());
	}
	/**
	 * 加载积分商品
	 */
	public void loadPointsMallProductList(){
		SystemManager.pointsMallProductList=productService.selectPointsMallProduct(new Product());
	}
	public void loadHotPointsProduct(){
		SystemManager.hotPointsMallProductList=productService.getHotPointsProduct(new Product());
	}

	/**
	 * 加载全部的缓存数据
	 * @throws Exception 
	 */
	public void loadAllCache() throws Exception {
		logger.error("loadAllCache...");
		loadHotquery();
		loadJFHotquery();
		loadCatalogs(true);
		loadIndexLeftProduct();
		loadAttributeList();
		loadIndexImgs();
		loadKeyValue();
		loadNavigations();
		loadSystemSetting();
		loadPlugConfig();
//		loadArea();
		loadExpress();
		loadAdvertList();
		
		loadNewCatalogs();
		loadNews();
		
//		loadHotProducts();
//		loadNewProducts();
//		loadSaleProducts();
//		loadSuijiProducts();
		loadProductsShowInIndex();
		loadNotices();
		
		loadProductStock();
		loadAccountRank();
//		loadHotSearch();
		
		readJsonArea();
		loadNotifyTemplate();
		
		
		//加载所有的活动列表
		loadActivityMap();
		
		
//不明白为什么下面三个都存缓存,暂时去除  
//2015-12-25
//加载促销活动的商品
//		loadActivityProductList();
//		//加载积分商城商品列表
//		loadActivityScoreProductList();
//		//加载团购活动的商品列表
//		loadActivityTuanProductList();
		//加载积分商品
		loadPointsMallProductList();
		loadHotPointsProduct();
		//加载新版本首页1,2,3,4,5,6楼商品列表,品牌
		loadNewIndexAll();
		
		//加载分类滚动商标
		loadScrollPic();
		//猪贸通自动刷单
		loadZdSd();
		//牛羊分类
		loadCatalogTypes();
		//自动确认收货
		zdqrsh();
		
		//首页发货流水线
		loadGdfh();
		//团购刷单
		tuanGouSd();
		//每天加载省猪价
		loadPrice();
		this.loadShengXianXuJingXuanChanPin();
		this.loadShengXianXuJingXuanLeiBie();
		
		logger.error("前台缓存加载完毕!");
	}
	/**
	 * 加载每日价格
	 * @author 滕武超
	 * @date 2016-1-10 下午01:18:18 
	 * @Description:
	 */
	private void loadPrice() {
		// TODO Auto-generated method stub
		List<Price> list = SystemManager.toDayPrice;
		if(list==null||list.size()==0||!list.get(0).getCreateTime().substring(0, 10).equals(DateUtil.dateToStr(new Date()))){
		Price price1=new Price();
		price1.setCreateTime(DateUtil.dateToStr(new Date()));
		list = priceService.selectList(price1);
		}
		if(list==null||list.size()==0){
			list=new ArrayList<Price>();
			Price price=new Price();
			price.setWhereSql(" and createTime =(select createTime from t_price order by id desc limit 1) ");
			List<Price> plist = priceService.selectList(price);
			for (Price p : plist) {
				p.setId(null);
				p.setCreateTime(DateUtil.dateToStr(new Date()));
				priceService.insert(p);
				list.add(p);
			}
		}
		SystemManager.toDayPrice=list;
	}

	/**
	 * 团购刷单
	 * 最后30分钟对未满团的进行刷单,分三次,结束前10分钟全部刷完
	 * @author 滕武超
	 * @date 2016-1-8 下午06:13:21 
	 * @Description:
	 */
	private void tuanGouSd() {
		// TODO Auto-generated method stub
		String tuangou = KeyValueHelper.get("TUANGOU_SD");
		if(StringUtils.isNotBlank(tuangou)&&tuangou.equals("y")){
			
			ProductSd psd=new ProductSd();
			psd.setWhereSql(" and nextTime<'"+DateUtil.dateToStrSS()+"' ");
			List<ProductSd> list2 = productSdService.selectList(psd);
			for (ProductSd sd : list2) {
				if(sd.getStatus()!=2){
				sd.setStatus(sd.getStatus()+1);
				sd.setNextTime(DateUtil.addMinutetoStr(new Date(), 10));
				productSdService.update(sd);
				}else{
					productSdService.delete(sd);
				}
				myCommonDao.executeSql(2, "update t_product set hit=hit+"+(sd.getNum()*2)+", sellcount=sellcount+"+sd.getNum()+",activityBuyCount=activityBuyCount+"+sd.getNum()+" where id="+sd.getProductID());
				
			}
			
		Activity ac=new Activity();
		String sql=" and status='y' and activityType='t' and endDate>'"+DateUtil.dateToStrSS()+"' and endDate<'"+DateUtil.dateToStr(DateUtil.addMinute(new Date(), 30),"yyyy-MM-dd HH:mm:ss")+"'" +
				" and id not in (select distinct activityID from t_product_sd) ";
		ac.setWhereSql(sql);
		List<Activity> list = activityService.selectList(ac);
		if(list!=null&&list.size()>0){
			for (Activity activity : list) {
				String[] ss = activity.getProductID().split(",");
				for (String s : ss) {
					Product p = productService.selectById(s);
					int num=activity.getMinGroupCount()-p.getActivityBuyCount();
					if(num>0){
						int num2=(int)Math.ceil(Double.valueOf(num)/3);
						myCommonDao.executeSql(2, "update t_product set hit=hit+"+(num2*2)+", sellcount=sellcount+"+num2+",activityBuyCount=activityBuyCount+"+num2+" where id="+s);
						ac.setWhereSql(sql);
						ProductSd ps=new ProductSd();
						ps.setActivityID(activity.getId());
						ps.setProductID(s);
						ps.setStatus(1);
						ps.setNum(num2);
						ps.setNextTime(DateUtil.addMinutetoStr(new Date(), 10));
						productSdService.insert(ps);
					}
				}
			}
		}
		}
		
	}

	/**
	 * 首页滚动发货流水线
	 * @author 滕武超
	 * @date 2015-12-23 上午10:52:04 
	 * @Description:
	 */
	private void loadGdfh() {
		// TODO Auto-generated method stub
		SystemManager.indexGdfh=new ArrayList<List<String>>();
		for (int i = 0; i < 20; i++) {
			List<String> l=new ArrayList<String>();
			l.add(0,DateUtil.dateToStr(new Date(), "yyyyMMdd")+randString(4));
			l.add("1"+randString (12));
			SystemManager.indexGdfh.add(l);
		}
	}
	  private String randString (int length)
	    {
	    	Random r = new Random();
	        String ssource = "0123456789";
	        char[] src = ssource.toCharArray();
	            char[] buf = new char[length];
	            int rnd;
	            for(int i=0;i<length;i++)
	            {
	                    rnd = Math.abs(r.nextInt()) % src.length;

	                    buf[i] = src[rnd];
	            }
	            return new String(buf);
	    }
	/**
	 * 自动确认收货
	 * @author 滕武超
	 * @date 2015-12-20 下午03:16:41 
	 * @Description:
	 */
	private void zdqrsh() {
		orderService.szqrsh();
	}

	private void loadCatalogTypes() {
		// TODO Auto-generated method stub
		if(SystemManager.catalogTypes==null){
			SystemManager.catalogTypes=new ArrayList<CatalogType>();
		}
		SystemManager.catalogTypes=catalogTypeService.selectList(new CatalogType());
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-21下午01:52:18
	 * 描述: 加载生鲜畜产品
	 */
	private void loadShengXianXuJingXuanChanPin(){
		String values = KeyValueHelper.get("SXCJX");
		String pids [] = values.split(",");
		List<String> productIds = new ArrayList<String>();
		for(String str:pids){
			productIds.add(str);
		}
		Product product = new Product();
		product.setProductIds(productIds);
		SystemManager.shengXianChuJingXuan = this.productService.selectList(product);
	}
	
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-21下午01:52:18
	 * 描述: 加载生鲜畜产品类别
	 */
	private void loadShengXianXuJingXuanLeiBie(){
		SystemManager.sxcCatalogs = new ArrayList<CatalogType>();
		for(CatalogType type:SystemManager.catalogTypes){
			if("shengxianchuchanpin".equals(type.getType())){
				SystemManager.sxcCatalogs.add(type);
			}
		}
	}

	public static void main(String[] args) {
		String str = "10280|10281|10282";
		String[] arr = str.split("\\|");
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
}