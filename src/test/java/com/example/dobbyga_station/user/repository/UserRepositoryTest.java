package com.example.dobbyga_station.user.repository;

import com.example.dobbyga_station.user.domain.Address;
import com.example.dobbyga_station.user.domain.User;
import com.example.dobbyga_station.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	UserRepository target;

	final String email = "tttck88@gmail.com";
	final String pw = "123456789";
	final String name = "한정택";
	final int phoneNum = 123456789;
	final UserRole role = UserRole.ROLE_ADMIN;
	final Address address = Address.builder()
		.city("인천")
		.street("용종로")
		.zipCode("400-000")
		.build();

	private User registerUser() {
		return target.save(User.builder()
			.email(email)
			.pw(pw)
			.name(name)
			.phoneNum(phoneNum)
			.role(role)
			.address(address)
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
			.address(address)
			.build();

	    // when
		final User result = target.save(user);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPhoneNum()).isEqualTo(phoneNum);
		assertThat(result.getRole()).isEqualTo(role);
		assertThat(result.getAddress()).isEqualTo(address);
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