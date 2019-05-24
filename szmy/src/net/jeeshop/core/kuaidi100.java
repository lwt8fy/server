package net.jeeshop.core;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class kuaidi100 {

    public static void main(String[] agrs)
    {
    	String content = "";
    try
    {
        URL url = new URL("http://www.kuaidi100.com/applyurl?key=f7719f90d5b82ed3&com=yunda&nu=3100397044123");
        URLConnection con = url.openConnection();
        con.setAllowUserInteraction(false);
        InputStream urlStream = url.openStream();
        byte b[] = new byte[10000];
        int numRead = urlStream.read(b);
        content = new String(b, 0, numRead);
        while (numRead != -1)
        {
            numRead = urlStream.read(b);
            if (numRead != -1)
            {
                // String newContent = new String(b, 0, numRead);
                String newContent = new String(b, 0, numRead, "UTF-8");
                content += newContent;
            }
        }
        urlStream.close();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
        System.out.println(content);
    }

}
