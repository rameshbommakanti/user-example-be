package com.myproject.demo.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.myproject.demo.user.dto.UserDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@LocalServerPort
	private int port;

	private Long sourceAccount;

	@BeforeEach
	public void setup() {
		sourceAccount = createAccount("testsource@test.com", "123456", "testaccount1", "name");
	}

	@Test
	void saveUser() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(dto, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.POST, entity,
				UserDTO.class);
		UserDTO actual = response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(actual.getId());
		assertEquals(dto.getFirstName(), actual.getFirstName());

	}

	@Test
	void updateUser() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		dto.setId(sourceAccount);

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(dto, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.PUT, entity,
				UserDTO.class);
		UserDTO actual = response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(actual.getId());

	}

	@Test
	void getUser() throws Exception {

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user/" + sourceAccount),
				HttpMethod.GET, entity, UserDTO.class);
		UserDTO actual = response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(actual.getId());

	}

	@Test
	void getUsers() throws Exception {

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(headers);
		ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.GET, entity,
				List.class);
		List<UserDTO> actual = response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(actual);

	}

	@Test
	void deleteUser() throws Exception {

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(headers);
		ResponseEntity response = restTemplate.exchange(createURLWithPort("/user/" + sourceAccount), HttpMethod.DELETE,
				entity, ResponseEntity.class);
		assertEquals(200, response.getStatusCodeValue());

	}

	private Long createAccount(String email, String mobile, String firstName, String lastName) {
		UserDTO dto = new UserDTO();
		dto.setEmail(email);
		dto.setMobileNumber(mobile);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(dto, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.POST, entity,
				UserDTO.class);
		UserDTO actual = response.getBody();
		return actual.getId();
	}
	
	@Test
	void updateUserNotFound() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setEmail("test@test.coom");
		dto.setMobileNumber("9828022338");
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		dto.setId(200L);

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(dto, headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.PUT, entity,
				UserDTO.class);
		assertEquals(404, response.getStatusCodeValue());

	}
	
	@Test
	void getUserNotfound() throws Exception {

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(headers);
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user/" + 200L),
				HttpMethod.GET, entity, UserDTO.class);
		assertEquals(404, response.getStatusCodeValue());

	}

	private URI createURLWithPort(String uri) {
		try {
			return new URI("http://localhost:" + port + uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}