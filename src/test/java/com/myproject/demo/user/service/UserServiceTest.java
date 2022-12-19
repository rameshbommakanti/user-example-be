package com.myproject.demo.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.myproject.demo.exception.UserNotFoundException;
import com.myproject.demo.user.dto.UserDTO;
import com.myproject.demo.user.model.User;
import com.myproject.demo.user.repository.UserRepository;


public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	@BeforeEach
	public void setup() {
		  MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void updateUser() {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		dto.setId(1L);
		User user=new User();
		user.setId(1L);
		Optional<User> opt=Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(opt);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
		UserDTO userDTO=userService.editUser(dto);
		assertNotNull(userDTO);
	}
	@Test
	public void getUser() {
		User user=new User();
		user.setId(1l);
		Optional<User> opt=Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(opt);
		UserDTO userDTO=userService.getUser(1L);
		assertNotNull(userDTO);
	}
	@Test
	public void getUsers() {
		User user=new User();
		user.setId(1l);
		List<User> users=new ArrayList<>();
		Mockito.when(userRepository.findAll()).thenReturn(users);
		List<UserDTO> userDTOs=userService.getUsers();
		assertNotNull(userDTOs);
	}
	@Test
	public void saveUser() {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		User user=new User();
		user.setId(1l);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
		UserDTO userDTO=userService.saveUser(dto);
		assertNotNull(userDTO);
	}
	
	@Test
	public void deleteUser() {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		User user=new User();
		user.setId(1l);
		Optional<User> opt=Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(opt);
		Mockito.doNothing().when(userRepository).delete(Mockito.any());
		userService.deleteUser(1L);
		Mockito.verify(userRepository,Mockito.times(1)).delete(Mockito.any());
	}
	
	@Test
	public void getUserNotFound() {
		User user=new User();
		user.setId(1l);
		Optional<User> opt=Optional.of(user);
		assertThrows(UserNotFoundException.class, ()->{
			userService.getUser(1L);
		});
		
	}
	
	@Test
	public void deleteUserNotfound() {
		assertThrows(UserNotFoundException.class, ()->{
		userService.deleteUser(1L);
		});
	}

}
