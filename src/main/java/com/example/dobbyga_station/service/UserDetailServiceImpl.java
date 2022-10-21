package com.example.dobbyga_station.service;

import com.example.dobbyga_station.domain.User;
import com.example.dobbyga_station.domain.UserDetail;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(u -> new UserDetail(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
			.orElseThrow(() -> new CustomException(ErrorResult.User_NOT_FOUND));
	}
}
