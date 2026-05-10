package com.example.inventorysystem.Tag;

import com.example.inventorysystem.Exceptions.ResourceNotFoundException;
import com.example.inventorysystem.SanitizationUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagRepository tags;

    public TagController(TagRepository tags) {
        this.tags = tags;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) { dataBinder.setDisallowedFields("id", "*.id"); }

    @GetMapping
    public ResponseEntity<?> tags() {
        return ResponseEntity.ok(tags.findAll());
    }

    @PostMapping
    public ResponseEntity<?> newTag(Tag tag) {
        Tag savedTag = null;

        tag.setName(SanitizationUtil.sanitize(tag.getName()));

        try {
            savedTag = this.tags.save(tag);
        } catch (DataIntegrityViolationException e) {
            savedTag = this.tags.findByName(tag.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Tag not found."));
        }
        return ResponseEntity.ok(savedTag);
    }
}
