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

    public List<Item> findItems(Long locationId, List<Long> tagIds, String name) {
        Specification<Item> spec = null;

        if (locationId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("location").get("id"), locationId));
        }

        if (tagIds != null && !tagIds.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                query.distinct(true);
                return root.join("tags").get("id").in(tagIds);
            });
        }

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            });
        }

        boolean filterApplied = locationId == null && tagIds == null && name == null;

        return !filterApplied ? itemRepository.findAll() : itemRepository.findAll(spec);
    }
}
