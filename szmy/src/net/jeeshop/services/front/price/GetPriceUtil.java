package net.jeeshop.services.front.price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.Price;
import net.jeeshop.services.common.Quote;
import net.jeeshop.services.front.quote.service.QuoteService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class GetPriceUtil {
	private QuoteService quoteService;
	public QuoteService getQuoteService() {
		return quoteService;
	}

	public void setQuoteService(QuoteService quoteService) {
		this.quoteService = quoteService;
	}
	private static final String priceFormat ="[^0-9.]";
	/**
	 *  获取生猪价格,网站http://www.pigcn.cn
	 * @author 滕武超
	 * @date 2016-1-9 下午06:26:40 
	 * @Description:
	 * type:(nsy:内三元,wsy:外三元,tzz:土杂猪)
	 */
    public static Map<String,Price> getSzPrice(String url,Map<String,Price> map,String type) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements ListDiv = doc.getElementsByTag("tr");
            for (Element element :ListDiv) {
                Elements e1 = element.getElementsByTag("FONT");
                for (int i = 0; i < e1.size(); i++) {
					Element e2 = e1.get(i);
					String text = e2.text().trim();
					Price price =map.get(text);
					if(price!=null){
						try{
						String p=e1.get(i+1).text().trim();
						if(text.equals("全国")){
							p = e1.get(i+2).text().trim();
						}
						if(type.equals("nsy"))price.setNsy(Double.valueOf(p));
						if(type.equals("wsy"))price.setWsy(Double.valueOf(p));
						if(type.equals("tzz"))price.setTzz(Double.valueOf(p));
						if(type.equals("ym"))price.setYm(Double.valueOf(p));
						if(type.equals("dp"))price.setDp(Double.valueOf(p));
						map.put(text, price);
						}catch (Exception e) {
							continue;
						}
					}
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取河南生猪
     * @author 滕武超
     * @date 2016-1-10 上午10:20:41 
     * @Description:
     */
 public static Map<String,Price> getHnsz(String url,Map<String,Price> map,String date){
	 Document doc;
 	try {
 		doc = Jsoup.connect(url).get();
 		Elements elements = doc.getElementsByTag("p");
 		 for (Element e1 :elements) {
 			 String text = e1.text().trim();
 			 if(text.contains("河南")){
 				 try{
 				 Price p=null;
 				 
 				 text = text.replace("河南", "");
 				 String[] ss = text.split(date);
 				 String s = ss[0].trim();
 				 String city = "";
 				 String area ="";
 				 if(s.contains("驻马店")){
 					 city="驻马店";
 					 area=s.replace("驻马店", "").trim();
 				 }else{
 				  city = s.substring(0,s.indexOf("市")+1).trim();
 				  area = s.substring(s.indexOf("市")+1).replace("　", "").replace(" ", "");
 				 }
 				 p = map.get(city+area);
 				 if(p==null){
 					 p=new Price();
 					 p.setProvince("河南省");
 	 				 p.setType("3");
 	 				 p.setCity(city);
 	 				 p.setArea(area);
 	 				 p.setCreateTime(DateUtil.dateToStr(new Date()));
 				 }
 				 String price ="";
 				 if(ss[1].contains("内三元")){
 					 price =ss[1].replace("生猪价格内三元", "").trim();
 					 p.setNsy(Double.valueOf(price));
 				 }else if(ss[1].contains("外三元")){
 					 price =ss[1].replace("生猪价格外三元", "").trim();
 					p.setWsy(Double.valueOf(price));
 				 }else if(ss[1].contains("土杂猪")){
 					 price =ss[1].replace("生猪价格土杂猪", "").trim();
 					p.setTzz(Double.valueOf(price));
 				 }
 				 map.put(city+area, p);
 				 }catch (Exception e) {
					continue;
				}
 			 }else if(text.contains("下一页")){
 				 Elements elements2 = e1.getElementsByTag("a");
 				 for (int i = 0; i < elements2.size()-1; i++) {
 					 Element e2 = elements2.get(i);
 					 System.out.println(e2.attr("href"));
 					map=getNextHnsz(e2.attr("href"),map,date);
 				 }
 			 }
 		}
 	} catch (IOException e) {
 		e.printStackTrace();
 	}
 	return map;
    }
	private static Map<String,Price> getNextHnsz(String url,Map<String,Price> map,String date) {
		// TODO Auto-generated method stub
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByTag("p");
			for (Element e1 :elements) {
				String text = e1.text().trim();
				if(text.contains("河南")){
					try{
					Price p=null;
					text = text.replace("河南", "");
					String[] ss = text.split(date);
					String s = ss[0].trim();
					String city = "";
					String area ="";
					if(s.contains("驻马店")){
						city="驻马店";
						area=s.replace("驻马店", "").trim();
					}else{
						city = s.substring(0,s.indexOf("市")+1).trim();
						area = s.substring(s.indexOf("市")+1).replace("　", "").replace(" ", "");
					}
					p = map.get(city+area);
	 				 if(p==null){
	 					 p=new Price();
	 					 p.setProvince("河南省");
	 	 				 p.setType("3");
	 	 				 p.setCity(city);
	 	 				 p.setArea(area);
	 	 				 p.setCreateTime(DateUtil.dateToStr(new Date()));
	 				 }
	 				String price ="";
	 				 if(ss[1].contains("内三元")){
	 					 price =ss[1].replace("生猪价格内三元", "").trim();
	 					 p.setNsy(Double.valueOf(price));
	 				 }else if(ss[1].contains("外三元")){
	 					 price =ss[1].replace("生猪价格外三元", "").trim();
	 					p.setWsy(Double.valueOf(price));
	 				 }else if(ss[1].contains("土杂猪")){
	 					 price =ss[1].replace("生猪价格土杂猪", "").trim();
	 					p.setTzz(Double.valueOf(price));
	 				 }
	 				 map.put(city+area, p);
					 }catch (Exception e) {
							continue;
						}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
    
    public static void testQgsz(){
    	 Document doc;
	        try {
	            doc = Jsoup.connect("http://www.pigcn.cn/Article/201601/472619.html").get();
	            Elements ListDiv = doc.getElementsByTag("tr");
	            for (Element element :ListDiv) {
	                Elements e1 = element.getElementsByTag("span");
	                for (int i = 0; i < e1.size(); i++) {
						Element e2 = e1.get(i);
						String text = e2.text().trim();
						System.out.println(text);
						
						if(text.equals("全国")){
							System.out.println(text);
							String p = e1.get(i+3).text().trim();
							System.out.println(p);
						}
					}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
	public static void main(String[] args) {
//		getHeNanPrice(0); 
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-13下午02:51:45
	 * 描述: 获取河南省生猪价格 当天
	 * @param page
	 * @return
	 */
	public static List<Quote> getHeNanPrice(String url,int page){
		ArrayList<Quote> list = new ArrayList<Quote>();
		String date = DateUtil.dateToStr(new Date()).substring(2);
		String endDate="";
		Document doc;
		try {
//			String url = "http://bj.zhue.com.cn/list-c-15-lx-0-s-8-sort-0.html";
			doc = Jsoup.connect(url+"?page="+page).get();
			Elements elements=doc.select("table.t_f tbody tr");
			for(int i =2;i<elements.size();i++){
				Elements td = elements.get(i).getElementsByTag("td");
				Quote quote = new Quote();
				Element tdOne = td.first();
				if(tdOne!=null){
					String html = tdOne.text();
					html = html.substring(0, 8);
					endDate = html;
					quote.setCreateTime("20"+html);
					if(!date.equals(html)){
						break;
					}
				}else{
					break;
				}
				 String str = td.get(1).text().replaceAll("([^\u4e00-\u9fa5]+)", "");
				 String pro =str.substring(0,str.indexOf("省")+1);
				 String city = str.replace(pro, "");
				 quote.setProvince(pro.replace("?", ""));
				 quote.setCity(city);
				 quote.setArea(td.get(2).text().replaceAll("([^\u4e00-\u9fa5]+)", ""));
				 quote.setType(td.get(3).text().replaceAll("([^\u4e00-\u9fa5]+)", ""));
				 quote.setSource("网络");
				 quote.setProductType(td.get(4).text().replaceAll("([^\u4e00-\u9fa5]+)", ""));
				 String pri = td.get(5).text().replaceAll(priceFormat, "");
				 quote.setPrice(Double.parseDouble(pri));
//				System.err.println(td.get(2).text().replaceAll("([^\u4e00-\u9fa5]+)", ""));
				 list.add(quote);
			}
			System.out.println(list);
			if(endDate.equals(date)){
				List<Quote> next = getHeNanPrice(url,page+1);
				if(next!=null && next.size()>0){
						list.addAll(next);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
    
}
