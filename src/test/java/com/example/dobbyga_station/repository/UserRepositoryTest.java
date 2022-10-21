package com.example.dobbyga_station.repository;

import com.example.dobbyga_station.domain.User;
import com.example.dobbyga_station.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository target;

	final String email = "tttck88@gmail.com";
	final String pw = "123456789";
	final String name = "한정택";
	final int phoneNum = 123456789;
	final UserRole role = UserRole.ROLE_ADMIN;

	private User registerUser() {
		return target.save(User.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build()
		);
	}

	@Test
	public void 회원가입() {
	    // given
		final User user = User.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.build();

	    // when
		final User result = target.save(user);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPhoneNum()).isEqualTo(phoneNum);
		assertThat(result.getRole()).isEqualTo(role);
	}

	@Test
	public void 회원상세조회() {
	    // given
		registerUser();

		// when
		User user = target.findByEmail(email).get();

		// then
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPhoneNum()).isEqualTo(phoneNum);
		assertThat(user.getRole()).isEqualTo(role);
	}

	@Test
	public void 회원목록조회() {
	    // given
		registerUser();
	    
	    // when
		List<User> users = target.findAll();

		// then
		assertThat(users.size()).isEqualTo(1);
	}
}