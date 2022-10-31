package com.example.dobbyga_station.order.service;

import com.example.dobbyga_station.delivery.domain.Delivery;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.enums.Category;
import com.example.dobbyga_station.item.respository.ItemRepository;
import com.example.dobbyga_station.order.domain.OrderItemRequest;
import com.example.dobbyga_station.order.repository.OrderRepository;
import com.example.dobbyga_station.user.domain.Address;
import com.example.dobbyga_station.user.domain.User;
import com.example.dobbyga_station.user.enums.UserRole;
import com.example.dobbyga_station.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService target;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ItemRepository itemRepository;

	private User createUser() {
		return User.builder()
			.id(-1L)
			.email("tttck88@gmail.com")
			.pw("123456")
			.name("한정택")
			.role(UserRole.ROLE_ADMIN)
			.phoneNum("01088840249")
			.address(Address.builder().city("인천").street("용종로").zipCode("400").build())
			.build();
	}

	private Item createItem(Long id, String name, int price, int stockQuantity, Category category) {
		return Item.builder()
			.id(id)
			.name(name)
			.price(price)
			.stockQuantity(stockQuantity)
			.category(category)
			.build();
	}

	@Test
	public void 상품주문실패_유저x() {
	    // given
		User user = createUser();
		Item item1 = createItem(-1L, "상품1", 1000, 5, Category.TOP);
		Item item2 = createItem(-2L, "상품2", 2000, 10, Category.BOTTOM);

		doReturn(Optional.empty()).when(userRepository).findById(-1L);

		List<OrderItemRequest> orderItemRequests = Arrays.asList(OrderItemRequest.builder().itemId(item1.getId()).count(item1.getStockQuantity()).build(),
			OrderItemRequest.builder().itemId(item2.getId()).count(item2.getStockQuantity()).build());

		Delivery delivery = Delivery.builder()
			.address(createUser().getAddress())
			.build();

	    // when
		final CustomException exception = assertThrows(CustomException.class, () -> target.order(user.getId(), delivery, orderItemRequests));
	    
	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.USER_NOT_FOUND);
	}
	
	@Test
	public void 상품주문실패_상품이유효하지않음() {
	    // given
		User user = createUser();
		Item item1 = createItem(-1L, "상품1", 1000, 5, Category.TOP);
		Item item2 = createItem(-2L, "상품2", 2000, 10, Category.BOTTOM);

		doReturn(Optional.of(user)).when(userRepository).findById(-1L);
		doReturn(Optional.empty()).when(itemRepository).findById(-1L);

		Delivery delivery = Delivery.builder()
			.address(createUser().getAddress())
			.build();

		List<OrderItemRequest> orderItemRequests = Arrays.asList(OrderItemRequest.builder().itemId(item1.getId()).count(item1.getStockQuantity()).build(),
			OrderItemRequest.builder().itemId(item2.getId()).count(item2.getStockQuantity()).build());


		// when
		final CustomException exception = assertThrows(CustomException.class, () -> target.order(user.getId(), delivery, orderItemRequests));

	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.ITEM_NOT_FOUND);
	}
	
	@Test
	public void 상품주문실패_재고가없음() {
	    // given
		User user = createUser();
		Item item = createItem(-1L, "상품1", 1000, 5, Category.TOP);

		doReturn(Optional.of(user)).when(userRepository).findById(-1L);
		doReturn(Optional.of(item)).when(itemRepository).findById(-1L);

		Delivery delivery = Delivery.builder()
			.address(createUser().getAddress())
			.build();

		List<OrderItemRequest> orderItemRequests = Arrays.asList(OrderItemRequest.builder().itemId(item.getId()).count(6).build());

	    // when
		final CustomException exception = assertThrows(CustomException.class, () -> target.order(user.getId(), delivery, orderItemRequests));

	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.NOT_ENOUGH_STOCK);
	}


	// 주문취소 테스트

}




















