package net.jeeshop.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.jeeshop.chat.client.ClientActivity;

public class ServerListen {
	
	/*  the PORT number for this application  */
	public static final int PORT=8855;
	private ServerSocket ss;

	public static void main(String args[]){
		new ServerListen().begin();
	}
	
	public void begin(){
		try{
			ss = new ServerSocket(PORT);
			System.out.println("聊天服务启动成功...");
			while(true)
			{
				Socket sock = ss.accept();
				new ClientActivity(sock,this);
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	public void closeServerSocket(){
		try{
			if(ss!=null&&!ss.isClosed()){
			ss.close();
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}
	public void updateFriendList(ClientActivity ca0) {
		ClientMap.getInstance().insert(ca0.getUserInfo().getId(), ca0);
	}
	
	public void removeOneClient(ClientActivity client0)
	{
		ClientMap.getInstance().remove(client0.getUserInfo().getId());
	}

	public ClientActivity getClientActivityById(int id) {
		return ClientMap.getInstance().getById(id);
	}
}
