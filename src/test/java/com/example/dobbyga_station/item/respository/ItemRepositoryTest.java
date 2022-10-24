package com.example.dobbyga_station.item.respository;

import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.enums.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

	@Autowired
	ItemRepository target;
	
	final String name = "이름";
	final int price = 1000;
	final int stockQuantity = 10;
	final Category category = Category.TOP;

	@Test
	public void 아이템저장() {
	    // given
		Item item = Item.builder()
			.name(name)
			.price(price)
			.stockQuantity(stockQuantity)
			.category(category)
			.build();

		// when
		Item result = target.save(item);

	    // then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPrice()).isEqualTo(price);
		assertThat(result.getStockQuantity()).isEqualTo(stockQuantity);
		assertThat(result.getCategory().getValue()).isEqualTo(category.getValue());
	}

	@Test
	public void 아이템상세조회() {
	    // given
		Item item = target.save(Item.builder()
			.name(name)
			.price(price)
			.stockQuantity(stockQuantity)
			.category(category)
			.build());

	    // when
		Item result = target.getOne(item.getId());

	    // then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(item.getId());
		assertThat(result.getName()).isEqualTo(name);
	}
	
	@Test
	public void 아이템목록조회() {
	    // given
		Item item = target.save(Item.builder()
			.name(name)
			.price(price)
			.stockQuantity(stockQuantity)
			.category(category)
			.build());

		// when
		List<Item> result = target.findAll();

		// then
		assertThat(result.size()).isEqualTo(1);
	}
}