package com.myproject.demo.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.demo.exception.UserNotFoundException;
import com.myproject.demo.user.dto.UserDTO;
import com.myproject.demo.user.model.User;
import com.myproject.demo.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	Function<User, UserDTO> toDTO = (user) -> {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setId(user.getId());
		dto.setLastName(user.getLastName());
		dto.setMobileNumber(user.getMobileNumber());
		;

		return dto;
	};

	Function<UserDTO, User> toBean = (dto) -> {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setMobileNumber(dto.getMobileNumber());

		return user;
	};

	public UserDTO saveUser(UserDTO userDTO) {

		User user = toBean.apply(userDTO);
		User userDB = userRepository.save(user);
		return toDTO.apply(userDB);

	}
	
	public UserDTO editUser(UserDTO userDTO) {

		Optional<User> userOpt = userRepository.findById(userDTO.getId());
		if(userOpt.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User user=userOpt.get();
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobileNumber(userDTO.getMobileNumber());
		User userDB = userRepository.save(user);
		return toDTO.apply(userDB);

	}
	
	public UserDTO getUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		User user=userOpt.get();
		return toDTO.apply(user);
	}
	
	public List<UserDTO> getUsers(){
		List<User> users = userRepository.findAll();
		List<UserDTO> dtos=new ArrayList<UserDTO>();
		for(User user:users) {
			dtos.add(toDTO.apply(user));
		}
		return dtos;
	}
	
	public void deleteUser(Long id){
		Optional<User> userOpt = userRepository.findById(id);
		if(userOpt.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User user=userOpt.get();
		userRepository.delete(user);
	}

}
