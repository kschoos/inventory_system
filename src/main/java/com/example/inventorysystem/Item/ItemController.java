package com.example.inventorysystem.Item;

import com.example.inventorysystem.Exceptions.ResourceNotFoundException;
import com.example.inventorysystem.Location.LocationRepository;
import com.example.inventorysystem.Tag.TagRepository;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemRepository items;
    private final TagRepository tags;
    private final LocationRepository locations;

    private final ItemService itemService;

    public ItemController(ItemRepository items, TagRepository tags, LocationRepository locations, ItemService itemService) {
        this.items = items;
        this.tags = tags;
        this.locations = locations;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<?> postItem(@Valid @RequestBody ItemCreate itemCreate) {
        var saved_item = itemService.create(itemCreate);
        return ResponseEntity.status(201).body(saved_item);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getItems(
            @RequestParam(required = false) Long location_id,
            @RequestParam(required = false) List<Long> tag_ids,
            @RequestParam(required = false) String name) {

        List<Item> found_items = itemService.findItems(location_id, tag_ids, name);
//
//        if (location_id != null && tag_ids != null) {
//            found_items = items.findDinstinctByLocationIdAndTagsIdIn(location_id, tag_ids);
//        } else if (location_id != null) {
//            found_items = items.findByLocationId(location_id);
//        } else if (tag_ids != null){
//            found_items = items.findDistinctByTagsIdIn(tag_ids);
//        } else {
//            found_items = items.findAll();
//        }

        return ResponseEntity.ok(found_items);
    }

    @GetMapping("/{item_id}")
    @ResponseBody
    public ResponseEntity<?> getSpecificItem(@PathVariable Integer item_id) {
//        int pageSize = 5;
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        Page<Item> itemResults = items.findAll(pageable);
        Item item = items.findById(item_id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        return ResponseEntity.ok(item);
    }
}
