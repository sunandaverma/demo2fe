package mycompro.mycomprobackend.dao;

import mycompro.mycomprobackend.model.ProfilePicture;

public interface ProfilePictureDAO {
	public void insertOrUpdateProfilePicture(ProfilePicture profilePicture);
	public ProfilePicture getProfilePicture(String username);
}
