package com.example.dobbyga_station.item.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

	ALL("ALL"),
	TOP("TOP"),
	BOTTOM("BOTTOM"),
	ACC("ACC");

	private final String value;
}
