package mycompro.mycomprobackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ApplyForJob {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int applied_for;
	@ManyToOne
	private UserDetails applied_by;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplied_for() {
		return applied_for;
	}
	public void setApplied_for(int applied_for) {
		this.applied_for = applied_for;
	}
	public UserDetails getApplied_by() {
		return applied_by;
	}
	public void setApplied_by(UserDetails applied_by) {
		this.applied_by = applied_by;
	}
	
}
