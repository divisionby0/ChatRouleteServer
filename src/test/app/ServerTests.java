package test.app;

import org.junit.Test;
import org.red5.server.api.IConnection;
import org.red5.server.net.rtmp.RTMPMinaConnection;

import dev.div0.users.User;
import dev.div0.users.collection.Users;


public class ServerTests {

	private Users users = new Users();
	
	
	
	private String user_1_streamName = "user1StreamName";
	private IConnection user_1_connection = new RTMPMinaConnection();
	private User user_1 = new User(user_1_streamName, user_1_connection);
	
	private String user_2_streamName = "user2StreamName";
	private IConnection user_2_connection = new RTMPMinaConnection();
	private User user_2 = new User(user_2_streamName, user_2_connection);
	
	@Test
	public void startTests(){
		
	}
}
