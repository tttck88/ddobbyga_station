package com.example.dobbyga_station.controller;

import com.example.dobbyga_station.constants.AuthConstants;
import com.example.dobbyga_station.domain.UserRegisterRequest;
import com.example.dobbyga_station.domain.UserRequest;
import com.example.dobbyga_station.domain.UserResponse;
import com.example.dobbyga_station.domain.UserUpdateRequest;
import com.example.dobbyga_station.enums.UserRole;
import com.example.dobbyga_station.service.UserService;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class UserControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private Gson gson;

	final String email = "tttck88@gmail.com";
	final String pw = "123456789";
	final String name = "한정택";
	final int phoneNum = 123456789;
	final UserRole role = UserRole.ROLE_ADMIN;
	final String token = AuthConstants.TOKEN_TYPE + " " + "eyJyZWdEYXRlIjoxNjY2MzE3MzA3Mjc1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY2ODkwOTMwNywiZW1haWwiOiJ0dHRjazg4QGdtYWlsLmNvbSJ9.hRW02Iy6Y8O-jjAwqWfYtHSqd1F8fXD2TeJc7e2l93c";

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

	private void registerUser() throws Exception {
		// given
		final String url = "/api/user/register";

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRegisterRequest.builder()
					.email(email)
					.pw(pw)
					.name(name)
					.phoneNum(phoneNum)
					.role(role)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	}

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.context)
			.apply(springSecurity())
			.build();
	}
	
	@Test
	public void null이아님() {
	    // then
		assertThat(mockMvc).isNotNull();
	}
	
	@Test
	public void 회원가입실패_필수값없음_이메일() throws Exception {
	    // given
		final String url = "/api/user/register";
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRegisterRequest.builder()
					.email(null)
					.pw(pw)
					.name(name)
					.phoneNum(phoneNum)
					.role(role)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입실패_중복이메일() throws Exception {
	    // given
		final String url = "/api/user/register";
		registerUser();

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRegisterRequest.builder()
					.email(email)
					.pw(pw)
					.name(name)
					.phoneNum(phoneNum)
					.role(role)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입성공() throws Exception {
	    // given
		final String url = "/api/user/register";

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRegisterRequest.builder()
					.email(email)
					.pw(pw)
					.name(name)
					.phoneNum(phoneNum)
					.role(role)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	public void 회원수정실패_헤더에토큰이없음() throws Exception {
		// given
		final String url = "/api/user/update";
		registerUser();

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.content(gson.toJson(UserUpdateRequest.builder().phoneNum(phoneNum).name(name).role(role)))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isFound());
	}
	
	@Test
	public void 회원수정실패_필수값없음_이메일() throws Exception {
	    // given
	    final String url = "/api/user/update";
	    registerUser();

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.content(gson.toJson(UserUpdateRequest.builder().phoneNum(phoneNum).name(name).role(role)))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원수정실패_존재하지않는계정() throws Exception {
	    // given
		final String url = "/api/user/update";
		registerUser();
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.content(gson.toJson(UserUpdateRequest.builder().email("noEmail").phoneNum(phoneNum).name(name).role(role)))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isNotFound());
	}
	
	@Test
	public void 회원수정성공() throws Exception {
	    // given
		final String url = "/api/user/update";
		registerUser();
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.content(gson.toJson(UserUpdateRequest.builder().email(email).phoneNum(phoneNum).name(name).role(role)))
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 회원목록조회실패_헤더에토큰이없음() throws Exception {
	    // given
		final String url = "/api/user/list";
		registerUser();

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isFound());
	}

	@Test
	public void 회원목록조회성공() throws Exception {
		// given
		final String url = "/api/user/list";
		registerUser();

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.contentType(MediaType.APPLICATION_JSON)
		);

		final List<UserResponse> responses = gson.fromJson(resultActions
			.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), List.class);

		// then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 회원상세조회실패_헤더에토큰이없음() throws Exception {
	    // given
		final String url = "/api/user/detail";
	    registerUser();

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.param("email",email)
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isFound());
	}

	@Test
	public void 회원상세조회실패_해당이메일이존재하지않음() throws Exception {
		// given
		final String url = "/api/user/detail";

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.param("email",email)
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void 회원상세조회성공() throws Exception {
		// given
		final String url = "/api/user/detail";
		registerUser();

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.header(AuthConstants.AUTH_HEADER, token)
				.param("email",email)
				.contentType(MediaType.APPLICATION_JSON)
		);

		final UserResponse response = gson.fromJson(resultActions
			.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), UserResponse.class);

		// then
		resultActions.andExpect(status().isOk());
		assertThat(response.getEmail()).isEqualTo(email);
	}
	
	@Test
	public void 회원로그인실패() throws Exception {
	    // given
		final String url = "/api/user/login";
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRequest.builder().email(email).pw(pw).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void 회원로그인성공() throws Exception {
	    // given
		final String url = "/api/user/login";
		registerUser();

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRequest.builder().email(email).pw(pw).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isOk());
	}
}




















