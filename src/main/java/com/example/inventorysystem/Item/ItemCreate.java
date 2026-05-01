package com.example.inventorysystem.Item;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ItemCreate {
    @NotNull
    private String name;

    private Integer locationId;

    private Set<Integer> tagIds;

    @NotNull
    @URL
    private String imageUrl;

    private Integer count;
}
