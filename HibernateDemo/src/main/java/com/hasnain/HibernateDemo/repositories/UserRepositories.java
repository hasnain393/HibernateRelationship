package com.hasnain.HibernateDemo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hasnain.HibernateDemo.models.Location;
import com.hasnain.HibernateDemo.models.User;

@Repository
public interface UserRepositories extends CrudRepository<User, Integer> {
	
	List<User> findByLocation(Optional<Location> location);

}
