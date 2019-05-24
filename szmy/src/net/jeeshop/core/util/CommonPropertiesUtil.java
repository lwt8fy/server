package net.jeeshop.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonPropertiesUtil {

	public static String getValue(String fileName,String name){
		InputStream in = CommonPropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
    	Properties p = new Properties();
    	try { 
    		   p.load(in);
    		   in.close();
    		  } catch (IOException e) {
    		   System.out.println("===================>配置文件:"+fileName+",未找到");
    		  }
    		  String value= p.getProperty(name);
    		  p.clear();
   		   if(value==null){
   			value="";
   			   System.out.println("===================>配置文件:"+fileName+"中--"+name+"属性,未找到");   
   		   }
		return value;
	}
	
	/**
	 * 获取短信模版
	 * @param name
	 * @return
	 */
	public static String getSMStemValue(String name){
		return getValue("SMStemplate.properties", name);
	}
	/**
	 * 获取短信发送参数配置
	 * @param name
	 * @return
	 */
	public static String getSMSconValue(String name){
		return getValue("javaMsg.properties", name);
	}

}
