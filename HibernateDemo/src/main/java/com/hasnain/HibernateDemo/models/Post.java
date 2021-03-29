package com.hasnain.HibernateDemo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class, 
//		property = "post_id")
public class Post extends Auditable<String> {
	   @Id
	   //optional if you automatically generate id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  post_id;
	private LocalDateTime post_date;
	@ManyToOne
	//user_id_se_join_kar
	//instead of using field create by hibernate ,we will create our own field 
	//and take control and insert,updatable mean will not work with hibernate created field
	//for that in join column rename the field from user_id to userid
	//step1
	@JoinColumn(name="userid", insertable=false, updatable=false)
	private User  user;
	
	//we are creating our own column
	//step2
	private Integer userid;
	
	//step3
	public Integer getUserid() {
		return userid;
	}
	
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	private String details;
	
	public Post() {
		super();
	}



	public Post(Integer post_id, LocalDateTime post_date, User user, String details) {
		super();
		this.post_id = post_id;
		this.post_date = post_date;
		this.user = user;
		this.details = details;
	}



	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public LocalDateTime getPost_date() {
		return post_date;
	}

	public void setPost_date(LocalDateTime post_date) {
		this.post_date = post_date;
	}
	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}






		

}
