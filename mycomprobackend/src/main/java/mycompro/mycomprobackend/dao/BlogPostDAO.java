package mycompro.mycomprobackend.dao;

import java.util.List;

import mycompro.mycomprobackend.model.BlogComment;
import mycompro.mycomprobackend.model.BlogPost;
import mycompro.mycomprobackend.model.UserDetails;

public interface BlogPostDAO {
	void insertOrUpdateBlogPost(BlogPost blogPost);
	List<BlogPost> getBlogPosts(String approved);
	BlogPost getBlogPostById(int id);
	List<BlogPost> getBlogPostsByUser(UserDetails user);
	void deleteBlogPost(BlogPost blogPost);
	void addBlogComment(BlogComment blogComment);
	List<BlogComment> getAllBlogComment(int blog_id);
}
