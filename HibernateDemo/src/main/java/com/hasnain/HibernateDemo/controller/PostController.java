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

import com.hasnain.HibernateDemo.models.Post;
import com.hasnain.HibernateDemo.models.User;
import com.hasnain.HibernateDemo.services.PostService;
import com.hasnain.HibernateDemo.services.UserService;
@RestController
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	@GetMapping("/posts")
	public List<Post> getAllPosts() {
	    return postService.findAll();
	}

	@GetMapping("/posts/{id}")
	public Optional<Post> getPostById(@PathVariable Integer id) {
	    return postService.findById(id);
	}
	
	//gives the same result as /user/2/posts
	@GetMapping("/posts/user/{id}/posts")
	public List<Post>  getPostByUser(@PathVariable Integer id){
		Optional<User> user=userService.getUserById(id);
		return postService.getPostByUser(user);
	}
	

@PostMapping("/post/addNew")
public void addPost(@RequestBody Post post) {
	postService.insertPost(post);
}

	@PutMapping("/post/{id}/update")
	public void updatePost(@RequestBody Post post) {
		postService.updatePost(post);
	}
	
	@DeleteMapping("/post/{id}/delete")
	public void deletePost(@PathVariable Integer id) {
		postService.deletePostById(id);
	}

}
