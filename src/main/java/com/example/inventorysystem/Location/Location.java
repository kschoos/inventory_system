package com.example.inventorysystem.Location;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NonNull
    private String name;

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
