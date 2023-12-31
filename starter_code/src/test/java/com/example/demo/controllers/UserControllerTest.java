package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	CartRepository cartRepository;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	void findById() {
		Mockito.doReturn(Optional.of(new User())).when(userRepository).findById(Mockito.any());
		ResponseEntity<User> response = userController.findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void findByUserNameNotFound() {
		Mockito.doReturn(null).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("admin");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void findByUserNameSuccess() {
		Mockito.doReturn(new User()).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("admin");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void createUser_passwordLengthThan7() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("morethan7");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void createUserInvalidConfirmPassword() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("admin");
		request.setConfirmPassword("admmin1");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void createUserSuccess() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("admin123");
		request.setConfirmPassword("admin123");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	private CreateUserRequest createUserRequest() {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername("admin");
		return request;
	}
}
