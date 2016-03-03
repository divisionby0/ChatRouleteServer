package dev.div0.users.net.client;

import org.red5.server.api.service.IServiceCapableConnection;

import dev.div0.BaseLogger;
import dev.div0.users.User;

public class SendHello extends BaseLogger{
	public SendHello(User to){
		try{
			((IServiceCapableConnection)to.getConnection()).invoke("helloServerResponce",new Object[]{});
		}
		catch(Exception exception){
			log("User "+to.getStreamId()+" connection is null");
		}
	}
}
