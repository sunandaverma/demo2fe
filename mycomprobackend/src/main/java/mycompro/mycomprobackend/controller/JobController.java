package mycompro.mycomprobackend.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mycompro.mycomprobackend.dao.JobDAO;
import mycompro.mycomprobackend.dao.UserDetailsDAO;
import mycompro.mycomprobackend.model.ApplyForJob;
import mycompro.mycomprobackend.model.Error;
import mycompro.mycomprobackend.model.Job;

@Controller
public class JobController {
	@Autowired
	private JobDAO jobDAO;
	@Autowired
	private UserDetailsDAO userDetailsDAO;
	
	@RequestMapping(value="/addJob",method=RequestMethod.POST)
	public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session){
		try{
			if(session.getAttribute("username")==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			else if(!session.getAttribute("role").equals("admin")){
				Error error=new Error(6,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	
			}
			job.setPosted_on(new Date());
			jobDAO.insertOrUpdateJob(job);
			return new ResponseEntity<Void>(HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to add Job details!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@RequestMapping(value="/deleteJob/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteJob(@PathVariable int id,HttpSession session){
		try{
			if(session.getAttribute("username")==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			else if(!session.getAttribute("role").equals("admin")){
				Error error=new Error(6,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	
			}
			jobDAO.deleteJob(jobDAO.getJobById(id));
			return new ResponseEntity<Void>(HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to delete Job details!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@RequestMapping(value="/getJobById/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getJobById(@PathVariable int id){
		Job job=jobDAO.getJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(){
		List<Job> list=jobDAO.getJob();
		return new ResponseEntity<List<Job>>(list,HttpStatus.OK);	
	}

	@RequestMapping(value="/applyForJob/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<?> applyForJob(@PathVariable int jobId ,HttpSession session){
		try{
			String username=(String)session.getAttribute("username");
			if(username==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			if(jobDAO.checkIfApplied(jobId, username)){
	    		Error error=new Error(8,"Not allowed to apply again!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.ALREADY_REPORTED);
	    	}
			ApplyForJob applyForJob=new ApplyForJob();
			applyForJob.setApplied_for(jobId);
			applyForJob.setApplied_by(userDetailsDAO.getUserDetails(username));
			jobDAO.applyForJob(applyForJob);
			return new ResponseEntity<Void>(HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to apply for Job!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@RequestMapping(value="/getAllAppliedUser/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<?> getAllAppliedUser(@PathVariable int jobId ,HttpSession session){
		if(session.getAttribute("username")==null){
    		Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
		else if(!session.getAttribute("role").equals("admin")){
			Error error=new Error(6,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	
		}
		List<ApplyForJob> list=jobDAO.getAllAppliedUser(jobId);
		return new ResponseEntity<List<ApplyForJob>>(list,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/checkIfApplied/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<?> checkIfApplied(@PathVariable int jobId ,HttpSession session){
		try{
			String username=(String)session.getAttribute("username");
			if(username==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			return new ResponseEntity<Boolean>(jobDAO.checkIfApplied(jobId, username),HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to check!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

}
