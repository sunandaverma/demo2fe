package mycompro.mycomprobackend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mycompro.mycomprobackend.model.Friends;
import mycompro.mycomprobackend.model.UserDetails;

@Repository
@Transactional
public class FriendsDAOImpl implements FriendsDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	public FriendsDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public List<UserDetails> getSuggestedFriends(String username) {
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from UserDetails where username in (select username from UserDetails where username!=? minus (select fromId from Friends where toId=? and status!='R' union select toId from Friends where fromId=? and status!='R'))";
		SQLQuery<UserDetails> query=session.createSQLQuery(queryString);
		query.addEntity(UserDetails.class);
		query.setString(0, username);
		query.setString(1, username);
		query.setString(2, username);
		return query.list();
	}

	public void addOrUpdateFriend(Friends friend) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(friend);
	}
	
	public List<UserDetails> getFriendRequests(String username) {
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from UserDetails where username in (select fromId from Friends where toId=? and status='P')";
		SQLQuery<UserDetails> query=session.createSQLQuery(queryString);
		query.addEntity(UserDetails.class);
		query.setString(0, username);
		return query.list();
	}

	public List<UserDetails> getSentRequests(String username) {
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from UserDetails where username in (select toId from Friends where fromId=? and status='P')";
		SQLQuery<UserDetails> query=session.createSQLQuery(queryString);
		query.addEntity(UserDetails.class);
		query.setString(0, username);
		return query.list();
	}

	public List<UserDetails> getFriendsList(String username) {
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from UserDetails where username in (select fromId from Friends where toId=? and status='A' union select toId from Friends where fromId=? and status='A')";
		SQLQuery<UserDetails> query=session.createSQLQuery(queryString);
		query.addEntity(UserDetails.class);
		query.setString(0, username);
		query.setString(1, username);
		return query.list();
	}

	public Friends getFriend(String toId,String fromId){
		Session session=sessionFactory.getCurrentSession();
		Query<Friends> query=session.createQuery("from Friends where toId=:toId and fromId=:fromId");
		query.setParameter("toId",toId);
		query.setParameter("fromId",fromId);
		return query.uniqueResult();
	}
}
