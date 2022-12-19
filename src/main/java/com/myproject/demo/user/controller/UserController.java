package com.myproject.demo.user.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.myproject.demo.user.dto.UserDTO;
import com.myproject.demo.user.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@PostMapping
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
		return ResponseEntity.ok(userService.saveUser(userDTO));
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO){
		return ResponseEntity.ok(userService.editUser(userDTO));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
		return ResponseEntity.ok(userService.getUser(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		return ResponseEntity.ok(userService.getUsers());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
	
}
