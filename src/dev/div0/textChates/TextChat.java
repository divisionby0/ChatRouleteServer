package dev.div0.textChates;

import java.util.ArrayList;
import java.util.List;

public class TextChat {
	private List<TextChatMessage> messages;
	
	public TextChat(){
		messages = new ArrayList<TextChatMessage>();
	}
	
	public void add(TextChatMessage message){
		messages.add(message);
		
		// send message
	}
	
	public void destroy(){
		messages.clear();
		messages = null;
	}
}
