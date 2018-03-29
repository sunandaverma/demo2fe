package mycompro.mycomprobackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class BlogPost {
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int blog_id;
	@NotNull
	private String blog_title;
	@Lob
	@NotNull
	private String blog_description;
	private Date posted_on;
	@ManyToOne
	private UserDetails posted_by;
	private String approved;
	
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public int getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}
	public String getBlog_title() {
		return blog_title;
	}
	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}
	public String getBlog_description() {
		return blog_description;
	}
	public void setBlog_description(String blog_description) {
		this.blog_description = blog_description;
}	
	public Date getPosted_on() {
		return posted_on;
	}
	public void setPosted_on(Date posted_on) {
		this.posted_on = posted_on;
	}
	public UserDetails getPosted_by() {
		return posted_by;
	}
	public void setPosted_by(UserDetails posted_by) {
		this.posted_by = posted_by;
	}
	
	
}
