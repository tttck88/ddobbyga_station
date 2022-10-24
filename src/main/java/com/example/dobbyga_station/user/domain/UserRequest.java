package com.example.dobbyga_station.user.domain;

import com.example.dobbyga_station.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Builder
public class UserRequest {

	private final String email;
	private final String pw;
	private final String name;
	private final UserRole role;
	private final int phoneNum;
	private final Address address;
}
