package com.example.dobbyga_station.item.service;

import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.domain.ItemRegisterRequest;
import com.example.dobbyga_station.item.domain.ItemResponse;
import com.example.dobbyga_station.item.enums.Category;
import com.example.dobbyga_station.item.respository.ItemRepository;
import com.example.dobbyga_station.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

	@InjectMocks
	private ItemService target;
	@Mock
	private ItemRepository itemRepository;

	final String name = "이름";
	final int price = 1000;
	final int stockQuantity = 10;
	final Category category = Category.TOP;

	@Test
	public void 상품저장성공() {
	    // given
		doReturn(Item.builder().build()).when(itemRepository).save(any(Item.class));
	    
	    // when
		ItemResponse result = target.registerItem(ItemRegisterRequest.builder()
				.name(name)
				.price(price)
				.stockQuantity(stockQuantity)
				.category(category)
				.build());
	    
	    // then
		assertThat(result).isNotNull();
	}
}
