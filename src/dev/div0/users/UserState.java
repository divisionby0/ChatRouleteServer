package dev.div0.users;

public enum UserState {
	SEARCHING("searching"),
	IDLE("idle"),
	CHATTING("chatting");
	
	private String state;
	
	UserState(String state) {
        this.state = state;
    }
}
