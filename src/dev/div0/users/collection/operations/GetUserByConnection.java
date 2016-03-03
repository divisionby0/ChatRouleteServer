package dev.div0.users.collection.operations;

import java.util.Iterator;
import org.red5.server.api.IConnection;

import dev.div0.users.User;
import dev.div0.users.collection.Users;

public class GetUserByConnection {
	public User execute(IConnection connection, Users collection){
		
		User user = null;
    	Iterator<User> userCollectionIterator = collection.getIterator();
    	
    	while(userCollectionIterator.hasNext()){
    		User possibleUser = userCollectionIterator.next();
    		if(possibleUser.isSelfConnection(connection)){
    			user = possibleUser;
    			break;
    		}
    	}
    	return user;
	}
}
