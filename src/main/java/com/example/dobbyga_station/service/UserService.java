package com.example.dobbyga_station.service;

import com.example.dobbyga_station.domain.*;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public UserResponse registerUser(final UserRegisterRequest userRegisterRequest) {
		if(userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent()) {
			throw new CustomException(ErrorResult.DUPLICATE_USER_REGISTER);
		}

		final User user = User.builder()
			.email(userRegisterRequest.getEmail())
			.pw(passwordEncoder.encode(userRegisterRequest.getPw()))
			.name(userRegisterRequest.getName())
			.phoneNum(userRegisterRequest.getPhoneNum())
			.role(userRegisterRequest.getRole())
			.build();

		final User savedUser = userRepository.save(user);

		return UserResponse.builder()
			.email(savedUser.getEmail())
			.name(savedUser.getName())
			.phoneNum(savedUser.getPhoneNum())
			.role(savedUser.getRole())
			.build();
	}

	@Transactional
	public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findByEmail(userUpdateRequest.getEmail())
			.map(u -> u.updateUser(userUpdateRequest))
			.orElseThrow(() -> new CustomException(ErrorResult.User_NOT_FOUND));

		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.phoneNum(user.getPhoneNum())
			.role(user.getRole())
			.build();
	}

	public List<UserResponse> findAllUser() {
		List<User> users = userRepository.findAll();

		return users.stream()
			.map(user -> UserResponse.builder()
				.id(user.getId())
				.email(user.getEmail())
				.phoneNum(user.getPhoneNum())
				.role(user.getRole())
				.build())
			.collect(Collectors.toList());
	}

	public UserResponse findUser(String email) {
		return userRepository.findByEmail(email)
			.map(u -> UserResponse.builder()
				.id(u.getId())
				.email(u.getEmail())
				.name(u.getName())
				.phoneNum(u.getPhoneNum())
				.role(u.getRole())
				.build())
			.orElseThrow(() -> new CustomException(ErrorResult.User_NOT_FOUND));
	}
}
