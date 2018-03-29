package mycompro.mycomprobackend.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mycompro.mycomprobackend.dao.BlogPostDAO;
import mycompro.mycomprobackend.dao.UserDetailsDAO;
import mycompro.mycomprobackend.model.Error;
import mycompro.mycomprobackend.model.UserDetails;
import mycompro.mycomprobackend.model.BlogComment;
import mycompro.mycomprobackend.model.BlogPost;

@Controller
public class BlogPostController {
	@Autowired
	BlogPostDAO blogPostDAO;
	@Autowired
	UserDetailsDAO userDetailsDAO;
	private int blog_id;
	@RequestMapping(value="/addBlogPost",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
		UserDetails user=userDetailsDAO.getUserDetails((String)session.getAttribute("username"));
		if(user==null){
			Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
		try{
			if(blogPost.getBlog_id()==0)
				blogPost.setApproved("NA");
			if(session.getAttribute("role").equals("admin"))
				blogPost.setApproved("A");
			blogPost.setPosted_on(new Date());
			blogPost.setPosted_by(user);
			blogPostDAO.insertOrUpdateBlogPost(blogPost);
			blog_id=blogPost.getBlog_id();
			return new ResponseEntity<Void>(HttpStatus.OK);
	    }
		catch(Exception e){
			Error error=new Error(1,"Unable to add BlogPost!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
		}			
	}
	
	@RequestMapping(value="/getAllBlogPost/{approved}",method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlogPost(@PathVariable String approved,HttpSession session){
		/*if(session.getAttribute("username")==null){
    		Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}*/
		List<BlogPost> list=blogPostDAO.getBlogPosts(approved);
		//System.out.println(list.size());
		return new ResponseEntity<List<BlogPost>>(list,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/getAllBlogPostByUser/{username}",method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlogPostByUser(@PathVariable String username){
		UserDetails user=userDetailsDAO.getUserDetails(username);
		List<BlogPost> list=blogPostDAO.getBlogPostsByUser(user);
		return new ResponseEntity<List<BlogPost>>(list,HttpStatus.OK);	
	}

	@RequestMapping(value="/getBlogPostById/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogPostById(@PathVariable int id){
		BlogPost blogPost=blogPostDAO.getBlogPostById(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/approveBlogPost/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> approveBlogPost(@PathVariable int id,HttpSession session){
		try{
			if(session.getAttribute("username")==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			else if(!session.getAttribute("role").equals("admin")){
				Error error=new Error(6,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	
			}
			BlogPost blogPost=blogPostDAO.getBlogPostById(id);
			blogPost.setApproved("A");
			blogPostDAO.insertOrUpdateBlogPost(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(1,"Unable to approve BlogPost!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
		}			
	}
	
	@RequestMapping(value="/rejectBlogPost/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> rejectBlogPost(@PathVariable int id,HttpSession session){
		try{
			if(session.getAttribute("username")==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			else if(!session.getAttribute("role").equals("admin")){
				Error error=new Error(6,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);	
			}
			BlogPost blogPost=blogPostDAO.getBlogPostById(id);
			blogPost.setApproved("R");
			blogPostDAO.insertOrUpdateBlogPost(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(1,"Unable to Reject BlogPost!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
		}			
	}

	@RequestMapping(value="/deleteBlogPost/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteBlogPost(@PathVariable int id,HttpSession session){
		try{
			if(session.getAttribute("username")==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			BlogPost blogPost=blogPostDAO.getBlogPostById(id);
			if(blogPost.getPosted_by().getUsername().equals(session.getAttribute("username"))){
			blogPostDAO.deleteBlogPost(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
			}
			else{
				Error error=new Error(6,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to delete BlogPost!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@RequestMapping(value="/addBlogComment",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		String username=(String)session.getAttribute("username");	
		if(username==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			try{
				UserDetails user=userDetailsDAO.getUserDetails(username);
				blogComment.setCommentedBy(user);
				blogComment.setCommentedOn(new Date());
				blogPostDAO.addBlogComment(blogComment);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			catch(Exception e){	
				Error error=new Error(1,"Unable to add BlogComment!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
		}

	@RequestMapping(value="/getAllBlogComment/{blog_id}",method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlogComment(@PathVariable int blog_id){
	List<BlogComment> list=blogPostDAO.getAllBlogComment(blog_id);
	return new ResponseEntity<List<BlogComment>>(list,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/addBlogPicture",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogImage(@RequestParam("image") MultipartFile image,HttpSession session)
	{	String imgpath=session.getServletContext().getRealPath("/resources/images/");
		String file_info=imgpath+blog_id+".jpg";
		System.out.println(file_info);
		File f=new File(file_info);
		if(!image.isEmpty()){
			try{
			byte buff[]=image.getBytes();
			BufferedOutputStream bs=new BufferedOutputStream(new FileOutputStream(f));
			bs.write(buff);
			bs.close();
			return new ResponseEntity<Void>(HttpStatus.OK);
	    	}
			catch(Exception e){
	    		System.out.print(e);
	    		Error error=new Error(1,"Unable to upload profile picture!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
			}
		else{
    		Error error=new Error(1,"Unable to upload profile picture!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
	


