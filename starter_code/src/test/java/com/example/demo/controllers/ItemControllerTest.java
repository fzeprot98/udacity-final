package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
	@InjectMocks
	ItemController itemController;
	
	@Mock
	ItemRepository itemRepository;
	
	@Test
	void getItems() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findAll();
		ResponseEntity<List<Item>> responseEntity = itemController.getItems();
		assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
	}
	
	@Test
	void getItemById() {
		Mockito.doReturn(Optional.of(new Item())).when(itemRepository).findById(1L);
		ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
		assertEquals(new Item(), responseEntity.getBody());
	}
	
	@Test
	void getItemByNameEmptyList() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findByName("item");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	void getItemsByNameNotFound() {
		Mockito.doReturn(null).when(itemRepository).findByName("item");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	void getItemsByNameContainsData() {
		List<Item> items = new ArrayList<>();
		Item item = new Item();
		item.setId(1L);
		item.setName("item");
		item.setPrice(BigDecimal.ONE);
		item.setDescription("description");
		items.add(item);
		Mockito.doReturn(items).when(itemRepository).findByName("item");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
