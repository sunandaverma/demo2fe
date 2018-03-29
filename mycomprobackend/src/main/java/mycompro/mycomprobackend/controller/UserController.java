package mycompro.mycomprobackend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mycompro.mycomprobackend.dao.UserDetailsDAO;
import mycompro.mycomprobackend.model.UserDetails;
import mycompro.mycomprobackend.model.Error;

@Controller
public class UserController {
	@Autowired
	private UserDetailsDAO userDetailsDAO;
	@Autowired
	private SockController sockController;
	
	@RequestMapping(value="/registerUser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody UserDetails userDetails){
    	try{
    		System.out.print(userDetails.getUsername());
    	UserDetails duplicateUserDetails=userDetailsDAO.getUserDetails(userDetails.getUsername());
    	if(duplicateUserDetails!=null){
    		System.out.print(2);
    		Error error=new Error(2,"Username already exists!!");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
    	duplicateUserDetails=userDetailsDAO.getUserDetailsByEmail(userDetails.getEmail());
    	if(duplicateUserDetails!=null){
    		System.out.print(3);
    		Error error=new Error(3,"Email address already exists!!");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
    	System.out.print(5);
    	userDetails.setOnline_status(false);
    	userDetailsDAO.insertOrUpdateUserDetails(userDetails);
		return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to register user details!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails,HttpSession session){
    	UserDetails validUserDetails=userDetailsDAO.login(userDetails);
    	if(validUserDetails==null){
    		Error error=new Error(4,"Invalid username or password!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	validUserDetails.setOnline_status(true);
    	userDetailsDAO.insertOrUpdateUserDetails(validUserDetails);
    	session.setAttribute("username", validUserDetails.getUsername());
    	session.setAttribute("role", validUserDetails.getRole());
    	return new ResponseEntity<UserDetails>(validUserDetails,HttpStatus.OK);
    }
 
	@RequestMapping(value="/logout",method=RequestMethod.GET)
    public ResponseEntity<?> logout(HttpSession session){
    	if(session.getAttribute("username")==null){
    		Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	String username=(String)session.getAttribute("username");
    	UserDetails userDetails=userDetailsDAO.getUserDetails(username);
    	userDetails.setOnline_status(false);
    	userDetailsDAO.insertOrUpdateUserDetails(userDetails);
    	sockController.onLogout(username);
    	session.removeAttribute("username");
    	session.invalidate();
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
	
	@RequestMapping(value="/getUser/{username}",method=RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(HttpSession session,@PathVariable String username){
    	if(session.getAttribute("username")==null){
    		Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	UserDetails userDetails=userDetailsDAO.getUserDetails(username);
    	return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
	}
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public ResponseEntity<?> updateUser(HttpSession session,@RequestBody UserDetails userDetails){
		if(session.getAttribute("username")==null){
    		Error error=new Error(5,"Unauthorized User!!");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
		if(!userDetailsDAO.getUserDetails(userDetails.getUsername()).getEmail().equals(userDetails.getEmail()))
			if(userDetailsDAO.getUserDetailsByEmail(userDetails.getEmail())!=null){
				Error error=new Error(3,"Email address already exists!!");
				return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
			}
		try{
    	userDetails.setOnline_status(true);
    	userDetailsDAO.insertOrUpdateUserDetails(userDetails);
		return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
    	}catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to Update user details!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

}

