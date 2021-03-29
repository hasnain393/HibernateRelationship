package com.hasnain.HibernateDemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hasnain.HibernateDemo.models.Location;
import com.hasnain.HibernateDemo.repositories.LocationRepositories;
@Service
public class LocationService {
	@Autowired
	private LocationRepositories locationRepository; 

	public List<Location> findAll() {
		return (List<Location>) locationRepository.findAll();
	}

	public Optional<Location> findById(Integer id) {
		return locationRepository.findById(id);
	}
	
	public void addNewLocation(Location location) {
		locationRepository.save(location);
	}

	public void updateLocation(Location location) {
		locationRepository.save(location);
		
	}

	public void deleteLocation(Integer id) {
		locationRepository.deleteById(id);
		
	}

}
