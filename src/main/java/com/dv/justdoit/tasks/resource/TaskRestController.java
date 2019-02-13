/**
 *
 */
package com.dv.justdoit.tasks.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.dv.justdoit.auth.users.JwtInMemoryUserDetailsService;
import com.dv.justdoit.auth.users.JwtUserDetails;
import com.dv.justdoit.auth.util.AuthUtil;
import com.dv.justdoit.tasks.Task;
import com.dv.justdoit.tasks.TaskRepository;

/**
 * @author Dhawal Verma
 */
@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class TaskRestController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private JwtInMemoryUserDetailsService userService;


	@GetMapping ("/users/{username}/tasks")
	public List<Task> getAlltasks(@PathVariable String username) {

		JwtUserDetails user     = (JwtUserDetails) userService.loadUserByUsername(username);
		AuthUtil.validateUserAuthorization(user);
		Long userId = user.getId();
		return taskRepository.findByUserId(userId)
							 .parallelStream()
							 .filter(t -> t.getMasterTask() == null)
							 .collect(Collectors.toList());
	}

	@GetMapping ("/users/{username}/tasks/{id}")
	public Task getTask(@PathVariable String username, @PathVariable long id) {
		return taskRepository.findById(id)
							 .get();
	}

	@DeleteMapping ("/users/{username}/tasks/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable String username, @PathVariable long id) {

		taskRepository.deleteById(id);

		return ResponseEntity.noContent()
							 .build();
	}


	@PutMapping ("/users/{username}/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable String username, @PathVariable long id,
					@RequestBody Task task) {

		Task taskUpdated = taskRepository.save(task);

		return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
	}

	@PostMapping ("/users/{username}/tasks")
	public ResponseEntity<Void> createTask(@PathVariable String username, @RequestBody Task task) {
		JwtUserDetails user   = (JwtUserDetails) userService.loadUserByUsername(username);
		Long           userId = user.getId();
		task.setUserId(userId);
		Task createdTask = taskRepository.save(task);

		// Get current resource url
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(createdTask.getId())
											 .toUri();

		return ResponseEntity.created(uri)
							 .build();
	}

}
