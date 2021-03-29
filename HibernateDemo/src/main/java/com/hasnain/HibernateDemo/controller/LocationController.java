package com.hasnain.HibernateDemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hasnain.HibernateDemo.models.Location;
import com.hasnain.HibernateDemo.models.User;
import com.hasnain.HibernateDemo.services.LocationService;

@RestController
public class LocationController {

	@Autowired
	private LocationService locationService;

		
	@GetMapping("/locations")
	public List<Location> getAllLocations() {
		return locationService.findAll();
	}
		
	@GetMapping("/locations/{id}")
	public Optional<Location> getLocationById(@PathVariable Integer id) {
		return locationService.findById(id);
	}
	
	@GetMapping("/location/{id}/users")
	public List<User> GetUsersByLocation(@PathVariable Integer id) {
		System.out.println("ouuu");
	    Optional<Location> location = locationService.findById(id);		
	    if(location.isPresent()) {
	    	System.out.println(location.get().getUser());
		return location.get().getUser();
	    }		
	    return null;
	}
	
	@PostMapping("/location/add")
	public void addNewLocation(@RequestBody Location location) {
		locationService.addNewLocation(location);
	}
	
	@PutMapping("/location/{id}/update")
	public void  updateLoction(@RequestBody Location location) {
		locationService.updateLocation(location);
	}
	//one locattion can have  many user 
		//each locaion has some user object(child) so if location  is delete 
		//if we delete location what will happen to those user in that location ,for this we use cascadetype.all
		//what will happen when you delete entity (location) that has child entity(users)
	@DeleteMapping("/location/{id}/delete")
	public void deleteLocation(@PathVariable Integer id) {
		locationService.deleteLocation(id);
	}

}
