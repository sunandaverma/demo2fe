package mycompro.mycomprobackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BlogComment {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private int id;
@ManyToOne
@JoinColumn(name="blog_id")
private BlogPost blogPost;
@ManyToOne
@JoinColumn(name="username")
private UserDetails commentedBy;
private Date commentedOn;
private String commentText;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public BlogPost getBlogPost() {
	return blogPost;
}
public void setBlogPost(BlogPost blogPost) {
	this.blogPost = blogPost;
}
public UserDetails getCommentedBy() {
	return commentedBy;
}
public void setCommentedBy(UserDetails commentedBy) {
	this.commentedBy = commentedBy;
}
public Date getCommentedOn() {
	return commentedOn;
}
public void setCommentedOn(Date commentedOn) {
	this.commentedOn = commentedOn;
}
public String getCommentText() {
	return commentText;
}
public void setCommentText(String commentText) {
	this.commentText = commentText;
}

}
