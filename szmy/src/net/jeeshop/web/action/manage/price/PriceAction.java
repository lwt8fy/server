package net.jeeshop.web.action.manage.price;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.Price;
import net.jeeshop.services.common.Quote;
import net.jeeshop.services.front.price.GetPriceUtil;
import net.jeeshop.services.front.price.service.PriceService;
import net.jeeshop.services.front.quote.service.QuoteService;

import org.apache.commons.lang.StringUtils;

public class PriceAction extends BaseAction<Price> {
	private static final long serialVersionUID = 1L;
	private PriceService priceService;
	private String nsyURL;
	private String wsyURL;
	private String tzzURL;
	private String ymURL;
	private String dpURL;
	private String hnsURL;
	private String date;
	private String jsonStr;
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	private Quote quote;
	private MyCommonDao myCommonDao;//
	private QuoteService quoteService;
	public QuoteService getQuoteService() {
		return quoteService;
	}

	public void setQuoteService(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}
	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub
		e.setCreateTime(DateUtil.dateToStr(new Date()));
		return super.selectList();
	}
	@Override
	public String insert() throws Exception {
		// TODO Auto-generated method stub
		e.setType("0");
		if(!e.getProvince().equals("全国")){
			e.setType("1");
		}
		if(StringUtils.isNotBlank(e.getCity())){
			e.setType("2");
		}
		if(StringUtils.isNotBlank(e.getArea())){
			e.setType("3");
		}
		e.setCreateTime(DateUtil.dateToStr(new Date()));
		priceService.insert(e);
		
		Price price1=new Price();
		price1.setCreateTime(DateUtil.dateToStr(new Date()));
		List<Price> list  = priceService.selectList(price1);
		SystemManager.toDayPrice=list;
		
		e=new Price();
		return selectList();
	}
	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		priceService.update(e);
		Price price1=new Price();
		price1.setCreateTime(DateUtil.dateToStr(new Date()));
		List<Price> list  = priceService.selectList(price1);
		SystemManager.toDayPrice=list;
		e=new Price();
		return selectList();
	}
	
	public String toLoad(){
		return "toLoad";
	}
	/**
	 * 根据传入url获取数据
	 * @author 滕武超
	 * @date 2016-1-10 上午08:38:55 
	 * @Description:
	 */
	public String insertByHtml() {
		Price price=new Price();
		
		if(StringUtils.isNotBlank(date)){
			price.setCreateTime(date);
		}else{
			price.setCreateTime(DateUtil.dateToStr(new Date()));
		}
		price.setWhereSql(" and type!='3' ");
		List<Price> list = priceService.selectList(price);
		
		
		Map<String,Price> map=new HashMap<String,Price>();
		for (Price p : list) {
			map.put(p.getProvince(), p);
		}
		if(StringUtils.isNotBlank(nsyURL))map=GetPriceUtil.getSzPrice(nsyURL, map, "nsy");
		if(StringUtils.isNotBlank(wsyURL))map=GetPriceUtil.getSzPrice(wsyURL, map, "wsy");
		if(StringUtils.isNotBlank(tzzURL))map=GetPriceUtil.getSzPrice(tzzURL, map, "tzz");
		if(StringUtils.isNotBlank(ymURL))map=GetPriceUtil.getSzPrice(ymURL, map, "ym");
		if(StringUtils.isNotBlank(dpURL))map=GetPriceUtil.getSzPrice(dpURL, map, "dp");
		
		
		//if(StringUtils.isNotBlank(hnsURL))map2=GetPriceUtil.getHnsz(hnsURL,map2, date);河南省已改为自动获取
		
		Set<String> ks = map.keySet();
		for (String s : ks) {
			Price p = map.get(s);
			priceService.update(p);
		}
		if(StringUtils.isBlank(date)||date.equals(DateUtil.dateToStr(new Date()))){
			Price price1=new Price();
			price1.setCreateTime(DateUtil.dateToStr(new Date()));
			List<Price> list2  = priceService.selectList(price1);
			SystemManager.toDayPrice=list2;
		}
		
		getRequest().setAttribute("message", "更新成功!");
		return "toLoad";
	}
	/**更新价格
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAllPrice(){
		String sz = "http://bj.zhue.com.cn/list-c-15.html";
		String ym = "http://bj.zhue.com.cn/list-c-15-lx-0-s-8-sort-0.html";
		String dp = "http://bj.zhue.com.cn/list-c-15-lx-0-s-9-sort-0.html";
		
		List<Quote> list = new ArrayList<Quote>();
		list = GetPriceUtil.getHeNanPrice(sz,0);
		
		List<Quote> list2 = new ArrayList<Quote>();
		list2 = GetPriceUtil.getHeNanPrice(ym,0);
		
		List<Quote> list3 = new ArrayList<Quote>();
		list3 = GetPriceUtil.getHeNanPrice(dp,0);
		String date = DateUtil.dateToStr(new Date());
		
		Quote quo = new Quote();
		quo.setSource("网络");
		quo.setCreateTime(date);
		quoteService.delete(quo);
		
		if(list==null || list.size()==0 || list2==null || list2.size()==0 ||list3==null || list3.size()==0){
			jsonStr = "价格信息暂时未获取到!";
			return "jsonStr";
		}else{
			try {
				quoteService.updateAll(list, list2, list3);
			} catch (Exception e) {
				e.printStackTrace();
				jsonStr = "已有价格信息更新失败!";
				return "jsonStr";
			}
		}
		try {
			//this.quoteService.updatePrice(); 更新以有价格
			String wsySql="update t_price C INNER JOIN(SELECT convert(avg(t.price),decimal(10,2))price,t.province,t.city,t.area,t.productType FROM " +
			" t_quote t LEFT JOIN   t_price p on p.province=t.province and  p.city = t.city and p.area = t.area where DATE_FORMAT(t.createTime, '%Y-%m-%d')" +
			" = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and t.type='生猪' and t.productType='外三元' " +
			" GROUP BY t.province, t.city,t.area,t.productType) A on A.province=C.province and  A.city = C.city and A.area = C.area" +
			" set C.wsy = A.price where DATE_FORMAT(C.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')and C.type='3'";
			String nsySql="update t_price C INNER JOIN(SELECT convert(avg(t.price),decimal(10,2))price,t.province,t.city,t.area,t.productType FROM " +
			" t_quote t LEFT JOIN   t_price p on p.province=t.province and  p.city = t.city and p.area = t.area where DATE_FORMAT(t.createTime, '%Y-%m-%d')" +
			" = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and t.type='生猪' and t.productType='内三元' " +
			" GROUP BY t.province, t.city,t.area,t.productType) A on A.province=C.province and  A.city = C.city and A.area = C.area" +
			" set C.nsy = A.price where DATE_FORMAT(C.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')and C.type='3'";
			String tzzSql="update t_price C INNER JOIN(SELECT convert(avg(t.price),decimal(10,2))price,t.province,t.city,t.area,t.productType FROM " +
					" t_quote t LEFT JOIN   t_price p on p.province=t.province and  p.city = t.city and p.area = t.area where DATE_FORMAT(t.createTime, '%Y-%m-%d')" +
					" = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and t.type='生猪' and t.productType='土杂猪' " +
					" GROUP BY t.province, t.city,t.area,t.productType) A on A.province=C.province and  A.city = C.city and A.area = C.area" +
					" set C.tzz = A.price where DATE_FORMAT(C.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')and C.type='3'";
			String ymSql = "update t_price C INNER JOIN  (SELECT convert(avg(t.price),decimal(10,2))  price,t.province,t.city,t.area," +
					"  t.productType FROM t_quote t LEFT JOIN   t_price p on p.province=t.province and  p.city = t.city and p.area = t.area " +
					"  where DATE_FORMAT(t.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and t.type='玉米' " +
					"  GROUP BY t.province,t.city,t.area,t.productType) A on A.province=C.province and  A.city = C.city and A.area = C.area set C.ym = A.price " +
					"  where DATE_FORMAT(C.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and C.type='3' ";
			String dpSql ="update t_price C INNER JOIN  (SELECT convert(avg(t.price),decimal(10,2))  price,t.province,t.city,t.area,t.productType FROM t_quote t " +
					" LEFT JOIN   t_price p on p.province=t.province and  p.city = t.city and p.area = t.area where DATE_FORMAT(t.createTime, '%Y-%m-%d') =" +
					" DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and t.type='豆粕'  " +
					" GROUP BY t.province,t.city,t.area,t.productType) A on A.province=C.province and  A.city = C.city and A.area = C.area set C.dp = A.price " +
					" where DATE_FORMAT(C.createTime, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')  and C.type='3'";
			myCommonDao.executeSql(2, wsySql);
			myCommonDao.executeSql(2, nsySql);
			myCommonDao.executeSql(2, tzzSql);
			myCommonDao.executeSql(2, ymSql);
			myCommonDao.executeSql(2, dpSql);
			//更新未有信息
			List<Quote> quotes= this.quoteService.getQuoteList("1");
			if(quotes!=null && quotes.size()>0){
				for(Quote qu:quotes){
					Price price = new Price();
					price.setProvince(qu.getProvince());
					price.setCity(qu.getCity());
					price.setArea(qu.getArea());
					price.setCreateTime(DateUtil.dateToStr(new Date()));
					price.setType("3");
					price.setAreaType("华中");
					Price price2 = this.priceService.selectOne(price);
					if(price2!=null){
						String update = "update t_price set ";
						String end =  "="+qu.getPrice() +" where id = "+price2.getId();
						if("土杂猪".equals(qu.getProductType())){
							end = update + "tzz" + end;
						}else if("外三元".equals(qu.getProductType())){
							end = update + "wsy" + end;
						}else if("内三元".equals(qu.getProductType())){
							end = update + "nsy" + end;
						}
						this.myCommonDao.executeSql(2, end);
					}else{
						String insert = "insert into t_price (areaType,province,city,area,type,createTime, ";
						String end = ") values('华中','"+qu.getProvince()+"','"+qu.getCity()+"','"+qu.getArea()+"','3', DATE_FORMAT(SYSDATE(), '%Y-%m-%d'),"+qu.getPrice()+")";
						if("土杂猪".equals(qu.getProductType())){
							end = insert + "tzz" + end;
						}else if("外三元".equals(qu.getProductType())){
							end = insert + "wsy" + end;
						}else if("内三元".equals(qu.getProductType())){
							end = insert + "nsy" + end;
						}
						this.myCommonDao.executeSql(2, end);
					}
				}
			}
			
			quotes = null;
			quotes= this.quoteService.getQuoteList("2");
			if(quotes!=null && quotes.size()>0){
				for(Quote qu:quotes){
					Price price = new Price();
					price.setProvince(qu.getProvince());
					price.setCity(qu.getCity());
					price.setArea(qu.getArea());
					price.setCreateTime(DateUtil.dateToStr(new Date()));
					price.setType("3");
					price.setAreaType("华中");
					Price price2 = this.priceService.selectOne(price);
					if(price2!=null){
						String update = "update t_price set ym="+qu.getPrice() +" where id = "+price2.getId();
						this.myCommonDao.executeSql(2, update);
					}else{
						String insert = "insert into t_price (areaType,province,city,area,type,createTime, ym) values('华中','"+qu.getProvince()+"','"+qu.getCity()+"','"+qu.getArea()+"','3', DATE_FORMAT(SYSDATE(), '%Y-%m-%d'),"+qu.getPrice()+")";
						this.myCommonDao.executeSql(2, insert);
					}
				}
			}
			quotes = null;
			quotes= this.quoteService.getQuoteList("3");
			if(quotes!=null && quotes.size()>0){
				for(Quote qu:quotes){
					Price price = new Price();
					price.setProvince(qu.getProvince());
					price.setCity(qu.getCity());
					price.setArea(qu.getArea());
					price.setCreateTime(DateUtil.dateToStr(new Date()));
					price.setType("3");
					price.setAreaType("华中");
					Price price2 = this.priceService.selectOne(price);
					if(price2!=null){
						String update = "update t_price set dp="+qu.getPrice() +" where id = "+price2.getId();
						this.myCommonDao.executeSql(2, update);
					}else{
						String insert = "insert into t_price (areaType,province,city,area,type,createTime, dp) values('华中','"+qu.getProvince()+"','"+qu.getCity()+"','"+qu.getArea()+"','3', DATE_FORMAT(SYSDATE(), '%Y-%m-%d'),"+qu.getPrice()+")";
						this.myCommonDao.executeSql(2, insert);
					}
				}
			}
			//未获取到的价格 取省价格
			
			String tzzj = "update t_price t INNER JOIN t_price c on  c.province=t.province   and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(c.createTime, '%Y-%m-%d') set t.tzz = c.tzz " +
			"  where  t.type='3' and c.type='1' and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(SYSDATE(), '%Y-%m-%d') and (t.tzz is null or t.tzz=0)";
			this.myCommonDao.executeSql(2, tzzj);
			String wsyj = "update t_price t INNER JOIN t_price c on  c.province=t.province    and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(c.createTime, '%Y-%m-%d') set t.wsy = c.wsy  " +
			"  where  t.type='3' and c.type='1' and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(SYSDATE(), '%Y-%m-%d') and (t.wsy is null or t.wsy=0)";
			this.myCommonDao.executeSql(2, wsyj);
			String nsyj = "update t_price t INNER JOIN t_price c on  c.province=t.province    and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(c.createTime, '%Y-%m-%d')  set t.nsy = c.nsy " +
			"  where  t.type='3' and c.type='1' and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(SYSDATE(), '%Y-%m-%d') and (t.nsy is null or t.nsy=0)";
			this.myCommonDao.executeSql(2, nsyj);
			String ymj="update t_price t INNER JOIN t_price c on  c.province=t.province    and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(c.createTime, '%Y-%m-%d')   set t.ym = c.ym  where " +
					"  t.type='3' and c.type='1' and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(SYSDATE(), '%Y-%m-%d') and (t.ym is null or t.ym=0)";
			this.myCommonDao.executeSql(2, ymj);
			String dpj="update t_price t INNER JOIN t_price c on  c.province=t.province   and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(c.createTime, '%Y-%m-%d')   set t.dp = c.dp   where " +
					"  t.type='3' and c.type='1'and DATE_FORMAT(t.createTime, '%Y-%m-%d')=DATE_FORMAT(SYSDATE(), '%Y-%m-%d') and (t.dp is null or t.dp=0)";
			this.myCommonDao.executeSql(2, dpj);
		} catch (Exception e) {
			e.printStackTrace();
			jsonStr = "新加价格信息更新失败!";
			return "jsonStr";
		}
		if(StringUtils.isBlank(jsonStr)){
			jsonStr="0";
		}
		return "jsonStr";
	}
		
		
	@Override
	public void insertAfter(Price e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new Price();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public Price getE() {
		return this.e;
	}

	public String getNsyURL() {
		return nsyURL;
	}

	public void setNsyURL(String nsyURL) {
		this.nsyURL = nsyURL;
	}

	public String getWsyURL() {
		return wsyURL;
	}

	public void setWsyURL(String wsyURL) {
		this.wsyURL = wsyURL;
	}

	public String getTzzURL() {
		return tzzURL;
	}

	public void setTzzURL(String tzzURL) {
		this.tzzURL = tzzURL;
	}

	public String getYmURL() {
		return ymURL;
	}

	public void setYmURL(String ymURL) {
		this.ymURL = ymURL;
	}

	public String getDpURL() {
		return dpURL;
	}

	public void setDpURL(String dpURL) {
		this.dpURL = dpURL;
	}

	public String getHnsURL() {
		return hnsURL;
	}

	public void setHnsURL(String hnsURL) {
		this.hnsURL = hnsURL;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public MyCommonDao getMyCommonDao() {
		return myCommonDao;
	}

	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}
	

}
