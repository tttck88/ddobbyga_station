package com.example.dobbyga_station.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
@NoArgsConstructor(force = true)
public class UserFindPwRequest {

	private final String email;
	private final String pw;
	private final String name;
	private final String phoneNum;
}
