package com.example.dobbyga_station.order.repository;

import com.example.dobbyga_station.delivery.domain.Delivery;
import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.order.domain.Order;
import com.example.dobbyga_station.order.enums.OrderStatus;
import com.example.dobbyga_station.orderItem.domain.OrderItem;
import com.example.dobbyga_station.user.domain.Address;
import com.example.dobbyga_station.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

	@Autowired
	OrderRepository target;

	@Test
	public void 주문생성() {
	    // given
		User user = User.builder().name("한정택").build();
		List<OrderItem> orderItems = Arrays.asList(OrderItem.builder().build(),OrderItem.builder().build());
		Delivery delivery = Delivery.builder().address(Address.builder().city("우리집").build()).build();

		Order order = Order.builder()
			.user(user)
			.orderItems(orderItems)
			.delivery(delivery)
			.orderDate(LocalDateTime.now())
			.status(OrderStatus.ORDER)
			.build();

		// when
		final Order result = target.save(order);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getUser().getName()).isEqualTo("한정택");
		assertThat(result.getOrderItems().size()).isEqualTo(2);
		assertThat(result.getDelivery().getAddress().getCity()).isEqualTo("우리집");
	}
	
	@Test
	public void 주문조회() {
	    // given
		Order order = target.save(Order.builder().build());
	    
	    // when
		Order result = target.findById(order.getId()).orElse(null);

	    // then
		assertThat(result).isNotNull();
	}
	
	@Test
	public void 전체주문조회() {
	    // given
		target.save(Order.builder().build());
	    
	    // when
		final List<Order> result = target.findAll();

		// then
		assertThat(result.size()).isEqualTo(1);
	}
}