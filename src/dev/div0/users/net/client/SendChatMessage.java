package dev.div0.users.net.client;

import org.red5.server.api.service.IServiceCapableConnection;

import dev.div0.BaseLogger;
import dev.div0.textChates.TextChatMessage;
import dev.div0.users.User;

public class SendChatMessage extends BaseLogger {
	public SendChatMessage(User from, User to, String message)
	{
		//log("sending chat message from "+from.getStreamId()+"  to "+to.getStreamId()+". Content: "+message);
		try{
			((IServiceCapableConnection)to.getConnection()).invoke("chatMessage",new Object[]{message});
		}
		catch(Exception exception){
			log("User "+to.getStreamId()+" connection is null");
			//log("User connection is null");
		}	
	}
}
