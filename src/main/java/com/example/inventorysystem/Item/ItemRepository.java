package com.example.inventorysystem.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
    Page<Item> findAll(Pageable pageable);
    List<Item> findByLocationId(Long locationId);
    Optional<Item> findById(Integer id);

    List<Item> findDistinctByTagsIdIn(List<Long> tagIds);
    List<Item> findDinstinctByLocationIdAndTagsIdIn(Long locationId, List<Long> tagIds);
    List<Item> findByNameContaining(String name);
}
