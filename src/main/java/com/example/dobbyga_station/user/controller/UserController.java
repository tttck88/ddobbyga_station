package com.example.dobbyga_station.user.controller;

import com.example.dobbyga_station.user.domain.*;
import com.example.dobbyga_station.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(value = "/api/user/register")
	ResponseEntity<UserResponse> registerUser(@RequestBody @Valid final UserRegisterRequest userRegisterRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRegisterRequest));
	}

	@PatchMapping(value = "/api/user/update")
	ResponseEntity<UserResponse> updateUser(@RequestBody @Valid final UserUpdateRequest userUpdateRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userUpdateRequest));
	}

	@PatchMapping(value = "/api/user/updatePassword")
	ResponseEntity<UserResponse> updateUserPassWord(@RequestBody @Valid final UserUpdateRequest userUpdateRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserPassWord(userUpdateRequest));
	}

	@PostMapping(value = "/api/user/login")
	ResponseEntity<UserResponse> login(@RequestBody final UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping(value = "/api/user/list")
	ResponseEntity<List<UserResponse>> userList() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUser());
	}

	@GetMapping(value = "/api/user/detail")
	ResponseEntity<UserResponse> userDetail(@RequestParam final String email) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(email));
	}
}



























