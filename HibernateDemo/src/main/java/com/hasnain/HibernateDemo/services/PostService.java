package com.hasnain.HibernateDemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hasnain.HibernateDemo.models.Post;
import com.hasnain.HibernateDemo.models.User;
import com.hasnain.HibernateDemo.repositories.PostRepositories;
@Service
public class PostService {
	
	@Autowired
	private PostRepositories postRepository;

public List<Post> findAll() {
	return (List<Post>) postRepository.findAll();
}

public Optional<Post> findById(Integer id) {
	return postRepository.findById(id);
}

public List<Post> getPostByUser(Optional<User> user){
	return postRepository.findByUser(user);
}

public void insertPost(Post post) {
	postRepository.save(post);
	
}

public void updatePost(Post post) {
postRepository.save(post);
	
}

public void deletePostById(Integer id) {
	postRepository.deleteById(id);
	
}



}
