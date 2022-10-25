package com.example.dobbyga_station.item.domain;

import com.example.dobbyga_station.item.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Builder
public class ItemUpdateRequest {

	@NotNull
	private final Long id;

	private final String name;

	private final int price;

	private final int stockQuantity;

	private final Category category;
}
