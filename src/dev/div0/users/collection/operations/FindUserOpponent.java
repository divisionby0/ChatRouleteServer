package dev.div0.users.collection.operations;

import dev.div0.BaseLogger;
import dev.div0.users.User;
import dev.div0.users.collection.Users;

public class FindUserOpponent extends BaseLogger {
	public User execute(User user, Users users){
		//log("Find user "+user.getStreamId()+" opponent");
		if(users.size()<2){
			error("only one user at collection");
			return null;
		}
		//log("FindUserOpponent user = "+user.getStreamId());
		User newOpponent = null;
		boolean isUserCanBeOpponent = user.isCanBeOpponent();
		//log("isUserCanBeOpponent = "+isUserCanBeOpponent);
		
		if(isUserCanBeOpponent){
			newOpponent = users.getRandomUser(user);
			if(newOpponent!=null){
				//log("Opponent for user "+user.getStreamId()+"  FOUND ! it is "+newOpponent.getStreamId());
				//log("newOpponent random = "+newOpponent.getStreamId()+" isCanBeOpponent="+newOpponent.isCanBeOpponent());
				
				boolean newOpponetCanBeOpponent = newOpponent.isCanBeOpponent();
				String newOpponentStreamId = newOpponent.getStreamId();
				String userStreamId = user.getStreamId();
				
				if(newOpponetCanBeOpponent!=true){
					newOpponent = null;
				}
				else{
					boolean streamsEqual = newOpponentStreamId.equals(userStreamId);
					if(streamsEqual == true){
						newOpponent = null;
					}
				}
			}
			else{
				error("opponent for user "+user.getStreamId()+"  not found");
			}
		}
		else{
			error("user "+user.getStreamId()+" cannot be opponent");
		}
		return newOpponent;
	}
}
