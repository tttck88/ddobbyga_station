package com.example.dobbyga_station.service;

import com.example.dobbyga_station.domain.*;
import com.example.dobbyga_station.enums.UserRole;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService target;

	@Mock
	private UserRepository userRepository;

	final String email = "tttck88@gmail.com";
	final String pw = "123456789";
	final String name = "한정택";
	final int phoneNum = 123456789;
	final UserRole role = UserRole.ROLE_ADMIN;

	private UserRequest userRequest() {
		return UserRequest.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build();
	}

	private UserRegisterRequest userRegisterRequest() {
		return UserRegisterRequest.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build();
	}

	private UserUpdateRequest userUpdateRequest() {
		return UserUpdateRequest.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build();
	}

	private User user() {
		return User.builder()
			.id(-1L)
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build();
	}

	@Test
	public void 회원가입실패_이미존재함() {
	    // given
		doReturn(Optional.of(User.builder().email(email).build())).when(userRepository).findByEmail(email);
	    
	    // when
		final CustomException exception = assertThrows(CustomException.class,
			() -> target.registerUser(userRegisterRequest()));
	    
	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.DUPLICATE_USER_REGISTER);
	}

	@Test
	public void 회원가입성공() {
	    // given
		doReturn(Optional.empty()).when(userRepository).findByEmail(email);
	    doReturn(user()).when(userRepository).save(any(User.class));

	    // when
		UserResponse result = target.registerUser(userRegisterRequest());

		// then
		assertThat(result).isNotNull();
	}
	
	@Test
	public void 회원수정실패_존재하지않는계정() {
	    // given
		doReturn(Optional.empty()).when(userRepository).findByEmail(email);
	    
	    // when
		final CustomException exception = assertThrows(CustomException.class,
			() -> target.updateUser(userUpdateRequest()));
	    
	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.User_NOT_FOUND);
	}
	
	@Test
	public void 회원수정성공() {
	    // given
		doReturn(Optional.of(User.builder().build())).when(userRepository).findByEmail(email);
	    
	    // when
		UserResponse result = target.updateUser(userUpdateRequest());

		// then
		assertThat(result).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
	}
	
	@Test
	public void 전체회원조회성공() {
	    // given
		doReturn(Arrays.asList(
			User.builder().build(),
			User.builder().build(),
			User.builder().build()
		)).when(userRepository).findAll();
	    
	    // when
		final List<UserResponse> result = target.findAllUser();
	    
	    // then
		assertThat(result.size()).isEqualTo(3);
	}
	
	@Test
	public void 회원상세조회실패_해당유저가없음() {
	    // given
		doReturn(Optional.empty()).when(userRepository).findByEmail(email);
	    
	    // when
		CustomException exception = assertThrows(CustomException.class,
			() -> target.findUser(email));
	    
	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.User_NOT_FOUND);
	}
	
	@Test
	public void 회원상세조회성공() {
	    // given
		doReturn(Optional.of(User.builder().email(email).build())).when(userRepository).findByEmail(email);
	    
	    // when
		UserResponse result = target.findUser(email);

	    // then
		assertThat(result).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
	}
}