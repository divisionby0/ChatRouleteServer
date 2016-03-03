package dev.div0.users.collection.operations;

import org.red5.server.api.IConnection;
import org.springframework.context.ApplicationEventPublisher;

import dev.div0.BaseLogger;
import dev.div0.users.User;
import dev.div0.users.UserState;
import dev.div0.users.collection.Users;

public class RemoveUser extends BaseLogger{
	
	private ApplicationEventPublisher eventPublisher;
	
	public RemoveUser(ApplicationEventPublisher eventPublisher){
		this.eventPublisher = eventPublisher;
	}
	
	public void execute(IConnection connection, Users users){
		//log("removeUser");
    	
    	User userToRemove = new GetUserByConnection().execute(connection, users);
    	
		//log("userToRemove = "+userToRemove);
		
		if(userToRemove!=null){
			removeUser(userToRemove, users);	
		}
		else{
			error("disconnectedUser not found");
		}
	}
	public void execute(User user, Users users){
		removeUser(user, users);
	}
	
	private void removeUser(User userToRemove, Users users){
		userToRemove.stopTimer();
		userToRemove.setState(UserState.IDLE);
		User userToRemoveOpponent = userToRemove.getOpponent();
		//log("userToRemoveOpponent = "+userToRemoveOpponent);
		
		if(userToRemoveOpponent!=null){
			userToRemoveOpponent.onOpponentDrop();
		}
		users.remove(userToRemove);
		userToRemove.remove();
		userToRemove = null;
		//log("User removed collection size is "+users.size());
	}
}
