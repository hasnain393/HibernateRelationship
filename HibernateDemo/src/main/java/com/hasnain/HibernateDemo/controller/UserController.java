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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hasnain.HibernateDemo.models.Location;
import com.hasnain.HibernateDemo.models.Post;
import com.hasnain.HibernateDemo.models.User;
import com.hasnain.HibernateDemo.services.LocationService;
import com.hasnain.HibernateDemo.services.UserService;
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationService locationService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		
	    return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable Integer id) {
	    return userService. getUserById(id);
	}
	
	@GetMapping("/user/{id}/posts")
	public List<Post> getPostByUser(@PathVariable Integer id) {
	   Optional<User> users=  userService.getUserById(id);
	   System.out.println(users);
	   if(users.isPresent()) {
		   List<Post> posts=users.get().getPost();
		   posts.forEach(post->System.out.println(post));
	   return users.get().getPost();
	   }
	   return null;
	}
	
	//below endpoint gives the same result as  /location/1/users
	@GetMapping("/users/location/{id}/users")
	public List<User> getUsersByLocation(@PathVariable Integer id) {
		Optional<Location> location=locationService.findById(id);
		
	    return userService.getUsersByLocation(location);
	}
	
	@PostMapping("/user/addNew")
	public void addUser(@RequestBody User user) {
		userService.insertUser(user);
	}
	
	@PutMapping("/user/{id}/update")
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
	
	//one user can have  many post 
	//each user has some post object(child) so if user is delete 
	//if we delete user what will happen to those post created by that user ,for this we use cascadetype.all
	//what will happen when you delete entity (user) that has child entity(posts)
	@DeleteMapping("/user/{id}/delete")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}
}
