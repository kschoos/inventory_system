package com.example.inventorysystem;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ItemController {
    private final ItemRepository items;
    private final TagRepository tags;
    private final LocationRepository locations;

    public ItemController(ItemRepository items, TagRepository tags, LocationRepository locations) {
        this.items = items;
        this.tags = tags;
        this.locations = locations;
    }

    @PostMapping("/items/new")
    public String newItem(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Item invalid.");
            return "createItem";
        }
        this.items.save(item);
        return "thanks";
    }

    @GetMapping("/items/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("tags", tags.findAll());
        model.addAttribute("locations", locations.findAll());
        return "createItem";
    }

    @GetMapping("/items")
    public String items(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Item> itemResults = items.findAll(pageable);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemResults.getTotalPages());
        model.addAttribute("totalItems", itemResults.getTotalElements());
        model.addAttribute("listItems", itemResults.getContent());

        return "itemList";
    }
}
