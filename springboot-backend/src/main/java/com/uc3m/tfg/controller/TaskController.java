package com.uc3m.tfg.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.Task;
import com.uc3m.tfg.service.GroupService;
import com.uc3m.tfg.service.TaskService;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired	
	private TaskService taskService;
	@Autowired
	private GroupService groupService;
	
	// Get all tasks
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Task> getAllTasks(){
		List<Task> tasks = StreamSupport
				.stream(taskService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return tasks;
	}
	
	// Create task
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/{group}")
	public ResponseEntity<?> createTask(@RequestBody Task task, @PathVariable Long group) {
		if(!groupService.findById(group).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// Search group
				Optional<Group> oGroup = groupService.findById(group);
				
				oGroup.get().addTask(task);	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
	}
	
	// Get task by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable Long id) {
		
		Optional<Task> oTask = taskService.findById(id);
		
		if(!oTask.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oTask);
	}
	
	// Update task
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
		Optional<Task> task = taskService.findById(id);
		
		if(!task.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		task.get().setTitle(taskDetails.getTitle());
		task.get().setStatement(taskDetails.getStatement());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task.get()));
	}
	
	// Delete task
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable Long id){
		if(!taskService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		taskService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}