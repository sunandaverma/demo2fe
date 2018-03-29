package mycompro.mycomprobackend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mycompro.mycomprobackend.model.ApplyForJob;
import mycompro.mycomprobackend.model.Job;

@Repository
@Transactional
public class JobDAOImpl implements JobDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	

	public void insertOrUpdateJob(Job job) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(job);	
	}

	public void deleteJob(Job job) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(job);	
	}

	public List<Job> getJob() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Job> list=session.createQuery("from Job").list();
		return list;
	}

	public Job getJobById(int id) {
		Session session=sessionFactory.getCurrentSession();
		Job job=session.get(Job.class,id);
		return job;
	}
	
	public void applyForJob(ApplyForJob applyForJob) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(applyForJob);	
	}
	
	public List<ApplyForJob> getAllAppliedUser(int jobId) {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<ApplyForJob> query=session.createQuery("from ApplyForJob where applied_for=:id");
		query.setParameter("id", jobId);
		List<ApplyForJob> list=query.list();
		return list;
	}
	
	public boolean checkIfApplied(int jobId,String username){
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<ApplyForJob> query=session.createQuery("from ApplyForJob where applied_for=:id and applied_by.username=:username");
		query.setParameter("id", jobId);
		query.setParameter("username", username);
		if(query.uniqueResult()==null)
			return false;
		return true;
	}
}
