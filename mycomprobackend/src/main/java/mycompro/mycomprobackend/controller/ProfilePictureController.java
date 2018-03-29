package mycompro.mycomprobackend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import mycompro.mycomprobackend.dao.ProfilePictureDAO;
import mycompro.mycomprobackend.model.Error;
import mycompro.mycomprobackend.model.ProfilePicture;

@Controller
public class ProfilePictureController {
	@Autowired
	ProfilePictureDAO profilePictureDAO;
	
	@RequestMapping(value="/addProfilePicture",method=RequestMethod.POST)
	public ResponseEntity<?> addProfilePicture(@RequestParam  MultipartFile image,HttpSession session){
		try{
			String username=(String)session.getAttribute("username");
			if(username==null){
	    		Error error=new Error(5,"Unauthorized User!!");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
			ProfilePicture profilePicture=new ProfilePicture();
			profilePicture.setUsername(username);
			profilePicture.setImage(image.getBytes());
			profilePictureDAO.insertOrUpdateProfilePicture(profilePicture);
			return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
    	}
		catch(Exception e){
    		System.out.print(e);
    		Error error=new Error(1,"Unable to upload profile picture!!");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@RequestMapping(value="/getProfilePicture/{username}",method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePicture(@PathVariable String username){
			ProfilePicture profilePicture=profilePictureDAO.getProfilePicture(username);
			if(profilePicture==null)
				return null;
			return profilePicture.getImage();
    	
	}
	
}
