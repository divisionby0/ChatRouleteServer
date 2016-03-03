package dev.div0;

import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import dev.div0.users.ApplyOpponentsEachOther;
import dev.div0.users.User;
import dev.div0.users.collection.Users;
import dev.div0.users.collection.operations.CreateUser;
import dev.div0.users.collection.operations.FindUserOpponent;
import dev.div0.users.collection.operations.GetUserByConnection;
import dev.div0.users.collection.operations.RemoveUser;

public class ChatRouleteServer extends LoggingAppAdapter implements ApplicationEventPublisherAware, IUserRequests
{	
	private Users users;
	
	private ApplicationEventListener eventListener;
	private ApplicationEventPublisher eventPublisher;
	
	public ChatRouleteServer(ApplicationEventListener eventListener){
		super();
		this.eventListener = eventListener;
		eventListener.setCallbackReceiver(this);
		createUsersCollection();
	}
	
	private void createUsersCollection() {
		users = new Users();
	}

	/** {@inheritDoc} */
    @Override
	public boolean connect(IConnection conn, IScope scope, Object[] params) {
    	
    	
    	String streamID = "unknown stream";
    	
    	if(params.length>0){
    		streamID = (String)params[0];
    	}
    	
    	String name = "unknown";
    	
    	try{
    		name = (String)params[1];
    	}
    	catch(ArrayIndexOutOfBoundsException exception){
    		error("no name provided");
    	}
    	
    	log("onConnect conn="+conn+" params = "+params+"  streamId="+streamID+"  name="+name);
    	createUser(conn, streamID, name);
		return true;
	}
    
    /** {@inheritDoc} */
    @Override
	public void disconnect(IConnection conn, IScope scope) {
    	log("onDisconnect conn="+conn+"   scope="+scope);
		super.disconnect(conn, scope);
		removeUser(conn);
	}

	private void createUser(IConnection conn, String streamId, String name) {
		User user = new CreateUser(eventPublisher).execute(conn, streamId, name);
		users.add(user);
	}
    
    private void removeUser(IConnection connection) {
    	new RemoveUser(eventPublisher).execute(connection, users);
    }

	private void opponentFound(User user, User opponent) {
		if(opponent==null){
			//log("opponent for user "+user.getStreamId()+"  NOT found. Starting timer...");
			user.startTimer();
		}
		else{
			if(opponent.isCanBeOpponent()!=true){
				//log("random opponent for user "+user.getStreamId()+"  cannot be opponent. Starting timer...");
				user.startTimer();
			}
			else{
				//log("opponent for user "+user.getStreamId()+"  found. it is "+opponent.getStreamId());
				applyOpponents(user, opponent);
			}
		}
	}

	private void applyOpponents(User user, User newOpponent) {
		new ApplyOpponentsEachOther(user, newOpponent);
	}

	// service interface methods
	public void getOpponentRequest(IConnection connection){
    	//log("get opponent request");
    	User user = new GetUserByConnection().execute(connection, users);
    	
    	if(user!=null){
    		user.startSearch();
    	}
    	else{
    		error("user by connection not found");
    	}
    }
	
	public void stopSearchForOpponent(IConnection connection){
    	User user = new GetUserByConnection().execute(connection, users);
    	user.stopSearch();
	}
	public void sendChatMessageRequest(IConnection connection, String message){
		User user = new GetUserByConnection().execute(connection, users);
		user.sendChatMessageRequest(message);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void getRandomUser(User user) {
		User opponent = new FindUserOpponent().execute(user, users);
		opponentFound(user, opponent);
	}
}
