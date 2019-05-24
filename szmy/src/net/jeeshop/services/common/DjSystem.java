package net.jeeshop.services.common;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.jeeshop.core.util.HttpUtil;
import net.sf.json.JSONObject;

/**
 *动检系统配置
 *用于动检系统用户对接,申报
 */
public class DjSystem {
	
	public static String PDS_URL="http://demo.pdsxmj.gov.cn/clientrcp/szmy_treat.action";//平顶山地址
	
	public static String HENAN_URL="henan";//河南地址对应的字符串
	
	public static Map<String, String> urlMap=getUrlMap();//各区域网址
	
	/**
	 * 查询报检发送的参数
	 * @author 滕武超
	 * @throws IOException 
	 * @date 2016-2-19 上午10:28:54 
	 * @Description:
	 */
	public static String getDeclarationList_hn(ConnectUser cu,int PAGENO,int PAGESIZE) throws IOException{
		JSONObject jo=new JSONObject();
		jo.put("SYNC_ACT", "AIS-2001000200000001");
		jo.put("SYNC_NO", "getDeclarationList|获取申报单列表|remote|"+new Date().getTime());
		jo.put("USERNAME", cu.getUserName());
		jo.put("_GATEWAY_NAME", "HA_NETWORK");
		jo.put("SYNC_USR", cu.getUserName());
		jo.put("AREA1",cu.getArea1()==null?"":cu.getArea1());
		jo.put("AREA2",cu.getArea2()==null?"":cu.getArea2());
		jo.put("AREA3",cu.getArea3()==null?"":cu.getArea3());
		jo.put("AREA4",cu.getArea4()==null?"":cu.getArea4());
		jo.put("AREAID",cu.getArea1()+","+ cu.getArea2()+","+ cu.getArea3()+","+ cu.getArea1());
		jo.put("KEYWORD", "");
		jo.put("SEARCHLEVEL", "1");
		jo.put("PAGENO", PAGENO);
		jo.put("PAGESIZE", PAGESIZE);
		jo.put("SEARCHUSR", cu.getUserName());
		jo.put("__GPS_X", "");
		jo.put("__GPS_Y", "");
		jo.put("__IMEI", "");
		jo.put("__TOKEN", cu.getUserName()+"$OOV5DPm/vxzmQg4K6vs/vQ==");
		jo.put("__UID", cu.getUserName());
		jo.put("__VER", "APP.1.01");
		
		String jsonStr="syncTransferData=["+jo.toString()+"]&__tokens="+cu.getUserName()+"$OOV5DPm/vxzmQg4K6vs/vQ==";
		
		String json = HttpUtil.get(urlMap.get(HENAN_URL),jsonStr,null);
		JSONObject jo2=JSONObject.fromObject(json);
		Object s = jo2.get("AIS_RST");
		if(s.toString().equals("AIS-00000001")){
			jo2.put("result", "success");
		}else{
			jo2.put("result", "failed");
		}
		jo2.put("type", "3");
		return jo2.toString();
	}
	/**
	 * 查询报检发送的参数
	 * @author 滕武超
	 * @throws IOException 
	 * @date 2016-2-19 上午10:28:54 
	 * @Description:
	 */
	public static String getTest() throws IOException{
		String cs="getDeclarationList|获取申报单列表|remote|"+new Date().getTime();//自定义参数
		JSONObject jo=new JSONObject();
		jo.put("SYNC_ACT", "AIS-2001000200000001");
		jo.put("SYNC_NO", cs);
		jo.put("USERNAME", "10009858");
		jo.put("_GATEWAY_NAME", "HA_NETWORK");
		jo.put("SYNC_USR", "10009858");
		jo.put("AREA1","2c90813c0a252d31010a252e4ae70001");
		jo.put("AREA2","2c90813c0a252d31010a252ea5a80003");
		jo.put("AREA3","8a8a8a830a6f91c1010a720516ab0028");
		jo.put("AREA4","");
		jo.put("AREAID","2c90813c0a252d31010a252e4ae70001,2c90813c0a252d31010a252ea5a80003,8a8a8a830a6f91c1010a720516ab0028");
		jo.put("KEYWORD", "");
		jo.put("SEARCHLEVEL", "1");
		jo.put("PAGENO", 1);
		jo.put("PAGESIZE", 20);
		jo.put("SEARCHUSR", "10009858");
		
		jo.put("__GPS_X", "");
		jo.put("__GPS_Y", "");
		jo.put("__IMEI", "");
		jo.put("__TOKEN", "10009858$OOV5DPm/vxzmQg4K6vs/vQ==");
		jo.put("__UID", "10009858");
		jo.put("__VER", "APP.1.01");
		
		String jsonStr="syncTransferData=["+jo.toString()+"]&__tokens=10009858$OOV5DPm/vxzmQg4K6vs/vQ==";
		
		System.out.println(jsonStr);
		
		String json = HttpUtil.get("http://dongjian.hnahi.org.cn/dongjian/isv/terminalSyncEngine/spGateWay",jsonStr,"utf-8");
		JSONObject jo2=JSONObject.fromObject(json);
		Object s = jo2.get("AIS_RST");
		if(s.toString().equals("AIS-00000001")){
			jo2.put("result", "success");
		}else{
			jo2.put("result", "failed");
		}
		jo.put("type", "3");
		return jo2.toString();
	}
	
	/**
	 * 查询平顶山猪场报检发送的参数
	 * @author 滕武超
	 * @throws IOException 
	 * @date 2016-2-19 下午01:58:07 
	 * @Description:
	 */
	public static String getDeclarationList_pds_zc(ConnectUser cu,int pn,int ps) throws IOException{
		String url=PDS_URL+"?api_method=c.sblist&a_name="+cu.getUserName()+"&a_pwd="+cu.getPassword()+"&p_n="+pn+"&p_s="+ps;
		String str = HttpUtil.get(url, null, null);
		JSONObject jo=JSONObject.fromObject(str);
		jo.put("type", "1");
		return jo.toString();
	}
	/**
	 * 查询平顶山检疫员报检发送的参数
	 * @author 滕武超
	 * @throws IOException 
	 * @date 2016-2-19 下午01:58:07 
	 * @Description:
	 */
	public static String getDeclarationList_pds_jyy(ConnectUser cu,int pn,int ps) throws IOException{
		String url=PDS_URL+"?api_method=c.cllist&a_name="+cu.getUserName()+"&a_pwd="+cu.getPassword()+"&p_n="+pn+"&p_s="+ps;
		String str = HttpUtil.get(url, null, null);
		JSONObject jo=JSONObject.fromObject(str);
		jo.put("type", "2");
		return jo.toString();
	}
	
	
	/**
	 * 各区域网址
	 * @author 滕武超
	 * @date 2016-2-19 上午10:25:21 
	 * @Description:
	 */
	public static Map<String, String> getUrlMap() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("henan", "http://dongjian.hnahi.org.cn/dongjian/isv/terminalSyncEngine/spGateWay");
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		String test = getTest();
		System.out.println(test);
	}
	
}
