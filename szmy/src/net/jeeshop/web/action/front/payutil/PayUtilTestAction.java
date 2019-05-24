package net.jeeshop.web.action.front.payutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.util.DesEncrypt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PayUtilTestAction extends ActionSupport{
	private static final Logger logger = LoggerFactory.getLogger(PayUtilTestAction.class);
	private static final long serialVersionUID = 1L;
	
	
	
	private String jsonStr;
	private final String yhdm="yhdm";
	private final String JSONJSP="json";
	
	
	
	private final String error="{\"success\":\"0\"}";//失败
	private final String success="{\"success\":\"1\"}";//成功
	private final String methodError="{\"success\":\"2\"}";//请求方式为get错误
	private final String userError="{\"success\":\"3\"}";//用户身份验证错误
	
	
	private final String KEY="szmy123";//用户身份验证错误
	private final String URL="http://192.168.0.165:8080/animalshop/";//
	
	
	
	/**
	 * 显示订单详情
	 * @return
	 * @throws IOException 
	 */
	public String showOrder() throws IOException{
		Map<String,String> map=new HashMap<String,String>();
		map.put("orderid", "10344");
		map.put("username", "buy");
		map.put("password", "bbb");
		String encrypt = DesEncrypt.getEncString(JSONObject.fromObject(map).toString());
		getResponse().sendRedirect(URL+"payUtilAction!showOrder"+"?MSG="+encrypt);
		return null;
	}
	/**
	 * 修改订单状态
	 * @return
	 */
	public String updateOrderStatus(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("orderId", "10333");
		map.put("orderPayId", "11373");
		map.put("status", "1");
		map.put("username", "buy");
		map.put("password", "bbb");
		String encrypt = DesEncrypt.getEncString(JSONObject.fromObject(map).toString());
		String result =doPost(URL+"payUtilAction!updateOrderStatus", "MSG="+encrypt, "UTF-8");
		/*
		 * private final String error="{\"success\":\"0\"}";//失败
	private final String success="{\"success\":\"1\"}";//成功
	private final String methodError="{\"success\":\"2\"}";//请求方式为get错误
	private final String userError="{\"success\":\"3\"}";//用户身份验证错误
		 */
		jsonStr=result;
		return	JSONJSP;
	}
	/**
	 * 接受支付请求
	 * @return
	 */
	public String receivePayRequest(){
		String msg = getRequest().getParameter("MSG");
		msg=DesEncrypt.getDesString(msg);
		Map<String, Object> map = parseJSON2Map(msg);
		String orderid=(String) map.get("orderid");
		String orderPayId=(String) map.get("orderPayId");
		String remark=(String) map.get("remark");
		String compName=(String) map.get("compName");
		String tradePrice=(String) map.get("tradePrice");
		String comUserName=(String) map.get("comUserName");
		String username=(String) map.get("username");
		String password=(String) map.get("password");
		jsonStr=map.toString();
		return	JSONJSP;
	}
	
	public Map<String, Object> parseJSON2Map(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(parseJSON2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }  
	 public String doPost(String urlStr, String params, String charset) { 
     	PrintWriter out = null; 
     	BufferedReader in = null; 
     	StringBuffer result = new StringBuffer(); 
     	try { 
     	URL realUrl = new URL(urlStr); 
     	// 打开和URL之间的连接 
     	URLConnection conn = realUrl.openConnection(); 
     	// 设置通用的请求属性 
     	conn.setRequestProperty("accept", "*/*"); 
     	conn.setRequestProperty("connection", "Keep-Alive"); 
     	conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
     	conn.setRequestProperty("Charset", charset);  
     	// 发送POST请求必须设置如下两行 
     	conn.setDoOutput(true); 
     	conn.setDoInput(true); 
     	// 获取URLConnection对象对应的输出流 
     	out = new PrintWriter(conn.getOutputStream()); 
     	// 发送请求参数 
     	out.print(params); 
     	// flush输出流的缓冲 
     	out.flush(); 
     	// 定义BufferedReader输入流来读取URL的响应 
     	in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
     	String line = ""; 
     	while ((line = in.readLine()) != null) { 
     	result.append(line); 
     	} 
     	} catch (Exception e) { 
     		logger.error(e.toString()); 
     	} finally { 
     	try { 
     	if (out != null) { 
     	out.close(); 
     	out = null; 
     	} 
     	if (in != null) { 
     	in.close(); 
     	in = null; 
     	} 
     	} catch (Exception ex) { 
     		logger.error(ex.toString()); 
     	} 
     	} 
     	return result.toString(); 
     	} 

	public String testGetXml(){
		String method = getRequest().getParameter("method");
		Map<String,String> map=new HashMap<String,String>();
		map.put("username", "sell");
		map.put("password", "bbb");
		String encrypt = DesEncrypt.getEncString(JSONObject.fromObject(map).toString());
		String result =doPost(URL+method, "MSG="+encrypt, "UTF-8");
		jsonStr=DesEncrypt.getDesString(result);
		
		return JSONJSP;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getActionContext().get(
				StrutsStatics.HTTP_REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getActionContext().get(
				StrutsStatics.HTTP_RESPONSE);
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public Map<String,Object> getApplication() {
		return ActionContext.getContext().getApplication();
	}
}
