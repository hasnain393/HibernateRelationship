## Auditing in Spring Boot(Step by Step Tutorial)

## 1.Introduction to Auditing

	Auditing helps you track changes to a table. For example, yo need to know:

	* who modified the record last
	* when was it modified?
	* who created the record
	* when was it crated?
	
	Before, now what I did was to add additional fields to my entities. So when a record is inserted, I have a code to insert the current time. Then I also insert the name of the current user. Now, you don’t have to worry about this. It is done automatically for you!

	So let’s get started.
## 2. Create the Auditable Class

	* Here we would create an abstract class that holds the audit information. 
	So any entity we would like to audit would extend this class. Follow the steps below:

	Step 1:  create an abstract class class Auditable. This should be in the models package. This class would have the following fields:

	**CreatedBy
	
	**CreatedDate**
	
	**LastModifiedBy**
	
	**LastModifiedDate**
	
	TIMESTAMP  is available with the namespace below:
	```
	import static javax.persistence.TemporalType.TIMESTAMP;
	```
	
	Step 2: Annotate the fields with annotations of the same name with the field. For example, CreatedBy is annotated with @CreatedBy

	Step 3: Annotate the class with @MappedSuperclass annotation. This means that this class has not table created for it
	
	Step 4: Annotate the class with @EntityListener annotation

	Step 5: Generate the getters and the setters

`	At the end, the class would be as shown below (getters and setters are omitted for brevity):
```
	@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    protected U createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date createdDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;
    
    // getters and setter here
}
```

## 3. Make the Classes Extend Auditable

	Now that we have the Auditable class, we can now extend it. So any entity we want to audit have to extend this class. Follow the steps:

	Step 1: Modify the Post, User and Location file to make then extend Auditable<String>

	Step 2: Launch the application.

	Step 3: Visit http://localhost:8080/users. Check the the four additional field are now available.

	Note: Follow step 4 if you have enabled h2-console

	Step 4(Optional): Visit http://localhost:8080/h2-console. Check that the additional fields exist in the tables
	
## 4. Implement the AuditorAware Interface

	Now, you need to create a class that implements the AuditorAware interface. This is the class the returns the current Auditor. That is the current user. Follow the steps below:

	Step 1: In the models package, create a class the implements AuditorAware
	
	Step 2: Override the getCurrentAuditor method of this class. Just return a string which represents the current user

	The class is as shown below:
	```
	public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		// Just return a string representing the username
		return Optional.ofNullable("Kindson").filter(s -> !s.isEmpty());
	}
	
	}
	```

## 5. Create an AuditorAware Bean

	Finally, we need to create a bean of type AuditorAware.

	Step 1: Annotate the main application class with @EnableJpaAuditing as shown below:

	@EnableJpaAuditing(auditorAwareRef=”auditorAware”)

	Step 2: Write a bean method that returns an AuditorAware object.  At the end, the main application class would be as shown below:
	```
	@EnableJpaAuditing(auditorAwareRef="auditorAware")
	@SpringBootApplication
	public class RelationshipDemoApplication {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RelationshipDemoApplication.class, args);
	}

	}
```
## 6. Test the Application

`	Step 1: Launch the application

	Step 2: Open Advanced Rest Client (or PostMan Rest Client).

	Step 3: Perform an insert operation in one of the tables and check the that audit data is added as well.

	
	