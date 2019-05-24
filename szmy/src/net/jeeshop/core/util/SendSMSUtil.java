package net.jeeshop.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import net.jeeshop.core.KeyValueHelper;

public class SendSMSUtil {
	 private static String spnumber=CommonPropertiesUtil.getSMSconValue("msg.spnumber");
	  private static String username=CommonPropertiesUtil.getSMSconValue("msg.username");
	  private static String password=CommonPropertiesUtil.getSMSconValue("msg.password");
	  private static String url=CommonPropertiesUtil.getSMSconValue("msg.url");
	  
	  
	  
	  /**
	   * "互易无限" 平台接口
	   * @param msg
	   * @param phone
	   * @return
	   */
	  public static String  sendSMS(String msg,String phone){
		  String result=""; 
		  String postUrl = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
		    String account = "cf_nxt";
		    String password = MD5.md5("shenzhoumuyi123");
		     //msg = new String("您的验证码是：4321。请不要把验证码泄露给其他人。");
		    try {

		    	URL url = new URL(postUrl);
		    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    	connection.setDoOutput(true);//允许连接提交信息
		    	connection.setRequestMethod("POST");//网页提交方式“GET”、“POST”
		    	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    	connection.setRequestProperty("Connection", "Keep-Alive");
		    	StringBuffer sb = new StringBuffer();
		    	sb.append("account="+account);
		    	sb.append("&password="+password);
		    	sb.append("&mobile="+phone);
		    	sb.append("&content="+msg);
		    	OutputStream os = connection.getOutputStream();
		    	os.write(sb.toString().getBytes());
		    	os.close();

		    	String line= "";
		    	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		    	while ((line = in.readLine()) != null) {
		    		result += line + "\n";
		    	}
		    	in.close();

		    } catch (IOException e) {
		    	e.printStackTrace(System.out);
		    }
		    return result;
	  }
	 /**
	  * 原短信系统,已作废
	  * @author 滕武超
	  * @date 2016-1-29 上午08:51:30 
	  * @Description:
	  */
	  @Deprecated
	  public static String  sendSMS2(String msg,String phone){
		  String str=""; 
		  try {
			  msg = URLEncoder.encode(msg, "GBK");
			  String getURL = url + "?spnumber=" + spnumber + "&password=" + 
			  password + "&username=" + username + "&msg=" + 
			  msg + "&mobilephones=" + phone;
			  
			  System.out.println(getURL);
			  URL getUrl = new URL(getURL);
			  
			  HttpURLConnection connection = (HttpURLConnection)getUrl
			  .openConnection();
			  connection.connect();
			  BufferedReader reader = new BufferedReader(new InputStreamReader(
					  connection.getInputStream(), "GBK"));
			  String lines;
			  while ((lines = reader.readLine()) != null)
			  {
				  str+=lines;
			  }
			  reader.close();
			  
			  connection.disconnect();
		  } catch (Exception e) {
			  e.printStackTrace();
		  } 
		  return str;
	  }
	  
	  /**
	   * 生成规定位数的随机数字
	   * @author 滕武超
	   * @date 2016-1-28 下午02:01:50 
	   * @Description:
	   */
	  public static String randString (int length)
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
	 * 短信写入log日志
	 * @author 滕武超
	 * @date 2016-1-29 上午08:52:34 
	 * @Description:
	 */
	  public static void writeToFile(String filename,String text) {
		  String logUrl = KeyValueHelper.get("SMSlog_url");
		  	File f=new File(logUrl);
			if (!f.exists()) {
	            f.mkdirs();
	        }
			BufferedWriter bufferedWriter = null;
			try {
			bufferedWriter = new BufferedWriter(new FileWriter(logUrl+filename,true));
			bufferedWriter.write(text);
			bufferedWriter.newLine();
			} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			} catch (IOException ex) {
			ex.printStackTrace();
			} finally {
			//Close the BufferedWriter
			try {
			if (bufferedWriter != null) {
			bufferedWriter.flush();
			bufferedWriter.close();
			}
			} catch (IOException ex) {
			ex.printStackTrace();
			}
			}
	}
	  
}
