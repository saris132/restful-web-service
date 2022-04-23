package com.in28minutes.rest.webservices.restfulwebservice.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	//Retrieve all users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	//Retrieve one user
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		//When user is not found optional return a proper object
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id: " + id);
		}
		
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		EntityModel<User> resource = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		//HATEOAS
		
		return resource;
	}
		
	//Create one user
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedUser.getId())
					.toUri();

		return ResponseEntity.created(location).build();
	}
	
	//Delete one user
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		//it handles exceptions by its own
		userRepository.deleteById(id);
	}
	
	//Retrieve all post from a user
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostByUser(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id: " + id);
		}
		
		return userOptional.get().getPosts();
	}
	
	//Create one post
		@PostMapping("/jpa/users/{id}/posts")
		public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){
			Optional<User> userOptional = userRepository.findById(id);
			if (!userOptional.isPresent()) {
				throw new UserNotFoundException("id: " + id);
			}
			
			User user = userOptional.get();
			post.setUser(user);
			postRepository.save(post);
			URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(post.getId())
						.toUri();

			return ResponseEntity.created(location).build();
		}
}
