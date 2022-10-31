package com.example.dobbyga_station.delivery.domain;

import com.example.dobbyga_station.delivery.enums.DeliveryStatus;
import com.example.dobbyga_station.order.domain.Order;
import com.example.dobbyga_station.user.domain.Address;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id", nullable = false)
	private Long id;

	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status; // READY, COMP
}
