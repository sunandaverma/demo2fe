package mycompro.mycomprobackend.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mycompro.mycomprobackend.model.ProfilePicture;
@Repository
@Transactional
public class ProfilePictureDAOImpl implements ProfilePictureDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	public ProfilePictureDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public void insertOrUpdateProfilePicture(ProfilePicture profilePicture) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(profilePicture);	
	}

	public ProfilePicture getProfilePicture(String username) {
		Session session=sessionFactory.getCurrentSession();
		return session.get(ProfilePicture.class,username);
	}

}
