package com.example.dobbyga_station.item.service;

import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.domain.ItemRegisterRequest;
import com.example.dobbyga_station.item.domain.ItemResponse;
import com.example.dobbyga_station.item.domain.ItemUpdateRequest;
import com.example.dobbyga_station.item.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

	@Transactional
	public ItemResponse updateItem(ItemUpdateRequest itemUpdateRequest) {
		Item item = itemRepository.findById(itemUpdateRequest.getId())
			.map(i -> i.updateItem(itemUpdateRequest))
			.orElseThrow(() -> new CustomException(ErrorResult.ITEM_NOT_FOUND));

		return ItemResponse.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.stockQuantity(item.getStockQuantity())
			.category(item.getCategory())
			.build();
	}

	public ItemResponse findItem(Long itemId) {
		return itemRepository.findById(itemId)
			.map(i -> ItemResponse.builder()
				.id(i.getId())
				.name(i.getName())
				.price(i.getPrice())
				.stockQuantity(i.getStockQuantity())
				.category(i.getCategory())
				.build())
			.orElseThrow(() -> new CustomException(ErrorResult.ITEM_NOT_FOUND));
	}

	public List<ItemResponse> findAllItem() {
		return itemRepository.findAll().stream()
			.map(i -> ItemResponse.builder()
				.id(i.getId())
				.name(i.getName())
				.price(i.getPrice())
				.stockQuantity(i.getStockQuantity())
				.category(i.getCategory())
				.build())
			.collect(Collectors.toList());
	}



}
