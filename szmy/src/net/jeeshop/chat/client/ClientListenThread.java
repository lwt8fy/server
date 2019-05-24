package net.jeeshop.chat.client;

import java.io.BufferedReader;

import net.jeeshop.chat.beans.ChatEntity;
import net.jeeshop.chat.commons.GlobalMsgTypes;
import net.jeeshop.chat.commons.GlobalStrings;

public class ClientListenThread extends Thread{
	
	private ClientActivity mClientActivity;
	private BufferedReader mBuffRder;
	
	public ClientListenThread(ClientActivity ca0, BufferedReader br0)
	{
		mClientActivity = ca0;
		mBuffRder = br0;
	}
	
	@Override
	public void run()
	{
		super.run();

		while(true)
		{
			try{				
				String received = mBuffRder.readLine();
				if (received == null) {	      // if received == null, meaning the socket has been closed
					// remove the class/threads etc. associated with this specific client
					mClientActivity.goOffLine();
					return;
				}
				else
				{
					int msgType = Integer.parseInt(received);			// type of message received
					
					String actualMsg = mBuffRder.readLine();
					actualMsg = actualMsg.replace(GlobalStrings.replaceOfReturn,"\n");				

					try {
						System.out.println("收到类型为 " + msgType + " 的信息,发送人为: " + mClientActivity.getUserInfo().getName());
					} catch(Exception e) {}

					switch (msgType) {
					case GlobalMsgTypes.msgPublicRoom:
						ChatEntity msg=ChatEntity.Str2Ent(actualMsg);
						mClientActivity.receivedNewMsg(msgType,msg.toString());
						// add this ChatEntity data package into the system stack
						break;
					case GlobalMsgTypes.msgChattingRoom:
						/* to be added later */
						break;
					case GlobalMsgTypes.msgFromFriend:	
						ChatEntity msg2=ChatEntity.Str2Ent(actualMsg);
						mClientActivity.receivedNewMsg(msgType,msg2.toString());
						/* to be added later */
						break;
					case GlobalMsgTypes.msgHandShake:
						mClientActivity.startHandShake(actualMsg);
						break;
					case GlobalMsgTypes.msgSignUp:
						mClientActivity.trySignup(actualMsg);
						break;
					case GlobalMsgTypes.msgSearchPeople:
						mClientActivity.startSearchPeople(actualMsg);
						break;
					case GlobalMsgTypes.msgFriendshipRequest:
						System.out.println("one friend request comes");
						mClientActivity.startFriendshipRequest(actualMsg);
						break;
					case GlobalMsgTypes.msgFriendshipRequestResponse:
						mClientActivity.onFriendshipRequestResponse(actualMsg);
						break;
					case GlobalMsgTypes.msgUpdateUserInfo:
						mClientActivity.onUpdateUserInfo(actualMsg);
						break;
					case GlobalMsgTypes.msgAskForUnsendMsgs:
						mClientActivity.onAskForUnsendMsgs();
						break;
					case GlobalMsgTypes.msgMsgReceived:
						mClientActivity.responsedOfMsgReceived();
						break;
					case GlobalMsgTypes.msgBackOnline:
						mClientActivity.backOnline(actualMsg);
						break;
					default:
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("线程报错关闭!!!");
				mClientActivity.goOffLine();
				return;
			}
		}
	}
	
	public void closeBufferedReader()
	{
		try {
			mBuffRder.close();
		} catch(Exception e) {}
	}
}
