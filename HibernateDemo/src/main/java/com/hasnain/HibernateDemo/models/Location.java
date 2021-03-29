package com.hasnain.HibernateDemo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class, 
//		property = "location_id")
public class Location  extends Auditable<String>{
	   @Id
	   //optional if you automatically generate id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer location_id;
	private String name;
	@OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
	private List<User> user;
	public Location() {
		super();
	}
	
	

	public Location(Integer location_id, String name, List<User> user) {
		super();
		this.location_id = location_id;
		this.name = name;
		this.user = user;
	}



	public Integer getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
@JsonManagedReference
	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}






		
}
