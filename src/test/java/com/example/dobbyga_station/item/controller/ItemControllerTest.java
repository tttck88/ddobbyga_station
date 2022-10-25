package com.example.dobbyga_station.item.controller;

import com.example.dobbyga_station.constants.AuthConstants;
import com.example.dobbyga_station.item.domain.ItemRegisterRequest;
import com.example.dobbyga_station.item.domain.ItemResponse;
import com.example.dobbyga_station.item.domain.ItemUpdateRequest;
import com.example.dobbyga_station.item.enums.Category;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Transactional
class ItemControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private Gson gson;

	final String token = AuthConstants.TOKEN_TYPE + " " + "eyJyZWdEYXRlIjoxNjY2MzE3MzA3Mjc1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY2ODkwOTMwNywiZW1haWwiOiJ0dHRjazg4QGdtYWlsLmNvbSJ9.hRW02Iy6Y8O-jjAwqWfYtHSqd1F8fXD2TeJc7e2l93c";

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.context)
			.apply(springSecurity())
			.build();
	}

	private ItemResponse registerItem() throws Exception {
		final String url = "/api/item/register";

		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(ItemRegisterRequest.builder()
					.name("상품A")
					.price(1000)
					.stockQuantity(10)
					.category(Category.TOP)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		return gson.fromJson(resultActions
			.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), ItemResponse.class);
	}
	
	@Test
	public void 상품등록테스트실패_필수값없음() throws Exception {
	    // given
		final String url = "/api/item/register";
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(ItemRegisterRequest.builder()
						.name("상품A")
						.price(1000)
//						.stockQuantity(null)
						.category(Category.TOP)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 상품등록성공() throws Exception {
	    // given
		final String url = "/api/item/register";
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(ItemRegisterRequest.builder()
					.name("상품A")
					.price(1000)
					.stockQuantity(10)
					.category(Category.TOP)
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		final ItemResponse itemResponse = gson.fromJson(resultActions
			.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), ItemResponse.class);
	    
	    // then
		resultActions.andExpect(status().isCreated());
		assertThat(itemResponse).isNotNull();
		assertThat(itemResponse.getId()).isNotNull();
		assertThat(itemResponse.getName()).isEqualTo("상품A");
	}
	
	@Test
	public void 상품수정실패_id가존재하지않음() throws Exception {
	    // given
		final String url = "/api/item/update";
	    
	    // when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.content(gson.toJson(ItemUpdateRequest.builder()
					.name("수정")
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 상품수정실패_존재하지않는상품() throws Exception {
	    // given
		final String url = "/api/item/update";

	    // when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.content(gson.toJson(ItemUpdateRequest.builder()
						.id(1L)
						.name("수정")
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
		
		final ItemResponse itemResponse = gson.fromJson(resultActions
			.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), ItemResponse.class);
	    
	    // then
		resultActions.andExpect(status().isNotFound());
	}
	
	@Test
	public void 상품수정성공() throws Exception {
	    // given
		final String url = "/api/item/update";
		Long id = registerItem().getId();
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.patch(url)
				.content(gson.toJson(ItemUpdateRequest.builder()
					.id(id)
					.name("수정")
					.build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
		final ItemResponse itemResponse = gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), ItemResponse.class);

		// then
		resultActions.andExpect(status().isOk());
		assertThat(itemResponse.getId()).isEqualTo(id);
		assertThat(itemResponse.getName()).isEqualTo("수정");
	}

	@Test
	public void 상품상세조회실패_id없음() throws Exception {
	    // given
		final String url = "/api/item/detail";

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 상품상세조회실패_상품없음() throws Exception {
		// given
		final String url = "/api/item/detail";

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.param("id", "1")
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void 상품상세조회성공() throws Exception {
	    // given
		final String url = "/api/item/detail";
		final Long id = registerItem().getId();

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.param("id", id.toString())
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void 상품목록조회성공() throws Exception {
	    // given
		final String url = "/api/item/list";
		registerItem();
	    
	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
		);
		final List result = gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), List.class);

		// then
		resultActions.andExpect(status().isOk());
		assertThat(result.size()).isEqualTo(1);
	}
}














