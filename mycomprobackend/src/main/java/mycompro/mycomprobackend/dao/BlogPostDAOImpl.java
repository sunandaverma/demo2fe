package mycompro.mycomprobackend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mycompro.mycomprobackend.model.BlogComment;
import mycompro.mycomprobackend.model.BlogPost;
import mycompro.mycomprobackend.model.UserDetails;
@Repository
@Transactional
public class BlogPostDAOImpl implements BlogPostDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	public BlogPostDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	public void insertOrUpdateBlogPost(BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(blogPost);	
	}

	public List<BlogPost> getBlogPosts(String approved) {
		Session session=sessionFactory.getCurrentSession();
		Query<BlogPost> query=session.createQuery("from BlogPost where approved=:approved");
		query.setParameter("approved",approved);
		return query.list();
	}
	public BlogPost getBlogPostById(int id) {
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=session.get(BlogPost.class,id);
		return blogPost;
	}
	public List<BlogPost> getBlogPostsByUser(UserDetails user) {
		Session session=sessionFactory.getCurrentSession();
		Query<BlogPost> query=session.createQuery("from BlogPost where posted_by=:user");
		query.setParameter("user",user);
		return query.list();
	}
	public void deleteBlogPost(BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(blogPost);	
	}
	public void addBlogComment(BlogComment blogComment) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogComment);		
	}
	public List<BlogComment> getAllBlogComment(int blog_id) {
		Session session=sessionFactory.getCurrentSession();
		Query<BlogComment> query=session.createQuery("from BlogComment where blogPost.blog_id=:id");
		query.setParameter("id",blog_id);
		return query.list();
	}

}
