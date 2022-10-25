package com.example.dobbyga_station.user.domain;

import com.example.dobbyga_station.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserRegisterRequest {

	@NotNull
	private final String email;

	@NotNull
	private final String pw;

	@NotNull
	private final String name;

	@NotNull
	private final UserRole role;

	@NotNull
	private final Integer phoneNum;

	private final Address address;
}
