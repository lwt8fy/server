<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="net.jeeshop.core.util.ImageUtils"%>
<%@page import="net.jeeshop.core.oss.OSSObjectSample"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.jeeshop.services.front.systemSetting.bean.SystemSetting"%>
<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper" %>
<%-- <%@ page import="org.json.simple.*" %> --%>
<%

/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
Logger logger = LoggerFactory.getLogger(OSSObjectSample.class);
logger.error("upload_json.jsp>>>>>");
SystemSetting systemSetting = SystemManager.systemSetting;
//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/") + "attached/";
//String savePath = "D:\\workspace\\myshop\\web\\attached\\";//pageContext.getServletContext().getRealPath("/") + "attached/";
System.out.println(pageContext.getServletContext().getRealPath("/")+"attached/");
//文件保存目录URL
//String saveUrl  = systemSetting.getImageRootPath()+"/attached/";//request.getContextPath() + "/attached/";
String saveUrl  =systemSetting.getWww() + "/attached/";
//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
//最大文件大小
long maxSize = 1000000;
System.out.println(savePath);
System.out.println(saveUrl);
session.setAttribute("ajax_upload", 1);
response.setContentType("text/html; charset=UTF-8");
if(!ServletFileUpload.isMultipartContent(request)){
	System.out.println("111111122");
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	out.println(getError("上传目录不存在。"));
	return;
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}
//创建文件夹
savePath += dirName + "/";

saveUrl += dirName + "/";
File saveDirFile = new File(savePath);
if (!saveDirFile.exists()) {
	saveDirFile.mkdirs();
}
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
String ymd = sdf.format(new Date());
savePath += ymd + "/";
saveUrl += ymd + "/";
File dirFile = new File(savePath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request;     

//获得上传的文件名     

String fileName = wrapper.getFileNames("imgFile")[0];//imgFile,imgFile,imgFile     

//获得文件过滤器     

File file = wrapper.getFiles("imgFile")[0];     


//检查扩展名     

String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();     

if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt))  

{  

  out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));  

  return;  

}     

//检查文件大小     

if (file.length() > maxSize)  

{     

      out.println(getError("上传文件大小超过限制。"));     

      return;     

}        


//重构上传图片的名称      

SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");     

String newImgName = df.format(new Date()) + "_"    

              + new Random().nextInt(1000) + "." + fileExt;     

byte[] buffer = new byte[1024];     

//获取文件输出流     

FileOutputStream fos = new FileOutputStream(savePath +"/" + newImgName);     

//System.out.println("########" + saveUrl + "@@"+ newImgName);  

//获取内存中当前文件输入流     

InputStream in = new FileInputStream(file);     

try   

{     

      int num = 0;     

      while ((num = in.read(buffer)) > 0)  

      {     

              fos.write(buffer, 0, num);     

      }     

}   

catch (Exception e)  

{     

      e.printStackTrace(System.err);     

} finally   

{     

      in.close();     

      fos.close();     

}   

JSONObject obj = new JSONObject();  

obj.put("error", 0);  
obj.put("url", saveUrl + newImgName);  
out.println(obj.toJSONString());  

%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>