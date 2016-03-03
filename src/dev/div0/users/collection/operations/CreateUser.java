package dev.div0.users.collection.operations;

import org.red5.server.api.IConnection;
import org.springframework.context.ApplicationEventPublisher;

import dev.div0.users.User;
import dev.div0.users.UserState;

public class CreateUser {
	private ApplicationEventPublisher eventPublisher;
	
	public CreateUser(ApplicationEventPublisher eventPublisher){
		this.eventPublisher = eventPublisher;
	}
	
	public User execute(IConnection connection, String streamId, String name){
		User user = new User(streamId, connection);
		//user.setState(UserState.IDLE);
		user.setEventPublisher(eventPublisher);
		return user;
	}
}
