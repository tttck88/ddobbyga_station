package com.example.dobbyga_station.item.respository;

import com.example.dobbyga_station.item.domain.Item;
import com.example.dobbyga_station.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
