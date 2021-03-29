# HibernateRelationship
### Part 1 – Introduction

* In this comprehensive tutorial, we’ll build an application made up of three entities: Location, User and Post.
How they relate is that: Location contains users and User has posts. This is illustrated in the figure below
![Relationship-Diagram](https://user-images.githubusercontent.com/56108097/112823558-11f2e000-90a7-11eb-8619-e3031fe8805d.jpg)
### Part 2 – Setting Up the Application

* In this part, we would set up a Spring Boot Starter application. Follow the steps below:

 Step 1: Create a Spring application (you should be able to do this by now). Add the starter web dependency. Also add the starter-jpa dependency.
 
 Step 2: Create three classes corresponding to Post, User and Location. Put them in the models package   
 
 Step 3: Also create all the repositories in the repositories package
 
 Step 4: Create the services in the Services package
 
 Step 5: Finally, create the RestControllers in the controllers package
  
### Part 3 – @ManyToOne Relationship

* We would now configure Many-to-one relationship between the User and the Location entities. Similarly, we do the same between the Post and User entities.

Follow the steps below:

Step 1: Annotate the Location field of the User entity with @ManyToOne annotation.

Step 2: Annotate the User field of the Post entity with @ManyToOne annotation

When you use these annotation, the entity decides to handle the relationship. 
In the first case, the User entity does this by creating a column called location_id in the User table.
Similarly, the Post entity does this by creating a column called user_id

### Part 4 – Configure the H2 Database

* H2 Database is an in-memory database that is normally used during development. Now we configure the H2 database following the steps below:

Step 1: Open the application.properties file. It is in the src/main/resources folder

Step 2: Add the following code in the application.properties file
```
spring.h2.console.enabled=true
spring.datasource.platform=h2
spring.datasource.url=jdbc:h2:mem:socialdb
spring.datasource.driverClassName=org.h2.Driver
```
Step 3: Run the the application and access the H2 Console

### Part 5 – Database Initialization (data.sql)

* We would write a script to insert some initial data into the database. Follow the steps below:

Step 1: Create a file inside the src/main/resources folder. Name it data.sql

Step 2: Open the file and add the following code
```
insert into Location(id, name) values(1, 'Budapest, Hungary');
insert into Location(id, name) values(2, 'Owerri, Nigeria');
insert into Location(id, name) values(3, 'Califonia, USA');

insert into User(id, firstname, lastname, email, location_id) values(1, 'Kindson', 'Munonye', 'kany@gmail.com', 1);
insert into User(id, firstname, lastname, email, location_id) values(2, 'Jeffrey', 'Yuba', 'yuba@gmail.com', 2);
insert into User(id, firstname, lastname, email, location_id) values(3, 'Solace', 'Okeke', 'solace@gmail.com', 3);

insert into Post(id, post_date, details, user_id) values(1, CURRENT_TIMESTAMP(), 'Very good post', 1);
insert into Post(id, post_date, details, user_id) values(2, CURRENT_TIMESTAMP(), 'A rainy day', 2);
insert into Post(id, post_date, details, user_id) values(3, CURRENT_TIMESTAMP(), 'nice tutorials', 3);
```
Step 3: Run the application. Access h2-console and verify that the data is inserted. The H2-Console window is shown below

### Part 6 – @OneToMany Relationship

* This relationship allows us to get list of child entities under a parent entity. 
For examples, we could get the list of posts for a user. Or we could get list of users in a location. Follow the steps below:

Step 1:  In the Location class, add a private field of List<User> type. (This means list of all users under that location)

Step 2:  Annotate this field with the @OneToMany annotation

Step 3:  In the User class, add a private field of List<Post> type. (This means list of all posts for that user)

Step 4: Annotate this field with the @OneToMany annotation

Step 5: Run the application and access the H2 Console

Now, you will notice in the H2 console that there are additional tables that have been created.
What happens here is this:

* for the User-Location relationship, the Location entity decides to handle the relationship mapping by creating an additional table called LOCATION_USERS.
* Again, for the Post-User relationship, the User entify decides the handle the relationship by creating an additional table called POST_USERS.
* But remember that the relationship mapping has already been handled using by the @ManyToOne using the location_id and the user_id columns.
**This duplicate mapping can be resolved using the mappedBy attibute**

### Part 7 – The mappedBy Attribute

The mappedBy is an attribute of the @OneToMany relationship. You use the mappedBy to tell the @OneToMany annotation that the relationship has already been handled using a foreign key in the corresponding entity. In this way, an additional table is not created.

To do this:

Step 1: In the @OneToMany annotation in the Location entity, add mappedBy=”location”

Step 2: In the @OneToMany annotation in the User entity, add mappedBy=”user”

Step 3: Launch the application, access H2-Console and check that there are no more duplicate tables;

### Part 8 – @JoinColumn Annotation

* The @JoinColumn annotation is used to specify the actual column(in the table) used for the mapping. This is done at the entity that owns the mapping.

* In the case of User-Post relationship, the Post owns the relationship and automatically creates the user_id column. This is the JoinColumn.

* Similarly, the  Location-User relationship, the Location owns the relationship and provides the location_id column.

Follow the steps below to add the @JoinColumn annotation

Step 1: In the Location field of the User entity, add @JoinColumn(name=”location_id”)

Step 2: In the User field of the Post entity, add @JoinColumn(name=”user_id”)

Step 3: Relaunch and check the tables in the h2 console (nothing changes though!)

### Part 9 – Setup findAll() and findById() for all Entities
* Write the methods to in the RestController files for Post, User and Location.
* The three RestController and Services files would contain the following:

* UserController

```
@Autowired
private UserService userService;

@GetMapping("/users")
public List<User> getAllUsers() {
    return userService.findAll();
}
@GetMapping("/users/{id}")
public Optional<User> getUserById(@PathVariable Integer id) {
    return userService.findById(id);
}
```

* UserService

```
@Autowired
private UserRepository userRepository;

public List<User> getAllUsers() {
	return (List<User>) userRepository.findAll();
}

public Optional<User> getUserById(Integer id) {
	return userRepository.findById(id);
}
```
Step 1: Launch the application and visit http://localhost/users to see the list of users.

* LocationController

```
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
```

* LocationService

```
@Autowired
private LocationRepository locationRepository; 

public List<Location> findAll() {
	return (List<Location>) locationRepository.findAll();
}

public Optional<Location> findById(Integer id) {
	return locationRepository.findById(id);
}
```

* PostController
```
@Autowired
private PostService postService;

@GetMapping("/posts")
public List<Post> getAllPosts() {
    return postService.findAll();
}

@GetMapping("/posts/{id}")
public Optional<Post> getPostById(@PathVariable Integer id) {
    return postService.findById(id);
}
```

* PostService
```
private PostRepository postRepository;

public List<Post> findAll() {
	return (List<Post>) postRepository.findAll();
}

public Optional<Post> findById(Integer id) {
	return postRepository.findById(id);
}
```

### Part 10 – Infinite Recursion (StackOverflow Error)

* We would now solve a problem that always arises when you have mapping between entities. Let’s see what the problem is first!

Step 1: In the User entity, right-click and generate Getter and Setters for the posts field

Step 2: In the Location entity, right-click and generate Getter and Setter for the users field

Step 3: Relaunch the application. Try to access the list of users via http://localhost:8080/users. You will notice, that it produces an infinite number of items. In you look at the console, you will now see this error.

Let’s solve it!

### Part 11- Solving Infinite Recursion using @JsonManagedReference and @JsonBackReference

So we need a way to break the infinite recursion loop. The @JsonManagedReference is used on the OneToMany side while the @JsonBackReference is used at the @ManyToOne side.

@JsonManagedReference is the forward part of the mapping/reference and the data gets serialized normally.

@JsonBackReference is is the backward side of the mapping and the data  does not get serialized

Follow the steps below:

Step 1: Add @JsonManagedReference to the getPosts method in the User entity

Step 2: Add @JsonBackReference to the getUser method of the Post entity

Step 3: Add @JsonManagedReference to the getUsers method of the Location entity

Step 4: Add @JsonBackReference to the getLocation method of the User entity

Step 5: Relaunch the application, try to access list of Users, Locations, or Posts. Notice that the problem is solved

Other methods of handling this problem include use of @JsonIdentityInfo and JsonIgnore annotations. These are discussed below

### Part 12 – @JsonIdentifyInfo and @JsonIgnore

* Now, the @JsonIgnore is an alternative  for the @JsonBackReference. So you can used @JsonIgnore in place of @JsonBackReference

@JsonIdentityInfo can be used in place of the both @JsonManagedReference and @JsonBackReference.
However, the @JsonIdentityInfo annotation is added to the class and NOT to the methods. Also, the @JsonIdentityInfo requires some attributes as shown in the code below:
```
@JsonIdentityInfo(
	generator = ObjectIdGenerators.PropertyGenerator.class, 
	property = "id")
  ```

### Part 13 – Get List of Users by Location

* Now we would try to get the list of users in a location. So we would write a method in the LocationController that takes the location_id and returns the users in that location.

This method is written as follows in the LocationController.

Step 1: Copy the code below and paste inside the LocationController
```
@GetMapping("/location/{id}/users")
public List<User> GetUsersByLocation(@PathVariable Integer id) {
    Optional<Location> location = locationService.findById(id);		
    if(location.isPresent()) {
	return location.get().getUsers();
    }		
    return null;
}
```
Listing 1: GetUsersByLocation() method

In Listing 1, we first get the Optional<Location> object using findById. Then if the location exists, then we get the actual Location object. Finally we get the list of users in that location using the getUser() member function of the the Location class.

Also observe, the url pattern “/location/{id}/users”

Step 2: Relaunch the application and try to access /location/1/users. List of users is returned for that location.

### Part 14 – Get List of Posts by User

* We would do the same thing with the List of Posts. This is similar to what we did in part 14. I would recommend you try to do this yourself, before you use the code below

Step 1: Paste the code below in the UserController file
```
@GetMapping("/user/{id}/posts")
public List<Post> getPostsByUser(@PathVariable Integer id) {
    Optional<User> user = userService.findById(id);
    if(user.isPresent()) {
	return user.get().getPosts();
    }
    return null;
}
```

Step 2: Relaunch and test the application

### Part 15 – List of Users By Location from UserController by Extending JPARepository

* Now you can skip this part if you want. It’s just a different way of achieving the same thing.

Here, we would receive the Location_id as a PathVariable and use it to filter the list of users. We’ll achieve this by extending the UserRepository.

Step 1: Write the code below in the UserRepository.
```
List<User> findByLocationId(Integer id);
 ```

Step 2: Write the following method in the UserService
```
public List<User> getUsersByLocation(Integer id) {
    return userRepository.findByLocationId(id);
}
 ```

Step 3: Write the following method in the UserController
```
@GetMapping("/users/location/{id}/users")
public List<User> getUsersByLocation(@PathVariable Integer id) {
    return userService.getUsersByLocation(id);
}
 ```

Notice that the url pattern is “/users/location/{id}/users”

Step 4: Launch the application and test it by accessing http://localhost/users/location/1/users

### Part 16 – Get List of Posts by User from PostController  by Extending JPARepository

* Now you can skip this part if you want. It’s just a different way of achieving the same thing.

We’ll do something similar to what we did in Part 15, but this time with the Post.

Step 1: Copy and paste the code below in the PostRepository
```
List<Post> findByUserId(Integer id);
```
 

Step 2: Paste the code below in the PostService
```
public List<Post> findByLocationId(Integer id) {
    return postRepository.findByUserId(id);
}
 ```

Step 3: Paste the code below in the PostController
```
@GetMapping("/posts/user/{id}/posts")
public List<Post> getPostsByUser(@PathVariable Integer id) {
    return postService.findByLocationId(id);
}
 ```

Step 4: Relaunch and test the application

### Part 17 – Setup Advanced REST Client

* Before you can make  a POST request to the application, you need to have a REST Client (you can’t use the browser!).  For me I use Advanced REST Client. Postman REST Client is ok too.  ARC  is an Add-on to google Chrome. To set it up Advanced REST Client on Google Chrome, follow the steps

Step 1: Follow the link below to setup Advanced REST Client

Step 2: Install it and then launch it. The interface is a shown below

### Part 18: Add New Location

To Insert a new Location we need to make a POST request.

Step 1: Write the AddLocation method in the LocationService. Copy and paste in the LocationService
```
public void AddLocation(Location location) {
     locationRepository.save(location);
}
``` 

Step 2: Write the AddLocation method in the LocationController. The method is given below. Copy and paste in the LocationController
```
@PostMapping("/locations/addNew")
public void AddLocation(@RequestBody Location location) {
    locationService.AddLocation(location);
}
 ```

 

Step 3: Relaunch the application.

Step 4: Make a POST request to the http://localhost:8080/locations/addNew url using Advanced Rest Client. The body of the request is as shown below:
```
{
    "id": 5,
    "name": "KanysTown"
}
 ```

Step 5: Now access the http://localhost/locations url to ensure the location is inserted. Then add a couple more locations.

### Part 19 – Inserting a New User

We would also do the same as we did with adding new Location, but with a little variation. We’ll make some modifications to the User entity and to our data.sql file.

Step 1: Launch the application and access the H2 Console to check  what columns are there in the User table. 
Notice that there is a column in the User table called location_Id.

Step 2: Open the data.sql file. In the code to insert location, change the location_id to locationid (remove the underscore)

Step 3: Open the User model class. Add a new field named locationid.

Step 4: Add the getters and setters for the new locationid field

Step 5: In the @JoinColumn annotation, change the attribute location_id to locationid.

Step 6: Add new attributes to the @JoinColumn: insertable= false, updatable= false

At the end all the changes you have made is given below:

```
@ManyToOne
@JoinColumn(name="locationid", insertable = false, updatable=false)
private Location location;

private Integer locationid;

public Integer getLocationid() {
    return locationid;
}

public void setLocationid(Integer locationid) {
    this.locationid = locationid;
}
```

Step 7: Open the UserService and add the following code to insert new User
```
public void UpdateUser(User user) {
	userRepository.save(user);
}
 ```

Step 8: Open the UserController and add the following code
```
@PostMapping("/users/addNew")
public void AddUser(@RequestBody User user) {
    userService.AddUser(user);
}
 ```

Step 10: Launch the application and an try to insert a new user using Advanced REST Client

### Part 20 – Inserting a New Post

Try to make the changes similar to the ones we made to insert a User. Try it and see if you get it, before following the procedure. The procedures are below:

Step 1: Open the data.sql file. Change the post_id to postid for the insert into POST queries.

Step 2: In the Post model, add a new field named postid.

Step 3: Add the getters and setters for the new postid field

Step 4: In the @JoinColumn annotation, change the post_id to postid.

Step 5: Add updatable and insertable attribute and set them to false

At the end, the changes to the  Post model would be as follows:

```
@ManyToOne
@JoinColumn(name="userid", insertable=false, updatable=false)
private User user;

private Integer userid;	

public Integer getUserid() {
    return userid;
}

public void setUserid(Integer userid) {
    this.userid = userid;
}
```

Step 6: Launch the application and make a request using the Advanced REST Client. The Json file is as shown below
```
{
"id": 4,
"postDate": "2019-09-02T17:42:03.878",
"userid": 2,
"details": "Kindson The Genius"
}
 ```
Check the H2 Console to ensure that the data inserted correctly.

### Part 21 – Updating Location Details

* To update Location details, follows the steps below:

Step 1: Open the LocationController file

Step 2: Copy and paste the code below
```
@PutMapping("/location/{id}/update")
public void UpdateLocation(@RequestBody Location location) {
	locationService.UpdateLocation(location);
}
 ```

Step 3: Open the LocationService and paste the code below:
```
public void UpdateLocation(Location location) {
	locationRepository.save(location);
}
 ```

Step 4: Launch the application. Use Advanced REST Client to attempt to update a location. Set the method this time to PUT

 

### Part 22 – Updating User Details

* To update Location details, follows the steps below:

Step 1: Open the UserController file

Step 2: Copy and paste the code below
```
@PutMapping("/user/{id}/update")
public void UpdateUsern(@RequestBody User user ) {
	userService.UpdateUser(user);
}
 ```

Step 3: Open the UserService and paste the code below:
```
public void UpdateUser(User user) {
	userRepository.save(user);
}
 
```

Step 4: Relaunch and test
 
### Part 23 – Updating Post Details

* To update Location details, follows the steps below:

Step 1: Open the PostController file

Step 2: Copy and paste the code below
```
@PutMapping("/post/{id}/update")
public void UpdatePost(@RequestBody Post post) {
	postService.UpdatePost(post);
}
``` 

Step 3: Open the PostService and paste the code below:
```
public void UpdatePost(Post post) {
	postRepository.save(post);
}
```

Step 4: Relaunch and test

### Part 24: Deleting a Post

* Deleting a Post would be quite straightforward as the Post object does not have any child objects. So let’s get it done!

Step 1: Copy the code below and paste in the PostController
```
@DeleteMapping("/post/{id}/delete")
public void DeletePost(@PathVariable Integer id) {
     postService.DeletePost(id);
}
 ```

Step 2: Copy the code below and paste into the PostService
```
public void DeletePost(Integer id) {
    postRepository.deleteById(id);
}
``` 

Step 3: Relaunch the application and test it using r the REST client. Either way, check that the post was deleted using H2 Console.

 

### Part 25: Deleting a User

* The question about deleting a User is this: If we delete a particular user, 
what will happen to all the posts belonging to this user? Well, before we answer this question, 
let’s try to delete a user using the method used in deleting post.

Step 1: Copy the code below and paste in the UserController
```
@DeleteMapping("/user/{id}/delete")
public void DeleteUser(@PathVariable Integer id) {
     userService.DeleteUser(id);
}
``` 

Step 2: Copy the code below and paste into the UserService
```
public void DeleteUser(Integer id) {
    userRepository.deleteById(id);
}
``` 

Step 3: Relaunch the application and test it using  REST client. Try to delete user/1. You will notice that the delete operation fails. You receive a message like:
```
{
"timestamp": "2019-09-02T22:23:30.879+0000",
"status": 500,
"error": "Internal Server Error",
"message": "could not execute statement; SQL [n/a]; constraint ["FK2SF14YOUCWQSO9PDCH0UIWPEM: PUBLIC.POST FOREIGN KEY(USERID) REFERENCES PUBLIC.USER(ID) (1)"; SQL statement: delete from user where id=? [23503-199]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
"path": "/user/1/delete"
}
 ```

The reason for this error is that we try to delete a user who has posts.( an entity with child entities)

Let’s now solve this problem by talking about CascadeTypes

 

### Part 26: CascadeType in Hibernate

* The CascadeType answers the question of what will happen when you delete an object has children reference objects. Now a number of things could happen

CascadeType.REMOVE: All the child entities are deleted (all post for the user is deleted)
CascadeType.ALL: In this case, the operation is propagated from the parent to all the child entities
CasCadeType.MERGE: It propagates merge operation from the parent to the child entities
CascadeType.PERSIST: Here, the transient instance is made persistent
CascadeType.DETACH: This cascade type removes that entity from the persistent context.
Let’s not go to far! We’ll only use the second one: CascadeType.ALL

To solve the problem simply take the step below:

Step 1: Open the User model

Step 2: Add cascade = CascadeType.ALL  to the @OneToMany annotation. It would be as shown below:
```
@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
private List<Post> posts;
 ```

Step 3: Now try to repeat the delete operation and see that it works!

 

Exercise: As an exercise for you, try out other CascadeTypes. Let me know in the comment box below the outcome

 

### Part 27 – Deleting a Location

* This follows for the method for deleting a user.

Step 1: Copy the code below and paste in the LocationController
```
@DeleteMapping("/location/{id}/delete")
public void DeleteLocation(@PathVariable Integer id) {
     locationService.DeleteLocation(id);
}
``` 

Step 2: Copy the code below and paste into the LocationService
```
public void DeleteLocation(Integer id) {
    locationRepository.deleteById(id);
}
 ```

Step 2: Open the User model, Add cascade = CascadeType.ALL  to the @OneToMany annotation. It would be as shown below:
```
@OneToMany(mappedBy="location", cascade = CascadeType.ALL)
private List<User> users;
``` 

Step 3: Relaunch and try deleting a location. It works too.

 

### Part 28: FetchType in Hibernate

* I would like to wrap up this course by talking about FetchTypes. This relates to how data is pulled from the repository – essentially from the database.

We would focus on two types of FetchTypes: FetchType.EAGER and FetchType.LAZY
 
FetchType.EAGER: This FetchType tells hibernate to retrieve the related record along with the initial query. This can be an advantage, but when the child entities to fetch is much, then it could create an overhead can jeopardize performance. For example, having to retrieve all the users in a location each time to access a Location.

FetchType.LAZY: This can be used to tell hibernate to delay the initialization of the mapping until you access it explicitly.

Do the following exercise:

Exercise 1: Add fetchType= FetchType.EAGER to all the @ManyToOne annotation
Exercise 2: Add fetchType=FetchType.LAZY  to all the @OneToMany annotations
Exercise 3: Test the application with the FetchTypes.
Exercise 4: Remove all the FetchTypes and test the application again.

