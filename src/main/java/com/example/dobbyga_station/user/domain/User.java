package com.example.dobbyga_station.user.domain;

import com.example.dobbyga_station.common.BaseEntity;
import com.example.dobbyga_station.order.domain.Order;
import com.example.dobbyga_station.user.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String pw;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private UserRole role;

	@Column(nullable = false)
	private String phoneNum;

	@Embedded
	private Address address;

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
