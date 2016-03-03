package dev.div0.textChates;

import dev.div0.users.User;
import dev.div0.util.MathUtil;

public class TextChatMessage {
	private User sender;
	private User receiver;
	private String message;
	
	private int id = MathUtil.randInt(0, 900000);
	
	public TextChatMessage(User sender, User receiver, String message){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	
	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}
	
	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	public boolean isEqual(TextChatMessage messageToCheck){
		return id == messageToCheck.getId();
	}
}
