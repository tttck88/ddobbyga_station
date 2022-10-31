package com.example.dobbyga_station.order.service;

import com.example.dobbyga_station.delivery.domain.Delivery;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.respository.ItemRepository;
import com.example.dobbyga_station.order.domain.Order;
import com.example.dobbyga_station.order.domain.OrderItemRequest;
import com.example.dobbyga_station.order.enums.OrderStatus;
import com.example.dobbyga_station.order.repository.OrderRepository;
import com.example.dobbyga_station.orderItem.domain.OrderItem;
import com.example.dobbyga_station.user.domain.User;
import com.example.dobbyga_station.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;

	public Order order(Long userId, Delivery delivery, List<OrderItemRequest> orderItemRequests) {
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorResult.USER_NOT_FOUND));

		List<OrderItem> orderItems = orderItemRequests.stream()
			.map(o -> createOrderItem(o))
			.collect(Collectors.toList());

		Order order = createOrder(user, delivery, orderItems);

		orderRepository.save(order);

		return order;
	}

	public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
		final Item item = itemRepository.findById(orderItemRequest.getItemId()).orElseThrow(() -> new CustomException(ErrorResult.ITEM_NOT_FOUND));
		item.removeStock(orderItemRequest.getCount());

		return OrderItem.builder()
			.item(item)
			.orderPrice(item.getPrice())
			.count(orderItemRequest.getCount())
			.build();
	}

	public Order createOrder(User user, Delivery delivery, List<OrderItem> orderItems) {
		return Order.builder()
			.user(user)
			.orderItems(orderItems)
			.delivery(delivery)
			.orderDate(LocalDateTime.now())
			.status(OrderStatus.ORDER)
			.build();
	}


}
