package dev.div0.users;

import dev.div0.BaseLogger;

public class ApplyOpponentsEachOther extends BaseLogger{
	public ApplyOpponentsEachOther(User user, User opponent){
		//log("applying users each other");
		user.setOpponent(opponent);
		opponent.setOpponent(user);
	}
}
