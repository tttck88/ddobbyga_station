package com.example.dobbyga_station.user.domain;

import com.example.dobbyga_station.user.enums.UserRole;
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
	@Column(name = "item_id", nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String pw;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private UserRole role;

	@Column(nullable = false)
	private int phoneNum;

	@Embedded
	private Address address;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column()
	private LocalDateTime updatedAt;

	public User updateUser(UserUpdateRequest userUpdateRequest) {
		this.email = userUpdateRequest.getEmail();
		this.name = userUpdateRequest.getName();
		this.role = userUpdateRequest.getRole();
		this.phoneNum = userUpdateRequest.getPhoneNum();
		return this;
	}

	public User UpdateUserPassWord(String pw) {
		this.pw = pw;
		return this;
	}
}
