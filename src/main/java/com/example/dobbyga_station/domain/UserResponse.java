package com.example.dobbyga_station.domain;

import com.example.dobbyga_station.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserResponse {

	private final Long id;
	private final String email;
	private final String name;
	private final UserRole role;
	private final int phoneNum;
}
