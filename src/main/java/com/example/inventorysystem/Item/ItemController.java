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
    private final ItemService itemService;

    public ItemController(ItemRepository items, ItemService itemService) {
        this.items = items;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<?> postItem(@Valid @RequestBody ItemCreate itemCreate) {
        var saved_item = itemService.create(itemCreate);
        return ResponseEntity.status(201).body(saved_item);
    }

    @PutMapping
    public ResponseEntity<?> putItem(@Valid @RequestBody ItemCreate itemCreate) {
        var saved_item = itemService.update(itemCreate);
        return ResponseEntity.status(200).body(saved_item);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getItems(
            @RequestParam(required = false) Long location_id,
            @RequestParam(required = false) List<Long> tag_ids,
            @RequestParam(required = false) String name) {

        List<Item> found_items = itemService.findItems(location_id, tag_ids, name);

        return ResponseEntity.ok(found_items);
    }

    @GetMapping("/{item_id}")
    @ResponseBody
    public ResponseEntity<?> getSpecificItem(@PathVariable Integer item_id) {
        Item item = items.findById(item_id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        return ResponseEntity.ok(item);
    }
}
