package dev.div0.users.net.client;

import org.red5.server.api.service.IServiceCapableConnection;

import dev.div0.BaseLogger;
import dev.div0.users.User;

public class SendUserStateChanged extends BaseLogger{
	public SendUserStateChanged(User to, String state){
		try{
			((IServiceCapableConnection)to.getConnection()).invoke("stateChanged",new Object[]{state});
		}
		catch(Exception exception){
			log("User "+to.getStreamId()+" connection is null");
		}
	}
}
