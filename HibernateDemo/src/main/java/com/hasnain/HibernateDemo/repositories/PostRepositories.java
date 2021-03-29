package com.hasnain.HibernateDemo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hasnain.HibernateDemo.models.Post;
import com.hasnain.HibernateDemo.models.User;

@Repository
public interface PostRepositories  extends CrudRepository<Post, Integer> {
	
	List<Post> findByUser(Optional<User> user);
	

}
