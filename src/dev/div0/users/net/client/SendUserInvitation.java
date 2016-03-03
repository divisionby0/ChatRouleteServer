package dev.div0.users.net.client;

import org.red5.server.api.service.IServiceCapableConnection;

import dev.div0.BaseLogger;
import dev.div0.users.User;

public class SendUserInvitation extends BaseLogger{
	public SendUserInvitation(User from, User to){
		
		//log("sending invitation from "+from.getStreamId()+"  to "+to.getStreamId());
		try{
			((IServiceCapableConnection)to.getConnection()).invoke("opponentConnectRequest",new Object[]{from.getStreamId(), from.getName()});
		}
		catch(Exception exception){
			log("User "+to.getStreamId()+" connection is null");
			//log("User connection is null");
		}	
	}
}
