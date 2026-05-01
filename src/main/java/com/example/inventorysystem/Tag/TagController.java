package com.example.inventorysystem.Tag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TagController {

    private TagRepository tags;

    public TagController(TagRepository tags) {
        this.tags = tags;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) { dataBinder.setDisallowedFields("id", "*.id"); }

    @GetMapping("/tags")
    @ResponseBody
    public List<Tag> tags() {
        return tags.findAll();
    }

    @PostMapping("/tags/new")
    public String newTag(Tag tag) {
        this.tags.save(tag);
        return "thanks";
    }

    @GetMapping("/tags/new")
    public String newTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "createTag";
    }
}
