package net.jeeshop.core.servlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class DownServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

@Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  doPost(request, response);
 }

@Override
public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	String path = request.getParameter("name");
 try {
	 String name ="";
	 if(path.equals("tg")){
		 path=ServletActionContext.getServletContext().getRealPath("/appDownload")+"/tuiGuang.xls";
		 name = "推广统计.xls";
	 }else if(path.equals("czsm")){
		 path=ServletActionContext.getServletContext().getRealPath("/help")+"/czsm.doc";
		 name = "操作说明.doc";
	 }
      else if(path.equals("wuliu")){
	    path=ServletActionContext.getServletContext().getRealPath("/help")+"/wuliu.doc";
	    name = "添加物流说明.doc";
     }
	 else{
		 return;
	 }
     name = new String(name.getBytes(),"iso-8859-1");
     response.setContentType("text/plain");
     response.setHeader("Content-Disposition", "attachment; filename=" + name);
	 
	 InputStream in = new FileInputStream(path);
     int b = 0;
     byte[] bs = new byte[1024];
     OutputStream os = response.getOutputStream();
    
     while((b=in.read(bs))!=-1){
         os.write(bs,0,b);
     }
     os.flush();
     os.close();
 } catch (Exception e) {
  e.printStackTrace();
 }
}
}
