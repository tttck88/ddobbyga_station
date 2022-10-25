package com.example.dobbyga_station.item.service;

import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.item.domain.*;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

	@Test
	public void 상품수정실패_존재하지않음() {
	    // given
		doReturn(Optional.empty()).when(itemRepository).findById(-1L);
	    
	    // when
		final CustomException exception = assertThrows(CustomException.class,
			() -> target.updateItem(ItemUpdateRequest.builder().id(-1L).build()));
	    
	    // then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.ITEM_NOT_FOUND);
	}
	
	@Test
	public void 상품수정성공() {
	    // given
		doReturn(Optional.of(Item.builder().build())).when(itemRepository).findById(-1L);
	    
	    // when
		ItemResponse result = target.updateItem(ItemUpdateRequest.builder().id(-1L).name("수정함").build());
	    
	    // then
		assertThat(result.getName()).isEqualTo("수정함");
	}
	
	@Test
	public void 상품상세조회실패_존재하지않음() {
	    // given
		doReturn(Optional.empty()).when(itemRepository).findById(-1L);
	    
	    // when
		final CustomException exception = assertThrows(CustomException.class,
			() -> target.findItem(-1L));

		// then
		assertThat(exception.getErrorResult()).isEqualTo(ErrorResult.ITEM_NOT_FOUND);
	}
	
	@Test
	public void 상품상세조회성공() {
	    // given
		doReturn(Optional.of(Item.builder().id(-1L).build())).when(itemRepository).findById(-1L);
	    
	    // when
		final ItemResponse item = target.findItem(-1L);

		// then
		assertThat(item.getId()).isEqualTo(-1L);
	}
	
	@Test
	public void 상품목록조회() {
	    // given
		doReturn(Arrays.asList(Item.builder().build(),Item.builder().build(),Item.builder().build())).when(itemRepository).findAll();
	    
	    // when
		final List<ItemResponse> itemList = target.findAllItem();

		// then
		assertThat(itemList.size()).isEqualTo(3);

	}
}




















