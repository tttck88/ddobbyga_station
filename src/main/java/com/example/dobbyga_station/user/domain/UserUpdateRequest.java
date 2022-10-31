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
public class UserUpdateRequest {

	@NotNull
	private final String email;
	private final String pw;
	private final String name;
	private final UserRole role;
	private final String phoneNum;
	private final Address address;
}
