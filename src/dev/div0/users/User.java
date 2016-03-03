package dev.div0.users;

import java.util.Timer;
import java.util.TimerTask;

import org.red5.server.api.IConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import dev.div0.BaseLogger;
import dev.div0.textChates.TextChatMessage;
import dev.div0.textChates.TextChatMessageConverter;
import dev.div0.users.events.UserEvent;
import dev.div0.users.net.client.SendChatMessage;
import dev.div0.users.net.client.SendHello;
import dev.div0.users.net.client.SendUserInvitation;
import dev.div0.users.net.client.SendUserStateChanged;

public class User extends BaseLogger{
	private User self;
	private String streamId;
	private String name;
	private UserState state;
	private IConnection connection;
	private User opponent;
	private Timer timer;
	private TimerTask timerTask;
	private TextChatMessage lastChatMessage;
	private int searchTimerOffset = 1000;
	
	
	@Autowired
    public ApplicationEventPublisher applicationEventPublisher;
	
	public User(String streamId, IConnection connection){
		self = this;
		timer = new Timer();
		this.streamId = streamId;
		this.connection = connection;
		state = UserState.IDLE;
		sendStateChangedNotification();
		sendHello();
	}
	private void sendHello() {
		//log("sending hello...");
		new SendHello(this);
		//log("SendHello sent");
	}

	public void startTimer(){
		clearTimer();
		createTimerTask();
		timer.schedule(timerTask, searchTimerOffset);
	}
	
	private void createTimerTask() {
			timerTask = new TimerTask() {
			
			@Override
			public void run() {
				//log("USER find opponent request");
				sendFindNewOpponentRequest();
			}
		};
	}

	private void clearTimer() {
		if(timerTask!=null){
			timerTask.cancel();
		}
		
		if (timer != null) {
			try{
				timer.cancel();
				timer = null;
			}
			catch(Exception timerException){
				error("timer exception");
			}
		}
		timer = new Timer();
	}

	public void stopTimer(){
		clearTimer();
	}
	//outside client wants to send message
	public void sendChatMessageRequest(String message){
		if(opponent!=null){
			User sender = this;
			lastChatMessage = new TextChatMessage(sender, opponent, message);
			opponent.onChatMessage(sender, lastChatMessage);
		}
		else{
			error("send chat message error - opponent is null");
		}
	}
	
	public void onChatMessage(User from, TextChatMessage message){
		lastChatMessage = message;
		String messageJson = TextChatMessageConverter.convert(message);
		new SendChatMessage(from, this, messageJson);
	}
	
	public void onOpponentDrop(){
		log("User "+streamId+"  on opponent drop state="+state);
		opponent = null;
		if(state.equals(UserState.CHATTING)){
			log("USer "+streamId+"  start new search");
			state = UserState.SEARCHING;
			sendStateChangedNotification();
			sendFindNewOpponentRequest();
		}
	}
	
	private void sendFindNewOpponentRequest() {
		UserEvent userEvent = new UserEvent(UserEvent.GET_RANDOM_OPPONENT_REQUEST, self);
		
		if(applicationEventPublisher!=null){
			applicationEventPublisher.publishEvent(userEvent);
		}
		else{
			error("publisher is null :(");
		}
	}
	public String getStreamId(){
		return streamId;
	}
	
	public boolean isSelfConnection(IConnection connectionToTest){
		return connection==connectionToTest;
	}
	public boolean isSelfStreamId(String streamIdToCheck){
		return streamId.equals(streamIdToCheck);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void startSearch(){
		if(state.equals(UserState.CHATTING)){
			// stop opponent
			stopOpponent();
		}
		state = UserState.SEARCHING;
		sendStateChangedNotification();
		sendFindNewOpponentRequest();
	}
	private void stopOpponent() {
		if(opponent!=null){
			opponent.onOpponentDrop();
			opponent = null;
		}
	}
	
	public void stopSearch(){
		log("User "+streamId+"  stop search");
		// my state
		state = UserState.IDLE;
		sendStateChangedNotification();
		// notify opponent
		if(opponent!=null){
			log("User "+streamId+" opponent="+opponent.getStreamId());
			opponent.onOpponentDrop();
			opponent = null;
		}
		else{
			log("User "+streamId+"  NO opponent!");
		}
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(UserState state) {
		this.state = state;
	}

	/**
	 * @param opponent the opponent to set
	 */
	public void setOpponent(User opponent) {
		stopTimer();
		this.opponent = opponent;
		sendInvitations();
		state = UserState.CHATTING;
		sendStateChangedNotification();
	}
	public boolean hasOpponent(){
		return (opponent!=null);
	}
	public boolean isItOpponent(User user){
		if(opponent==null){
			return false;
		}
		else{
			String userOpponentStreamId = opponent.getStreamId();
			String userToCheckStreamId = user.getStreamId();
			
			if(userOpponentStreamId.equals(userToCheckStreamId)){
				return true;
			}
			else{
				return false;
			}
		}
	}

	private void sendInvitations() {
		SendUserInvitation userToOpponentInvitation = new SendUserInvitation(this, opponent);
		//SendUserInvitation opponentToUserInvitation = new SendUserInvitation(opponent, this);
	}
	private void sendStateChangedNotification(){
		new SendUserStateChanged(this, state.toString());
	}

	/**
	 * @return the opponent
	 */
	public User getOpponent() {
		return opponent;
	}
	
	public IConnection getConnection(){
		return connection;
	}
	
	public boolean isSearching(){
		return state.equals(UserState.SEARCHING);
	}
	public boolean isIdle(){
		return state.equals(UserState.IDLE);
	}
	public boolean isChatting(){
		return state.equals(UserState.CHATTING);
	}
	public boolean isCanBeOpponent(){
		return state.equals(UserState.SEARCHING);
	}
	public void remove(){
		if(connection!=null){
			connection.close();
		}
	}

	public void setEventPublisher(ApplicationEventPublisher arg0) {
		applicationEventPublisher = arg0;
	}
}
