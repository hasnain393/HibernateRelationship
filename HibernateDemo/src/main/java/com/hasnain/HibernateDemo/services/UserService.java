package com.hasnain.HibernateDemo.services;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hasnain.HibernateDemo.models.Location;
import com.hasnain.HibernateDemo.models.User;
import com.hasnain.HibernateDemo.repositories.UserRepositories;
@Service
public class UserService {

	@Autowired
	private UserRepositories userRepository;


	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}


	public Optional<User> getUserById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
		
	}
	public List<User> getUsersByLocation(Optional<Location> location) {
	    return userRepository.findByLocation(location);
	}
	
	public void insertUser(User user) {
		userRepository.save(user);
	}


	public void updateUser(User user) {
		userRepository.save(user);
		
	}


	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	
}
