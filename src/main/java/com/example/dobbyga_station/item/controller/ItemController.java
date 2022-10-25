package com.example.dobbyga_station.item.controller;

import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.item.domain.ItemRegisterRequest;
import com.example.dobbyga_station.item.domain.ItemResponse;
import com.example.dobbyga_station.item.domain.ItemUpdateRequest;
import com.example.dobbyga_station.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@PostMapping(value = "/api/item/register")
	public ResponseEntity<ItemResponse> registerItem(@RequestBody @Valid final ItemRegisterRequest itemRegisterRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.registerItem(itemRegisterRequest));
	}

	@PatchMapping(value = "/api/item/update")
	public ResponseEntity<ItemResponse> updateItem(@RequestBody @Valid final ItemUpdateRequest itemUpdateRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(itemUpdateRequest));
	}

	@GetMapping(value = "/api/item/detail")
	public ResponseEntity<ItemResponse> findItem(@RequestParam final Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findItem(id));
	}

	@GetMapping(value = "/api/item/list")
	public ResponseEntity<List<ItemResponse>> findAllItem() {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findAllItem());
	}

}
