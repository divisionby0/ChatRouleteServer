package dev.div0.users.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.red5.server.api.IConnection;

import dev.div0.BaseLogger;
import dev.div0.users.User;

public class Users extends BaseLogger{
	private List<User> collection;
	
	private Random randomGenerator;
	
	public Users(){
		collection = new ArrayList<User>();
		randomGenerator = new Random();
	}
	
	// TODO Если 1 элемент то ничего, если более одного то, если совпадает с exceptionUser - еще раз запустить поиск БЕЗ возврата результата
	public User getRandomUser(User exceptionUser){
		if(collection.size()<2){
			return null;
		}
		
		User randomUser = getRandomListItem();
		//log("randomUser = "+randomUser);
		//log("randomUser stream = "+randomUser.getStreamId()+"  exceptionUser stream="+exceptionUser.getStreamId());
		if(randomUser.getStreamId().equals(exceptionUser.getStreamId())){
			//log("is Exception user - getting random again");
			getRandomUser(exceptionUser);
			//randomUser = null;
		}
		
		return randomUser;
	}
	
	public boolean add(User user){
		boolean retValue = false;
		
		// by connection
		//IConnection possibleDuplicatedUserConnection = user.getConnection();
		//int possibleDuplicatedUserIndex = getUserIndexByConnection(possibleDuplicatedUserConnection);
		
		// by stream
		int possibleDuplicatedUserIndex = getUserIndexByStreamId(user.getStreamId());
		
		if(possibleDuplicatedUserIndex==-1){
			collection.add(user);
			retValue = true;
		}
		return retValue;
	}
	
	private int getUserIndexByConnection(IConnection connection){
		int userIndex = -1;
		int i;
		for(i=0; i<collection.size(); i++){
			User user = collection.get(i);
			boolean isSelfConnection = user.isSelfConnection(connection);
			
			if(isSelfConnection == true){
				userIndex = i;
				break;
			}
		}
		return userIndex;
	}
	private int getUserIndexByStreamId(String streamId){
		//log("getUserIndexByStreamId "+streamId);
		int userIndex = -1;
		int i;
		for(i=0; i<collection.size(); i++){
			User user = collection.get(i);
			boolean isSelfStream = user.isSelfStreamId(streamId);
			
			if(isSelfStream == true){
				userIndex = i;
				break;
			}
		}
		return userIndex;
	}
	public void remove(User user){
		collection.remove(user);
	}
	public int size(){
		return collection.size();
	}
	public boolean isEmpty(){
		return collection.isEmpty();
	}
	
	public Iterator<User> getIterator(){
		return collection.iterator();
	}
	
	private User getRandomListItem(){
		int index = randomGenerator.nextInt(collection.size());
		//log("random index = "+index);
		return collection.get(index);
	}
}
