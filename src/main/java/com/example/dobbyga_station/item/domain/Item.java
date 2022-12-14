package com.example.dobbyga_station.item.domain;

import com.example.dobbyga_station.common.BaseEntity;
import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import com.example.dobbyga_station.item.enums.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stockQuantity;

	@Column(nullable = false)
	private Category category;

	public Item updateItem(ItemUpdateRequest itemUpdateRequest) {
		this.name = itemUpdateRequest.getName();
		this.price = itemUpdateRequest.getPrice();
		this.stockQuantity = itemUpdateRequest.getStockQuantity();
		this.category = itemUpdateRequest.getCategory();
		return this;
	}

	public void removeStock(int count) {
		int restStock = this.getStockQuantity() - count;
		if(restStock < 0) {
			throw new CustomException(ErrorResult.NOT_ENOUGH_STOCK);
		} else {
			this.stockQuantity = restStock;
		}
	}
}
