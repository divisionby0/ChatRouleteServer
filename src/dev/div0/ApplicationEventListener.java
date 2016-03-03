package dev.div0;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import dev.div0.users.User;
import dev.div0.users.events.UserEvent;

public class ApplicationEventListener extends BaseLogger implements ApplicationListener<ApplicationEvent>{

	private IUserRequests userRequests;
	
	public void setCallbackReceiver(IUserRequests userRequests){
		this.userRequests = userRequests;
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
		UserEvent userEvent = null;
		try{
			userEvent = (UserEvent)appEvent;
		}catch(ClassCastException exception){
			
		}
		
		if(userEvent==null){
			return;
		}
		
		String type = userEvent.getType();
		User user = userEvent.getUser();
		if(type.equals(UserEvent.GET_RANDOM_OPPONENT_REQUEST)){
			userRequests.getRandomUser(user);
		}
	}

}
