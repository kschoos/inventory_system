package com.example.inventorysystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findAll(Pageable pageable);

    Optional<Item> findById(Integer id);
}
