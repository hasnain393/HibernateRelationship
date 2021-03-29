package com.hasnain.HibernateDemo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class, 
//		property = "user_id")
public class User extends Auditable<String> {
	   @Id
	   //optional if you automatically generate id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer user_id;
	private String firstname;
	private String lastname;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Post> post	;
	@ManyToOne
	//location_id_se_join_kar
	//instead of using field create by hibernate ,we will create our own field 
	//and take control and insert,updatable mean will not work with hibernate created field
	//for that in join column rename the field from location_id to locationid
	@JoinColumn(name="locationid", insertable = false, updatable=false)
	private Location location;
	
	//we are creating our own column
	private Integer locationid;
	
	

	public Integer getLocationid() {
		return locationid;
	}


	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}






	private String email;
	
	
	public User() {
		super();
	}


	public User(Integer user_id, String firstname, String lastname, List<Post> post, Location location, String email) {
		super();
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.post = post;
		this.location = location;
		this.email = email;
	}

	public Integer getUser_id() {
		return user_id;
	}






	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}






	public String getFirstname() {
		return firstname;
	}






	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}






	public String getLastname() {
		return lastname;
	}






	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




@JsonManagedReference
	public List<Post> getPost() {
		return post;
	}






	public void setPost(List<Post> post) {
		this.post = post;
	}





	@JsonBackReference
	public Location getLocation() {
		return location;
	}






	public void setLocation(Location location) {
		this.location = location;
	}






	public String getEmail() {
		return email;
	}






	public void setEmail(String email) {
		this.email = email;
	}


	






	





}
