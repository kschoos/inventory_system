package com.example.inventorysystem.Item;

import com.example.inventorysystem.Location.Location;
import com.example.inventorysystem.Location.LocationRepository;
import com.example.inventorysystem.Tag.Tag;
import com.example.inventorysystem.Tag.TagRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;
    private final TagRepository tagRepository;


    public ItemService(ItemRepository itemRepository, LocationRepository locationRepository, TagRepository tagRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
    }

    public Item create(ItemCreate itemCreate) {
        Location location = locationRepository.findById(itemCreate.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(itemCreate.getTagIds()));

        Item item = new Item();
        item.setName(itemCreate.getName());
        item.setImageUrl(itemCreate.getImageUrl());
        item.setCount(itemCreate.getCount());
        item.setTags(tags);
        item.setLocation(location);

        return itemRepository.save(item);
    }

    public static Specification<Item> hasLocation(Long locationId) {
        return (root, query, cb) -> {
            if (locationId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("location").get("id"), locationId);
        };
    }

    public static Specification<Item> hasTags(List<Long> tagIds) {
        return (root, query, cb) -> {
            query.distinct(true);
            if (tagIds == null) {
                return cb.conjunction();
            }

            return root.join("tags").get("id").in(tagIds);
        };
    }

    public static Specification<Item> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return cb.conjunction();
            }

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public List<Item> findItems(Long locationId, List<Long> tagIds, String name) {
        Specification<Item> spec = Specification.where(hasLocation(locationId))
                .and(hasTags(tagIds))
                .and(hasName(name));

        return itemRepository.findAll(spec);
    }
}
