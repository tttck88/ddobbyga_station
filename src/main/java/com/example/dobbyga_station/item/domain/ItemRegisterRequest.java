package com.example.dobbyga_station.item.domain;

import com.example.dobbyga_station.item.enums.Category;
import lombok.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
@Builder
@NoArgsConstructor(force = true)
public class ItemRegisterRequest {

	@NotNull
	private final String name;

	@NotNull
	private final Integer price;

	@NotNull
	private final Integer stockQuantity;

	@NotNull
	private final Category category;
}
