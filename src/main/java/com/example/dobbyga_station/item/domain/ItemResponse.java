package com.example.dobbyga_station.item.domain;

import com.example.dobbyga_station.item.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
@NoArgsConstructor(force = true)
public class ItemResponse {
	private final Long id;

	private final String name;

	private final int price;

	private final int stockQuantity;

	private final Category category;
}
