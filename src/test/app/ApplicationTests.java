package test.app;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;

import dev.div0.users.ApplyOpponentsEachOther;
import dev.div0.users.User;
import dev.div0.users.collection.Users;

public class ApplicationTests {
	
	private Users users = new Users();
	private User user1 = new User("user1_stream", null);
	private User user2 = new User("user2_stream", null);
	
	private ApplicationEventPublisher publisher;
	
	@Test
	public void startTest(){
		boolean user1Added = users.add(user1);
		assertEquals(user1Added, true);
		
		assertEquals(user1.isIdle(), true);
		assertEquals(users.size(), 1);
		
		user1.startSearch();
		assertEquals(user1.isSearching(), true);
		assertEquals(user1.hasOpponent(), false);
		
		boolean user2Added = users.add(user2);
		assertEquals(users.size(), 2);	
		assertEquals(user2Added, true);	
		assertEquals(user2.hasOpponent(), false);
		assertEquals(user2.isSearching(), false);
		
		// apply opponents
		new ApplyOpponentsEachOther(user1, user2);
		
		assertEquals(user1.hasOpponent(), true);
		assertEquals(user2.hasOpponent(), true);
		
		assertEquals(user1.isSearching(), false);
		assertEquals(user2.isSearching(), false);
		
		assertEquals(user1.isChatting(), true);
		assertEquals(user2.isChatting(), true);
		
		assertEquals(user1.isItOpponent(user2), true);
		assertEquals(user2.isItOpponent(user1), true);
		
		// drop 2nd user
		user1.onOpponentDrop();
		assertEquals(user1.isSearching(), true);
		
		new ApplyOpponentsEachOther(user1, user2);
		assertEquals(user1.hasOpponent(), true);
		assertEquals(user2.hasOpponent(), true);
		
		assertEquals(user1.isSearching(), false);
		assertEquals(user2.isSearching(), false);
		
		assertEquals(user1.isChatting(), true);
		assertEquals(user2.isChatting(), true);
		
		assertEquals(user1.isItOpponent(user2), true);
		assertEquals(user2.isItOpponent(user1), true);
	}
}
