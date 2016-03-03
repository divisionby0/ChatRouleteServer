package dev.div0.users;

import org.red5.server.api.IConnection;
import org.red5.server.api.service.IServiceCapableConnection;

public class UserTextMessageSender {
	private IConnection receiver;
	private IServiceCapableConnection receiverConnection;
	private String senderName;
	
	public UserTextMessageSender(String senderName){
		this.senderName = senderName;
	}
	
	public void setReceiver(IConnection receiver){
		this.receiver = receiver;
	}
	public void send(String message){
		receiverConnection = (IServiceCapableConnection)receiver;
		boolean receiverConnected = receiverConnection.isConnected();
		if(receiverConnected){
			((IServiceCapableConnection)receiver).invoke("onTextChatMessage", new Object[]{message, senderName});
		}
		else{
			// TODO receiver not connected !!!
			error("receiver not connected");
		}
	}
	
	private void log(String value)
    {
    	System.out.println(value);
    } 
	private void error(String value)
	{
		System.err.println(value);
	} 
}
