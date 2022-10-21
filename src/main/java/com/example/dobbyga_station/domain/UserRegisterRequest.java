package com.example.dobbyga_station.domain;

import com.example.dobbyga_station.enums.UserRole;
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
	private final int phoneNum;
}
