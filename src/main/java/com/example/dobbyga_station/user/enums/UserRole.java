package com.example.dobbyga_station.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");

	private final String value;
}
