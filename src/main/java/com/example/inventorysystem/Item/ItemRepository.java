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

    @Query("SELECT i FROM Item i JOIN i.tags t WHERE i.location.id = :locationId AND t.id IN :tagIds GROUP BY i HAVING COUNT(DISTINCT t.id) = :tagCount")
    List<Item> findByLocationAndAllTags(
            @Param("locationId") Long locationId,
            @Param("tagIds") List<Long> tagIds,
            @Param("tagCount") long tagCount
    );
}
