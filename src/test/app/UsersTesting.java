package test.app;

import org.red5.server.api.IConnection;
import org.red5.server.net.rtmp.RTMPClient;
import org.red5.server.net.rtmp.RTMPConnection;
import org.red5.server.net.rtmp.RTMPMinaConnection;

import dev.div0.users.User;
import dev.div0.users.collection.Users;

public class UsersTesting {
	private Users users = new Users();
	
	private String user_1_streamName = "user1StreamName";
	private IConnection user_1_connection = new RTMPMinaConnection();
	private User user_1 = new User(user_1_streamName, user_1_connection);
	
	private String user_2_streamName = "user2StreamName";
	private IConnection user_2_connection = new RTMPMinaConnection();
	private User user_2 = new User(user_2_streamName, user_2_connection);
	
	public UsersTesting(){
	
		// test adding users
		log("adding 1st user");
		users.add(user_1);
		if(users.size() == 1){
			log("users size = 1");
		}
		else{
			error("User size not 1");
		}
		
		log("adding 2nd user");
		users.add(user_2);
		if(users.size() == 2){
			log("users size = 2");
		}
		else{
			error("User size not 2");
		}
		
		// test finding disconnected user opponent
		
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
