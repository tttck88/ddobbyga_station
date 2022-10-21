package com.example.dobbyga_station.domain;

import com.example.dobbyga_station.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

//	@Column(nullable = false)
	private String pw;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private UserRole role;

	@Column(nullable = false)
	private int phoneNum;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column()
	private LocalDateTime updatedAt;

	public User updateUser(UserUpdateRequest userUpdateRequest) {
		this.email = userUpdateRequest.getEmail();
		this.pw = userUpdateRequest.getPw();
		this.name = userUpdateRequest.getName();
		this.role = userUpdateRequest.getRole();
		this.phoneNum = userUpdateRequest.getPhoneNum();
		return this;
	}
}
