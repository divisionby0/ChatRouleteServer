package dev.div0.textChates;

import org.json.simple.JSONObject;

public class TextChatMessageConverter {
	public static String convert(TextChatMessage message){
		JSONObject obj = new JSONObject();
        
		obj.put("id", message.getId());
        obj.put("from", message.getSender().getName());
        obj.put("message", message.getMessage());

        return obj.toString();
	}
}
