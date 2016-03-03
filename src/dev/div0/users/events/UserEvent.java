package dev.div0.users.events;

import org.springframework.context.ApplicationEvent;

import dev.div0.users.User;

public class UserEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	public static final String GET_RANDOM_OPPONENT_REQUEST = "getRandomOpponentRequest";
	
	private String type;
	
	private User user;
	
	public UserEvent(Object source, User user) {
		super(source);
		type = (String)source;
		this.user = user;
	}
	
	public String getType(){
		return type;
	}
	public User getUser(){
		return user;
	}
	
}
