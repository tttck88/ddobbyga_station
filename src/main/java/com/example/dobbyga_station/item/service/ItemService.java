package com.example.dobbyga_station.item.service;

import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.domain.ItemRegisterRequest;
import com.example.dobbyga_station.item.domain.ItemResponse;
import com.example.dobbyga_station.item.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public ItemResponse registerItem(final ItemRegisterRequest itemRegisterRequest) {
		final Item item = itemRepository.save(Item.builder()
			.name(itemRegisterRequest.getName())
			.price(itemRegisterRequest.getPrice())
			.stockQuantity(itemRegisterRequest.getStockQuantity())
			.category(itemRegisterRequest.getCategory())
			.build());

		return ItemResponse.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.stockQuantity(item.getStockQuantity())
			.category(item.getCategory())
			.build();
	}

}
