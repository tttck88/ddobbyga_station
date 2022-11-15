package com.example.dobbyga_station.user.service;

import com.example.dobbyga_station.user.domain.*;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.user.repository.UserRepository;
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

	@Transactional
	public UserResponse registerUser(final UserRegisterRequest userRegisterRequest) {
		if(userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent()) {
			throw new CustomException(ErrorResult.DUPLICATE_USER_REGISTER);
		}

		final User user = User.builder()
			.email(userRegisterRequest.getEmail())
			.pw(new BCryptPasswordEncoder().encode(userRegisterRequest.getPw()))
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
			.orElseThrow(() -> new CustomException(ErrorResult.USER_NOT_FOUND));

		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.phoneNum(user.getPhoneNum())
			.role(user.getRole())
			.build();
	}

	public UserResponse updateUserPassWord(UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findByEmail(userUpdateRequest.getEmail())
			.map(u -> u.UpdateUserPassWord(new BCryptPasswordEncoder().encode(userUpdateRequest.getPw())))
			.orElseThrow(() -> new CustomException(ErrorResult.USER_NOT_FOUND));

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
			.orElseThrow(() -> new CustomException(ErrorResult.USER_NOT_FOUND));
	}

	public UserResponse findPassword(UserFindPwRequest userFindPwRequest) {
		User user = userRepository.findByEmail(userFindPwRequest.getEmail())
			.orElseThrow(() -> new CustomException(ErrorResult.USER_NOT_FOUND));

		if(!userFindPwRequest.getName().equals(user.getName())) {
			throw new CustomException(ErrorResult.USER_NOT_FOUND);
		}

		if(!userFindPwRequest.getPhoneNum().equals(user.getPhoneNum())) {
			throw new CustomException(ErrorResult.USER_NOT_FOUND);
		}

		// 새로운패스워드생성
		String newPw = makeRandomPw();

		// 패스워드수정
		updateUserPassWord(UserUpdateRequest.builder()
			.email(userFindPwRequest.getEmail())
			.pw(newPw)
			.build());

		// 수정된패스워드전송
		sendNewPwToEmail(userFindPwRequest.getEmail(), user.getName() ,newPw);

		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.phoneNum(user.getPhoneNum())
			.role(user.getRole())
			.build();
	}

	private String makeRandomPw() {
		// todo 랜덤문자열 생성
		return "newPw";
	}

	private void sendNewPwToEmail(String email, String name, String newPw) {
		// todo 이메일 전송
		System.out.println("email = " + email);
		System.out.println("name = " + name);
		System.out.println("newPw = " + newPw);
	}
}
