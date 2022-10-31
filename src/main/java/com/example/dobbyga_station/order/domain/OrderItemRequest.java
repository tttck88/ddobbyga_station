package com.example.dobbyga_station.order.domain;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Builder
@NoArgsConstructor(force = true)
public class OrderItemRequest {

	private final Long itemId;
	private final int count;
}
