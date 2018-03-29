package mycompro.mycomprobackend.dao;

import java.util.List;

import mycompro.mycomprobackend.model.Friends;
import mycompro.mycomprobackend.model.UserDetails;

public interface FriendsDAO {
	List<UserDetails> getSuggestedFriends(String username);
	void addOrUpdateFriend(Friends friend);
	public List<UserDetails> getFriendRequests(String username);
	public List<UserDetails> getFriendsList(String username);
	public Friends getFriend(String toId,String fromId);
	public List<UserDetails> getSentRequests(String username);
}
